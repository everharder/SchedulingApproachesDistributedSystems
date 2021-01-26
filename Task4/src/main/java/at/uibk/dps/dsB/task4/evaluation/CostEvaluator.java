package at.uibk.dps.dsB.task4.evaluation;

import com.google.common.collect.Range;
import net.sf.opendse.model.*;
import org.apache.commons.collections15.EnumerationUtils;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;


import org.opt4j.core.Objective.Sign;

import net.sf.opendse.optimization.ImplementationEvaluator;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * The {@link CostEvaluator} is used to calculate the costs of different
 * orchestrations of the PIW3000.
 * 
 * @author Fedor Smirnov
 *
 */
public class CostEvaluator implements ImplementationEvaluator {

	protected final Objective costObjective = new Objective("Average Costs [FreeRep Libra]", Sign.MIN);
	protected final EvaluationHelper evaluationHelper = new EvaluationHelper();

	@Override
	public Specification evaluate(Specification implementation, Objectives objectives) {

		// calculate average implementation cost by averaging over a fixed number of iterations
		// this is done in order to mitigate fluctuations of available cloud-resources and recognized people / cars
		double costs = IntStream.range(0, evaluationHelper.getIterationCount())
				.mapToDouble(x -> calculateImplementationCost(implementation))
				.average().getAsDouble();

		objectives.add(costObjective, costs);
		// No changes to the implementation => return null
		return null;
	}

	/**
	 * Does the actual cost calculation
	 * 
	 * @param implementation the solution which is being evaluated
	 * @return the cost of the implementation
	 */
	protected double calculateImplementationCost(Specification implementation) {

		Architecture<Resource, Link> architecture = implementation.getArchitecture();
		Mappings<Task, Resource> mappings = implementation.getMappings();



		double costs = 0.0;

		// aggregate resource costs
		// use this set to keep track of already calculated resources
		// (in case tasks are mapped twice to non-cloud resource, which are only billed once)
		Set<String> alreadyDoneResources = new HashSet<>();
		for(Mapping<Task, Resource> m : mappings) {

			Resource resource  = m.getTarget();
			double resourceCost = evaluationHelper.getResourceCosts(resource);

			if(evaluationHelper.isCloudResource(resource)) {

				// cloud resources are rented -> cost = cost * executiontime
				costs += resourceCost * evaluationHelper.getExecutionTime(m) * evaluationHelper.getNumberOfExecutions(m.getSource());

			} else {

				String resourceId = m.getTarget().getId();
				if(alreadyDoneResources.contains(resourceId)) {
					// non-cloud resource already calculated -> skip
					continue;
				}

				// first time for this non-cloud resource -> add to set so it is only calculated once
				alreadyDoneResources.add(resourceId);

				// non cloud resources are purchased -> just use cost
				costs += resourceCost;
			}
		}

		// aggregate link costs
		costs += architecture.getEdges().stream().mapToDouble(x ->evaluationHelper.getLinkCost(x)).sum();

		return costs;
	}

	@Override
	public int getPriority() {
		// To be executed after the timing evaluator
		return TimingEvaluator.priority + 1;
	}
}
