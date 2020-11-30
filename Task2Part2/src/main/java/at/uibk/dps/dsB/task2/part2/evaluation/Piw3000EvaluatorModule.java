package at.uibk.dps.dsB.task2.part2.evaluation;

import net.sf.opendse.optimization.evaluator.EvaluatorModule;

/**
 * The {@link Piw3000EvaluatorModule} is used to include the
 * {@link CostEvaluator} and the {@link TimingEvaluator} into the optimization.
 * 
 * (no need to alter anything here)
 * 
 * @author Fedor Smirnov
 *
 */
public class Piw3000EvaluatorModule extends EvaluatorModule {

	@Override
	protected void config() {
		bindEvaluator(CostEvaluator.class);
		bindEvaluator(TimingEvaluator.class);
	}
}
