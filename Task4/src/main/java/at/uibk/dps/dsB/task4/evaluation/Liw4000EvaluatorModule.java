package at.uibk.dps.dsB.task4.evaluation;

import net.sf.opendse.optimization.evaluator.EvaluatorModule;

/**
 * The {@link Liw4000EvaluatorModule} is used to include the
 * {@link CostEvaluator} and the {@link TimingEvaluator} into the optimization.
 * 
 * (no need to alter anything here)
 * 
 * @author Fedor Smirnov
 *
 */
public class Liw4000EvaluatorModule extends EvaluatorModule {

	@Override
	protected void config() {
		bindEvaluator(CostEvaluator.class);
		bindEvaluator(TimingEvaluator.class);
	}
}
