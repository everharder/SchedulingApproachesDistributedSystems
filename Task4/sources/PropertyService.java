package at.uibk.dps.dsB.task2.part2.properties;

import net.sf.opendse.model.Link;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;

/**
 * Static method container offering convenience methods to access the properties
 * of the elements of the specification used in part 2 of this task.
 * 
 * @author Fedor Smirnov
 */
public class PropertyService {

	enum TaskProperties {
		/**
		 * The type of the task
		 */
		TYPE,
		/**
		 * A measure of the workload/payload of the task/message
		 */
		WORKLOAD
	}

	enum TaskType {
		/**
		 * Task executed just once
		 */
		SINGLE_EXECUTION,
		/**
		 * Task executed once for every car detected in the picture
		 */
		ITERATIVE_CARS,
		/**
		 * Task executed once for every person detected in the picture
		 */
		ITERATIVE_PEOPLE
	}

	enum ResourceProperties {
		/**
		 * The resource type
		 */
		TYPE,
		/**
		 * the cost rate (cost per time) for cloud resources; fix costs for every other
		 * resource
		 */
		COST,
		/**
		 * a measure for the capability to process workload
		 */
		PERFORMANCE
	}

	enum ResourceTypes {
		CLOUD, FOG, EDGE
	}

	enum LinkProperties {
		/**
		 * The monetary costs of links (not set if free)
		 */
		COST,
		/**
		 * Bandwidth of the link
		 */
		BANDWIDTH
	}

	/**
	 * Sets the bandwidth for the requested link.
	 * 
	 * @param l the requested link
	 * @param bandwidth the bandwidth to set
	 */
	public static void setBandwidth(Link l, double bandwidth) {
		l.setAttribute(LinkProperties.BANDWIDTH.name(), bandwidth);
	}
	
	/**
	 * Returns the bandwidth of the requested link
	 * 
	 * @param l the requested link
	 * @return the bandwidth of the requestes link
	 */
	public static double getBandwidth(Link l) {
		return l.getAttribute(LinkProperties.BANDWIDTH.name());
	}
	
	/**
	 * Sets the workload for the provided task
	 * 
	 * @param t the provided task
	 * @param workload the workload of the provided task
	 */
	public static void setWorkload(Task t, double workload) {
		t.setAttribute(TaskProperties.WORKLOAD.name(), workload);
	}
	
	/**
	 * Returns the workload of the provided task
	 * 
	 * @param t the provided task
	 * @return the workload of the provided task
	 */
	public static double getWorkload(Task t) {
		return t.getAttribute(TaskProperties.WORKLOAD.name());
	}
	
	/**
	 * Sets the performance for the provided resource.
	 * 
	 * @param res
	 * @param performance
	 */
	public static void setPerformance(Resource res, double performance) {
		res.setAttribute(ResourceProperties.PERFORMANCE.name(), performance);
	}
	
	/**
	 * Returns the performance of the requested resource
	 * 
	 * @param res the requested resource
	 * @return the performance of the requested resource
	 */
	public static double getPerformance(Resource res) {
		return res.getAttribute(ResourceProperties.PERFORMANCE.name());
	}
	
	public static boolean isIterativePeople(Task t) {
		return isOfType(t, TaskType.ITERATIVE_PEOPLE);
	}

	public static void setIterativePeople(Task t) {
		setTaskType(t, TaskType.ITERATIVE_PEOPLE);
	}

	public static boolean isIterativeCars(Task t) {
		return isOfType(t, TaskType.ITERATIVE_CARS);
	}

	public static void setIterativeCars(Task t) {
		setTaskType(t, TaskType.ITERATIVE_CARS);
	}

	public static boolean isSingleExecTask(Task t) {
		return isOfType(t, TaskType.SINGLE_EXECUTION);
	}

	protected static boolean isOfType(Task t, TaskType type) {
		return getTaskType(t).equals(type);
	}

	/**
	 * Returns the type of the task.
	 * 
	 * @param t the task
	 * @return the type of the task
	 */
	protected static TaskType getTaskType(Task t) {
		if (t.getAttribute(TaskProperties.TYPE.name()) == null) {
			return TaskType.SINGLE_EXECUTION;
		} else {
			return TaskType.valueOf(t.getAttribute(TaskProperties.TYPE.name()));
		}
	}

	/**
	 * Sets the provided task type for the provided task.
	 * 
	 * @param t        the provided task
	 * @param taskType the provided task type
	 */
	protected static void setTaskType(Task t, TaskType taskType) {
		t.setAttribute(TaskProperties.TYPE.name(), taskType.name());
	}

	/**
	 * Sets the costs for the provided link.
	 * 
	 * @param l     the provided link
	 * @param costs the costs to set
	 */
	public static void setLinkCost(Link l, double costs) {
		l.setAttribute(LinkProperties.COST.name(), costs);
	}

	/**
	 * Returns the costs of the provided link.
	 * 
	 * @param l the provided link
	 * @return the costs of the provided link
	 */
	public static double getLinkCost(Link l) {
		if (l.getAttribute(LinkProperties.COST.name()) == null) {
			return 0.0;
		} else {
			return l.getAttribute(LinkProperties.COST.name());
		}
	}

	/**
	 * Set the costs for the provided resource
	 * 
	 * @param res   the provided resource
	 * @param costs the costs to set
	 */
	public static void setResourceCosts(Resource res, double costs) {
		res.setAttribute(ResourceProperties.COST.name(), costs);
	}

	/**
	 * Returns the costs of the provided resource
	 * 
	 * @param res the provided resource
	 * @return the costs of the provided resource
	 */
	public static double getResourceCosts(Resource res) {
		if (res.getAttribute(ResourceProperties.COST.name()) == null) {
			return 0.0;
		} else {
			return res.getAttribute(ResourceProperties.COST.name());
		}
	}

	/**
	 * Annotates the given resource as a cloud resource
	 * 
	 * @param res
	 */
	public static void makeCloudRes(Resource res) {
		setResType(res, ResourceTypes.CLOUD);
	}
	
	/**
	 * Annotates the given resource as a fog resource
	 * 
	 * @param res
	 */
	public static void makeFogRes(Resource res) {
		setResType(res, ResourceTypes.FOG);
	}
	
	/**
	 * Annotates the given resource as a cloud resource
	 * 
	 * @param res
	 */
	public static void makeEdgeRes(Resource res) {
		setResType(res, ResourceTypes.EDGE);
	}
	
	/**
	 * Sets the type of the given resource
	 *  
	 * @param res the given resource
	 * @param type the type to set
	 */
	protected static void setResType(Resource res, ResourceTypes type) {
		res.setAttribute(ResourceProperties.TYPE.name(), type.name());
	}
	
	/**
	 * Returns true iff the provided resource is an edge resource.
	 * 
	 * @param res the provided resource
	 * @return true iff the provided resource is an edge resource
	 */
	public static boolean isEdgeResource(Resource res) {
		return isResourceType(res, ResourceTypes.EDGE);
	}

	/**
	 * Returns true iff the provided resource is a fog resource.
	 * 
	 * @param res the provided resource
	 * @return true iff the provided resource is a fog resource
	 */
	public static boolean isFogResource(Resource res) {
		return isResourceType(res, ResourceTypes.FOG);
	}

	/**
	 * Returns true iff the provided resource is a cloud resource.
	 * 
	 * @param res the provided resource
	 * @return true iff the provided resource is a cloud resource
	 */
	public static boolean isCloudResource(Resource res) {
		return isResourceType(res, ResourceTypes.CLOUD);
	}

	/**
	 * Returns true iff the provided resource is of the provided type.
	 * 
	 * @param res  the provided resource
	 * @param type the resource type in question
	 * @return true iff the provided resource is of the provided type
	 */
	protected static boolean isResourceType(final Resource res, final ResourceTypes type) {
		if (res.getAttribute(ResourceProperties.TYPE.name()) == null) {
			return false;
		} else {
			return res.getAttribute(ResourceProperties.TYPE.name()).equals(type.name());
		}
	}

}
