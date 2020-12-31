package at.uibk.dps.sds.t3.example;

import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.Objective.Sign;

import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Mappings;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Specification;
import net.sf.opendse.model.Task;
import net.sf.opendse.optimization.ImplementationEvaluator;

/**
 * Primitive cost evaluator for the example.
 * 
 * @author Fedor Smirnov
 *
 */
public class ExampleCostEvaluator implements ImplementationEvaluator {

	protected final Objective costObjective = new Objective("Costs [$]", Sign.MIN);

	@Override
	public Specification evaluate(Specification implementation, Objectives objectives) {
		int costs = getMappingCosts(implementation.getMappings());
		objectives.add(costObjective, costs);
		// no change to the implementation
		return null;
	}

	/**
	 * Returns the costs of the given implementation mappings.
	 * 
	 * @param implMappings the implementation mappings
	 * @return the costs of the given implementation mappings
	 */
	protected int getMappingCosts(Mappings<Task, Resource> implMappings) {
		int result = 0;
		for (Mapping<Task, Resource> mapping : implMappings) {
			result += (int) mapping.getAttribute(ExampleSpecWrapper.costAttrName);
		}
		return result;
	}

	@Override
	public int getPriority() {
		// no relation to anything else
		return 0;
	}
}
