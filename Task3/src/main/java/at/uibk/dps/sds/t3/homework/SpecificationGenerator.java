package at.uibk.dps.sds.t3.homework;

import java.util.HashSet;
import java.util.Set;

import org.opt4j.core.common.random.Rand;
import org.opt4j.core.start.Constant;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import edu.uci.ics.jung.graph.util.EdgeType;
import net.sf.opendse.model.Application;
import net.sf.opendse.model.Architecture;
import net.sf.opendse.model.Communication;
import net.sf.opendse.model.Dependency;
import net.sf.opendse.model.Link;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Mappings;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Specification;
import net.sf.opendse.model.Task;
import net.sf.opendse.model.properties.TaskPropertyService;
import net.sf.opendse.optimization.SpecificationWrapper;

/**
 * The {@link SpecificationGenerator} is used to generate the specification used
 * for the exploration done in the homework.
 * 
 * @author Fedor Smirnov
 */
@Singleton
public class SpecificationGenerator implements SpecificationWrapper {

	protected final Specification spec;
	protected final Rand rand;

	protected final double secrecy;

	// just to keep the IDs unique:
	protected int taskNum = 0;
	protected int commNum = 0;
	protected int dependencyNum = 0;

	protected int resNum = 0;
	protected int linkNum = 0;
	protected int mappingNum = 0;
	protected int regionNum = 0;

	@Inject
	public SpecificationGenerator(
			@Constant(value = "functionNumber", namespace = SpecificationGenerator.class) int funcNum,
			@Constant(value = "minFuncLength", namespace = SpecificationGenerator.class) int minLength,
			@Constant(value = "maxFuncLength", namespace = SpecificationGenerator.class) int maxLength,
			@Constant(value = "maxNumSucc", namespace = SpecificationGenerator.class) int maxNumSucc,
			@Constant(value = "secrecyProb", namespace = SpecificationGenerator.class) double secrecy,
			@Constant(value = "numEdgeClusters", namespace = SpecificationGenerator.class) int numEdgeClusters,
			@Constant(value = "numCloudClusters", namespace = SpecificationGenerator.class) int numCloudClusters,
			@Constant(value = "maxNumResEdge", namespace = SpecificationGenerator.class) int maxEdgeResPerCluster,
			@Constant(value = "maxNumResCloud", namespace = SpecificationGenerator.class) int maxCloudResPerCluster,
			Rand rand) {
		this.rand = rand;
		this.secrecy = secrecy;
		this.spec = generateSpec(funcNum, minLength, maxLength, maxNumSucc, secrecy, numEdgeClusters, numCloudClusters,
				maxEdgeResPerCluster, maxCloudResPerCluster);
	}

	/**
	 * Generates the specification which is provided by this class
	 * 
	 * @param funcNum    the number of functions (number of disconnected graph
	 *                   components)
	 * @param minLength  the min length of a function
	 * @param maxLength  the max length of a function
	 * @param maxNumSucc the max number of successors which a single node can have
	 * @param secrecy    the probability that a created task node will be annotated
	 *                   as secret
	 * @return the specification
	 */
	protected Specification generateSpec(int funcNum, int minLength, int maxLength, int maxNumSucc, double secrecy,
			int numEdgeClusters, int numCloudClusters, int maxEdgeResPerCluster, int maxCloudResPerCluster) {
		Application<Task, Dependency> appl = generateApplication(funcNum, minLength, maxLength, maxNumSucc);
		Architecture<Resource, Link> arch = generateArchitecture(numEdgeClusters, numCloudClusters,
				maxEdgeResPerCluster, maxCloudResPerCluster);
		Mappings<Task, Resource> mappings = generateMappings(arch, appl);
		return new Specification(appl, arch, mappings);
	}

	/**
	 * Generates the mappings between the given application and the given
	 * archtecture.
	 * 
	 * @param arch the given architecture
	 * @param appl the given application
	 * @return the mappings between the given application and the given archtecture
	 */
	protected Mappings<Task, Resource> generateMappings(Architecture<Resource, Link> arch,
			Application<Task, Dependency> appl) {
		Mappings<Task, Resource> result = new Mappings<>();
		// get all edge and cloud resources
		Set<Resource> targets = new HashSet<>();
		for (Resource res : arch) {
			if (PropertyService.isCloud(res) || PropertyService.isEdge(res)) {
				targets.add(res);
			}
		}
		// add mappings for all processes
		for (Task t : appl) {
			if (TaskPropertyService.isProcess(t)) {
				for (Resource res : targets) {
					result.add(new Mapping<Task, Resource>("m" + mappingNum++, t, res));
				}
			}
		}
		return result;
	}

	/**
	 * Generates the architecture which is provided by this class.
	 * 
	 * @param numEdgeClusters       number of edge clusters to create
	 * @param numCloudClusters      number of cloud clusters to create
	 * @param maxEdgeResPerCluster  maximum number of edge resources in a single
	 *                              cluster
	 * @param maxCloudResPerCluster maximum number of cloud resources in a single
	 *                              cluster
	 * @return the generated architecture
	 */
	protected Architecture<Resource, Link> generateArchitecture(int numEdgeClusters, int numCloudClusters,
			int maxEdgeResPerCluster, int maxCloudResPerCluster) {
		Architecture<Resource, Link> result = new Architecture<>();
		Resource internetNode = new Resource("Internet");
		for (int i = 0; i < numCloudClusters; i++) {
			int resNum = rand.nextInt(maxCloudResPerCluster) + 1;
			addCloudCluster(internetNode, result, resNum);
		}
		for (int i = 0; i < numEdgeClusters; i++) {
			int resNum = rand.nextInt(maxEdgeResPerCluster) + 1;
			addEdgeCluster(internetNode, result, resNum);
		}
		return result;
	}

	/**
	 * Creates and connects a cloud cluster with the specified number of resources.
	 * 
	 * @param inet   the resource node modeling the internet
	 * @param arch   the architecture
	 * @param resNum the number of cloud resources in the cluster to create
	 */
	protected void addCloudCluster(Resource inet, Architecture<Resource, Link> arch, int resNum) {
		Resource connectionNode = createRes();
		arch.addEdge(createLink(), inet, connectionNode, EdgeType.UNDIRECTED);
		for (int i = 0; i < resNum; i++) {
			Resource cloudRes = createRes();
			PropertyService.makeCloud(cloudRes);
			arch.addEdge(createLink(), connectionNode, cloudRes, EdgeType.UNDIRECTED);
		}
	}

	/**
	 * Creates and connects an edge cluster with the specified number of resources
	 * to the architecture.
	 * 
	 * @param inet   the internet node
	 * @param arch   the architecture
	 * @param resNum the number of edge recources
	 */
	protected void addEdgeCluster(Resource inet, Architecture<Resource, Link> arch, int resNum) {
		int regionId = regionNum++;
		Resource connectionNode = createRes();
		arch.addEdge(createLink(), inet, connectionNode, EdgeType.UNDIRECTED);
		for (int i = 0; i < resNum; i++) {
			Resource edgeRes = createRes();
			PropertyService.makeEdge(edgeRes);
			PropertyService.setRegion(edgeRes, "Region " + regionId);
			arch.addEdge(createLink(), connectionNode, edgeRes, EdgeType.UNDIRECTED);
		}
	}

	/**
	 * Generates the application uses the same arguments as the generateSpec method
	 * 
	 * @param funcNum
	 * @param minLength
	 * @param maxLength
	 * @param maxNumSucc
	 * @param secrecy
	 * @return
	 */
	protected Application<Task, Dependency> generateApplication(int funcNum, int minLength, int maxLength,
			int maxNumSucc) {
		Application<Task, Dependency> result = new Application<>();
		for (int i = 0; i < funcNum; i++) {
			int funcLength = minLength + rand.nextInt(maxLength - minLength) + 1;
			addFunction(funcLength, maxNumSucc, result);
		}
		return result;
	}

	/**
	 * Adds a function to the application.
	 * 
	 * @param length     the length of the function
	 * @param maxSuccNum the max number of direct successors of one task
	 * @param appl       the application
	 */
	protected void addFunction(int length, int maxSuccNum, Application<Task, Dependency> appl) {
		int levelsToAdd = length - 1;
		Task funcRoot = createTask();
		appl.addVertex(funcRoot);
		addRemainingLevels(appl, funcRoot, levelsToAdd, maxSuccNum);
		return;
	}

	/**
	 * Recursive function for the addition of function levels
	 * 
	 * @param appl        the application
	 * @param t           the current task
	 * @param levelsToAdd levels yet to add
	 * @param maxSuccNum  the max number of direct successors of one task
	 */
	protected void addRemainingLevels(Application<Task, Dependency> appl, Task t, int levelsToAdd, int maxSuccNum) {
		if (levelsToAdd == 0) {
			return;
		}
		int succNum = rand.nextInt(maxSuccNum);
		for (int succ = 0; succ < succNum; succ++) {
			Task successor = createTask();
			Communication comm = createComm();
			appl.addEdge(createDependency(), t, comm, EdgeType.DIRECTED);
			appl.addEdge(createDependency(), comm, successor, EdgeType.DIRECTED);
			addRemainingLevels(appl, successor, levelsToAdd - 1, maxSuccNum);
		}
	}

	@Override
	public Specification getSpecification() {
		return this.spec;
	}

	protected Dependency createDependency() {
		return new Dependency("d" + dependencyNum++);
	}

	protected Task createTask() {
		Task result = new Task("t" + taskNum++);
		PropertyService.setSecret(result, rand.nextDouble() < secrecy);
		return result;
	}

	protected Communication createComm() {
		return new Communication("c" + commNum++);
	}

	protected Resource createRes() {
		return new Resource("r" + resNum++);
	}

	protected Link createLink() {
		return new Link("l" + linkNum++);
	}

}
