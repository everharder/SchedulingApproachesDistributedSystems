package at.uibk.dps.sds.t3.homework;

import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.Objective.Sign;

import net.sf.opendse.model.Specification;
import net.sf.opendse.optimization.ImplementationEvaluator;

/**
 * The evaluator used to enforce the security constraints by means of additional
 * objectives.
 * 
 * @author Fedor Smirnov
 *
 */
public class HwConstraintEvaluator implements ImplementationEvaluator {

	protected final Objective numConstraintViolations = new Objective("Num Constraint Violations", Sign.MIN);

	@Override
	public Specification evaluate(Specification implementation, Objectives objectives) {
		objectives.add(numConstraintViolations, countConstraintViolations(implementation));
		return null;
	}

	/**
	 * Counts the number of constraint violations in the given implementation
	 * 
	 * @param implementation the given implementation
	 * @return the number of constraint violations
	 */
	protected int countConstraintViolations(Specification implementation) {
		// TODO implement this method
		throw new IllegalStateException("Counting constraint violations not yet implemented.");
	}

	@Override
	public int getPriority() {
		// independent of other stuff
		return 0;
	}
}
