package at.uibk.dps.sds.t3.modules;

import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.start.Constant;

import at.uibk.dps.sds.t3.homework.SpecificationGenerator;
import net.sf.opendse.optimization.SpecificationWrapper;
import net.sf.opendse.optimization.io.IOModule;

public class HomeworkSpecModule extends IOModule{

	@Order(1)
	@Info("Number of application functions, i.e., disconnected graph parts.")
	@Constant(value = "functionNumber", namespace = SpecificationGenerator.class)
	public int functionNumber = 5;
	
	@Order(2)
	@Info("Minimum length of a function (the depth of the graph).")
	@Constant(value = "minFuncLength", namespace = SpecificationGenerator.class)
	public int minFunctionLength = 1;
	
	@Order(3)
	@Info("Maximum length of a function (the depth of the graph).")
	@Constant(value = "maxFuncLength", namespace = SpecificationGenerator.class)
	public int maxFunctionLength = 3;
	
	@Order(4)
	@Info("Maximum number of successors that one task has.")
	@Constant(value = "maxNumSucc", namespace = SpecificationGenerator.class)
	public int maxNumSucc = 3;
	
	@Order(5)
	@Info("The probability that a task will be assigned as scret.")
	@Constant(value = "secrecyProb", namespace = SpecificationGenerator.class)
	public double secrecyProbability = .5;
	
	@Order(6)
	@Info("The number of edge clusters.")
	@Constant(value = "numEdgeClusters", namespace = SpecificationGenerator.class)
	public int numEdgeClusters = 3;
	
	@Order(7)
	@Info("The number of cloud clusters.")
	@Constant(value = "numCloudClusters", namespace = SpecificationGenerator.class)
	public int numCloudClusters = 3;
	
	@Order(8)
	@Info("Maximum number of resources per edge cluster")
	@Constant(value = "maxNumResEdge", namespace = SpecificationGenerator.class)
	public int maxEdgeResPerCluster = 5;
	
	@Order(9)
	@Info("Maximum number of resources per cloud cluster")
	@Constant(value = "maxNumResCloud", namespace = SpecificationGenerator.class)
	public int maxCloudResPerCluster = 5;
	
	public int getFunctionNumber() {
		return functionNumber;
	}

	public void setFunctionNumber(int functionNumber) {
		this.functionNumber = functionNumber;
	}

	public int getMinFunctionLength() {
		return minFunctionLength;
	}

	public void setMinFunctionLength(int minFunctionLength) {
		this.minFunctionLength = minFunctionLength;
	}

	public int getMaxFunctionLength() {
		return maxFunctionLength;
	}

	public void setMaxFunctionLength(int maxFunctionLength) {
		this.maxFunctionLength = maxFunctionLength;
	}

	public int getMaxNumSucc() {
		return maxNumSucc;
	}

	public void setMaxNumSucc(int maxNumSucc) {
		this.maxNumSucc = maxNumSucc;
	}

	public double getSecrecyProbability() {
		return secrecyProbability;
	}

	public void setSecrecyProbability(double secrecyProbability) {
		this.secrecyProbability = secrecyProbability;
	}

	public int getNumEdgeClusters() {
		return numEdgeClusters;
	}

	public void setNumEdgeClusters(int numEdgeClusters) {
		this.numEdgeClusters = numEdgeClusters;
	}

	public int getNumCloudClusters() {
		return numCloudClusters;
	}

	public void setNumCloudClusters(int numCloudClusters) {
		this.numCloudClusters = numCloudClusters;
	}

	public int getMaxEdgeResPerCluster() {
		return maxEdgeResPerCluster;
	}

	public void setMaxEdgeResPerCluster(int maxEdgeResPerCluster) {
		this.maxEdgeResPerCluster = maxEdgeResPerCluster;
	}

	public int getMaxCloudResPerCluster() {
		return maxCloudResPerCluster;
	}

	public void setMaxCloudResPerCluster(int maxCloudResPerCluster) {
		this.maxCloudResPerCluster = maxCloudResPerCluster;
	}

	@Override
	protected void config() {
		bind(SpecificationWrapper.class).to(SpecificationGenerator.class);
	}
}
