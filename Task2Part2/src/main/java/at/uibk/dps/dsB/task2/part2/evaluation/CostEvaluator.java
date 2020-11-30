package at.uibk.dps.dsB.task2.part2.evaluation;

import net.sf.opendse.model.*;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;

import org.opt4j.core.Objective.Sign;

import at.uibk.dps.dsB.task2.part2.properties.PropertyProvider;
import at.uibk.dps.dsB.task2.part2.properties.PropertyProviderStatic;
import net.sf.opendse.optimization.ImplementationEvaluator;

import java.util.HashSet;
import java.util.Set;

/**
 * The {@link CostEvaluator} is used to calculate the costs of different
 * orchestrations of the PIW3000.
 * 
 * @author Fedor Smirnov
 *
 */
public class CostEvaluator implements ImplementationEvaluator {

	protected final Objective costObjective = new Objective("Costs [Distopistan Dorrar]", Sign.MIN);
	protected final EvaluationHelper evaluationHelper = new EvaluationHelper();

	@Override
	public Specification evaluate(Specification implementation, Objectives objectives) {
		double costs = calculateImplementationCost(implementation);
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
		double implementationCost = 0;

		Mappings<Task, Resource> mappings = implementation.getMappings();
		Set<String> alreadyDoneResources = new HashSet<>();

		for(Mapping<Task, Resource> m : mappings) {

			String id = m.getTarget().getId();

			// non-cloud resource are only counted once -> skip if already done
			if(alreadyDoneResources.contains(id)) {
				continue;
			}

			double cost = evaluationHelper.getCost(m.getTarget());

			// check whether resource is a cloud attribute or not
			if(evaluationHelper.isCloudResource(m.getTarget())) {
				double time = evaluationHelper.getExecutionTime(m) * evaluationHelper.getNumberOfExecutions(m.getSource());
				implementationCost += cost / time;
			} else {
				alreadyDoneResources.add(id);
				implementationCost += cost;
			}
		}

		return implementationCost;
	}

	@Override
	public int getPriority() {
		// To be executed after the timing evaluator
		return TimingEvaluator.priority + 1;
	}
}
