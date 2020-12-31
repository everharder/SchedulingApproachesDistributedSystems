package at.uibk.dps.sds.t3.modules;

import at.uibk.dps.sds.t3.example.ExampleCostEvaluator;
import net.sf.opendse.optimization.evaluator.EvaluatorModule;

/**
 * Binds the simple cost evaluator.
 * 
 * @author Fedor Smirnov
 */
public class ExampleCostEvaluatorModule extends EvaluatorModule{

	@Override
	protected void config() {
		bindEvaluator(ExampleCostEvaluator.class);
	}
}
