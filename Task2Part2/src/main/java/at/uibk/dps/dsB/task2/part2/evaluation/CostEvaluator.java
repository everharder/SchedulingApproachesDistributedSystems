package at.uibk.dps.dsB.task2.part2.evaluation;

import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;

import org.opt4j.core.Objective.Sign;

import at.uibk.dps.dsB.task2.part2.properties.PropertyProvider;
import at.uibk.dps.dsB.task2.part2.properties.PropertyProviderStatic;
import net.sf.opendse.model.Specification;
import net.sf.opendse.optimization.ImplementationEvaluator;

/**
 * The {@link CostEvaluator} is used to calculate the costs of different
 * orchestrations of the PIW3000.
 * 
 * @author Fedor Smirnov
 *
 */
public class CostEvaluator implements ImplementationEvaluator {

	protected final Objective costObjective = new Objective("Costs [Distopistan Dorrar]", Sign.MIN);
	protected final PropertyProvider propertyProvider = new PropertyProviderStatic();

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
		throw new IllegalStateException("Cost Calculation not yet implemented.");
	}

	@Override
	public int getPriority() {
		// To be executed after the timing evaluator
		return TimingEvaluator.priority + 1;
	}
}
