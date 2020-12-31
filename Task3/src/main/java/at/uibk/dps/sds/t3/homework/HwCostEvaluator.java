package at.uibk.dps.sds.t3.homework;

import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;

import com.google.inject.Inject;

import org.opt4j.core.Objective.Sign;
import org.opt4j.core.start.Constant;

import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Specification;
import net.sf.opendse.model.Task;
import net.sf.opendse.optimization.ImplementationEvaluator;

/**
 * 
 * Simplistic cost evaluator. Assumes a constant cost of mapping on cloud and
 * edge resources, respectively.
 * 
 * @author Fedor Smirnov
 *
 */
public class HwCostEvaluator implements ImplementationEvaluator {

	protected final Objective costObjective = new Objective("Mapping Cost", Sign.MIN);
	protected final int costCloudMapping;
	protected final int costEdgeMapping;

	@Inject
	public HwCostEvaluator(@Constant(value = "cloudCost", namespace = HwCostEvaluator.class) int costCloudMapping,
			@Constant(value = "edgeCost", namespace = HwCostEvaluator.class) int costEdgeMapping) {
		this.costCloudMapping = costCloudMapping;
		this.costEdgeMapping = costEdgeMapping;
	}

	@Override
	public Specification evaluate(Specification implementation, Objectives objectives) {
		int result = 0;
		for (Mapping<Task, Resource> m : implementation.getMappings()) {
			if (PropertyService.isCloud(m.getTarget())) {
				result += costCloudMapping;
			} else if (PropertyService.isEdge(m.getTarget())) {
				result += costEdgeMapping;
			}
		}
		objectives.add(costObjective, result);
		return null;
	}

	@Override
	public int getPriority() {
		return 0;
	}
}
