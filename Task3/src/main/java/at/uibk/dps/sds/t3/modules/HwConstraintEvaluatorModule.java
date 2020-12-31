package at.uibk.dps.sds.t3.modules;

import at.uibk.dps.sds.t3.homework.HwConstraintEvaluator;
import net.sf.opendse.optimization.evaluator.EvaluatorModule;

/**
 * Binds the {@link HwConstraintEvaluator}. 
 * 
 * @author Fedor Smirnov
 */
public class HwConstraintEvaluatorModule extends EvaluatorModule{

	@Override
	protected void config() {
		bindEvaluator(HwConstraintEvaluator.class);
	}
}
