package at.uibk.dps.dsB.task4.evaluation;

import net.sf.opendse.model.*;
import org.opt4j.core.Objective;
import org.opt4j.core.Objective.Sign;


import org.opt4j.core.Objectives;

import net.sf.opendse.optimization.ImplementationEvaluator;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Evaluator for the makespan of the Piw3000
 * 
 * @author Fedor Smirnov
 */
public class TimingEvaluator implements ImplementationEvaluator {

	protected final EvaluationHelper evaluationHelper = new EvaluationHelper();

	protected static final int priority = 0;

	protected final Objective makeSpanObjective = new Objective("Worst-Case Makespan [TU]", Sign.MIN);

	//protected final String endTimeAttribute = "End Time";
	//public static final String accumulatedUsageAttribute = "Accumulated Usage";

	@Override
	public Specification evaluate(Specification implementation, Objectives objectives) {

		// calculate average implementation cost by calculating the makespan a fixed number of times and taking the maximum
		// this is done in order to mitigate fluctuations of available cloud-resources and recognized people / cars
		double makespan = IntStream.range(0, evaluationHelper.getIterationCount())
				.mapToDouble(x -> calculateMakespan(implementation))
				.max().getAsDouble();

		objectives.add(makeSpanObjective, makespan);
		// Implementation annotated => return the impl
		return implementation;
	}

	/**
	 * Does the actual makespan calculation.
	 * 
	 * @param implementation the orchestration under evaluation
	 * @return the makespan of the orchestration
	 */
	protected double calculateMakespan(Specification implementation) {

		Application<Task, Dependency> application = implementation.getApplication();
		Optional<Task> firstTask = application
				.getVertices()
				.stream()
				.filter(x -> application.getPredecessorCount(x) == 0)
				.findFirst();
		if(firstTask.isEmpty()) {
			throw new IllegalArgumentException("implementation is invalid -> does not contain a root task (no predecessors)");
		}

		// recursively calculate makespan of application
		return calculateMakespan(firstTask.get(), implementation, new HashSet<>());
	}

	protected double calculateMakespan(Task t, Specification implementation, Set<String> visitedTaskIds) {

		// check if the task has already been processed (loops), in order to avoid infinite recursion
		String id = t.getId();
		if(visitedTaskIds.contains(id)) {
			return 0.0;
		}
		// set task visited
		visitedTaskIds.add(id);

		// calculate makespan of all successors recursively
		OptionalDouble successorsMakespan = implementation.getApplication()
				.getSuccessors(t)
				.stream()
				.mapToDouble(x -> calculateMakespan(x, implementation, visitedTaskIds))
				.max();

		// makespan = execution time of current task + makespan of successors
		return getExecutionTime(t, implementation) + (successorsMakespan.isPresent() ? successorsMakespan.getAsDouble() : 0);
	}

	private double getExecutionTime(Task t, Specification implementation) {
		if(t == null) {
			throw new IllegalArgumentException("t");
		}

		if(evaluationHelper.isCommunication(t)) {
			if(!(t instanceof Communication)) {
				throw new IllegalArgumentException("A communication task must be an instance of Communication");
			}
			return getCommunicationTaskExecutionTime((Communication) t, implementation);
		} else if(evaluationHelper.isProcess(t)) {
			return getProcessTaskExecutionTime(t, implementation);
		} else {
			throw new UnsupportedOperationException("task is neither communication nor process. Check specification!");
		}
	}

	private double getProcessTaskExecutionTime(Task task, Specification implementation) {
		Set<Mapping<Task, Resource>> mappings = implementation.getMappings().get(task);

		// check if task is mapped -> should be a prerequisite
		if(mappings.isEmpty()) {
			throw new UnsupportedOperationException("no resource mapping for task '"+task.getId()+"'. Check implementation!");
		}

		// it could be possible that a task is mapped to multiple resource -> execution time of slowest resource
		return mappings.stream()
				.mapToDouble(x -> getProcessTaskExecutionTime(x))
				.max()
				.getAsDouble();
	}

	private double getProcessTaskExecutionTime(Mapping<Task, Resource> mapping) {
		double executionTime = evaluationHelper.getExecutionTime(mapping);

		// 1-N, depending on if it is a single execution, iterative cars or iterative people task
		int numberOfExecutions = evaluationHelper.getNumberOfExecutions(mapping.getSource());

		// if the resource is in the cloud it is possible that we can divide the workload to multiple instances
		Resource resource = mapping.getTarget();
		int resourceInstances = evaluationHelper.isCloudResource(resource)
				? evaluationHelper.getNumberOfAvailableInstances(resource)
				: 1;

		resourceInstances = Math.max(1, resourceInstances);

		// calculate real execution time based on the number of (parallel) executions
		return executionTime * (int)Math.ceil(numberOfExecutions / (double)resourceInstances);
	}

	private double getCommunicationTaskExecutionTime(Communication communication, Specification implementation) {

		Collection<Link> edges = implementation.getRoutings().get(communication).getEdges();
		if(edges.isEmpty()) {
			// no edges -> assume transmission time is irrelevant
			return 0;
		}

		// use slowest transmission time as communication execution time
		return edges.stream()
				.mapToDouble(x -> evaluationHelper.getTransmissionTime(x, communication))
				.max()
				.getAsDouble();
	}

	@Override
	public int getPriority() {
		return priority;
	}
}
