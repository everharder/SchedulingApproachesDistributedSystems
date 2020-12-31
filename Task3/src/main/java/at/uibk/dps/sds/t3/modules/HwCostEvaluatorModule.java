package at.uibk.dps.sds.t3.modules;

import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.start.Constant;

import at.uibk.dps.sds.t3.homework.HwCostEvaluator;
import net.sf.opendse.optimization.evaluator.EvaluatorModule;

/**
 * Binds the simplistic cost evaluator.
 * 
 * @author Fedor Smirnov
 *
 */
public class HwCostEvaluatorModule extends EvaluatorModule {

	@Order(1)
	@Constant(value = "cloudCost", namespace = HwCostEvaluator.class)
	public int costPerCloudMapping = 1;

	@Order(2)
	@Constant(value = "edgeCost", namespace = HwCostEvaluator.class)
	public int costPerEdgeMapping = 5;

	public int getCostPerCloudMapping() {
		return costPerCloudMapping;
	}

	public void setCostPerCloudMapping(int costPerCloudMapping) {
		this.costPerCloudMapping = costPerCloudMapping;
	}

	public int getCostPerEdgeMapping() {
		return costPerEdgeMapping;
	}

	public void setCostPerEdgeMapping(int costPerEdgeMapping) {
		this.costPerEdgeMapping = costPerEdgeMapping;
	}

	@Override
	protected void config() {
		bindEvaluator(HwCostEvaluator.class);
	}
}
