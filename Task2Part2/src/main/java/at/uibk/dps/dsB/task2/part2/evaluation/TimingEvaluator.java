package at.uibk.dps.dsB.task2.part2.evaluation;

import net.sf.opendse.model.*;
import org.opt4j.core.Objective;
import org.opt4j.core.Objective.Sign;
import org.opt4j.core.Objectives;

import at.uibk.dps.dsB.task2.part2.properties.PropertyProvider;
import at.uibk.dps.dsB.task2.part2.properties.PropertyProviderStatic;
import net.sf.opendse.optimization.ImplementationEvaluator;

import java.util.Set;

/**
 * Evaluator for the makespan of the Piw3000
 * 
 * @author Fedor Smirnov
 */
public class TimingEvaluator implements ImplementationEvaluator {

	protected final EvaluationHelper evaluationHelper = new EvaluationHelper();

	protected static final int priority = 0;

	protected final Objective makeSpanObjective = new Objective("Makespan [TU]", Sign.MIN);

	protected final String endTimeAttribute = "End Time";
	public static final String accumulatedUsageAttribute = "Accumulated Usage";


	@Override
	public Specification evaluate(Specification implementation, Objectives objectives) {
		objectives.add(makeSpanObjective, calculateMakespan(implementation));
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

		double executionTime = 0;

		Application<Task, Dependency> application = implementation.getApplication();

		Mappings<Task, Resource> mappings = implementation.getMappings();

		Routings<Task, Resource, Link> routings = implementation.getRoutings();



		for(Task t : application.getVertices()) {

			Architecture<Resource, Link> routing = routings.get(t);

			// check how often the task has to be executed
			int n = evaluationHelper.getNumberOfExecutions(t);

			// get the resource mappings for the task
			Set<Mapping<Task, Resource>> mapping = mappings.get(t);
			for(Mapping<Task, Resource> m : mapping) {

				// check how many instances of the resource are available for parallelization
				int instances = evaluationHelper.getNumberOfAvailableInstances(m.getTarget());

				// calculate total execution time for the given task and the current resource
				executionTime += evaluationHelper.getExecutionTime(m) * ((n / instances)+1);
			}
		}

		return  executionTime;
	}

	@Override
	public int getPriority() {
		return priority;
	}
}
