package at.uibk.dps.dsB.task2.part2.evaluation;

import org.opt4j.core.Objective;
import org.opt4j.core.Objective.Sign;
import org.opt4j.core.Objectives;

import at.uibk.dps.dsB.task2.part2.properties.PropertyProvider;
import at.uibk.dps.dsB.task2.part2.properties.PropertyProviderStatic;
import net.sf.opendse.model.Specification;
import net.sf.opendse.optimization.ImplementationEvaluator;

/**
 * Evaluator for the makespan of the Piw3000
 * 
 * @author Fedor Smirnov
 */
public class TimingEvaluator implements ImplementationEvaluator {

	protected final PropertyProvider propertyProvider = new PropertyProviderStatic();

	protected static final int priority = 0;

	protected final Objective makeSpanObjective = new Objective("Makespan [TU]", Sign.MIN);

	protected final String endTimeAttribute = "End Time";
	public static final String accumulatedUsageAttribute = "Accumulated Usage";

	@Override
	public Specification evaluate(Specification implementation, Objectives objectives) {
		objectives.add(makeSpanObjective, calculateMakespan(implementation));
		// Implementation annotated => return the impl
		return implementation;
	}

	/**
	 * Does the actual makespan calculation.
	 * 
	 * @param implementation the orchestration under evaluation
	 * @return the makespan of the orchestration
	 */
	protected double calculateMakespan(Specification implementation) {
		throw new IllegalArgumentException("Makespan calculation not yet implemented.");
	}

	@Override
	public int getPriority() {
		return priority;
	}
}
