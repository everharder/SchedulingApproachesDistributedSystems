package at.uibk.dps.sds.t3.homework;

import at.uibk.dps.sds.t3.homework.constraints.*;
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

	public HwConstraintEvaluator() {
	}

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
	 	return ConstraintService.CountConstraintViolations(implementation);
	}

	@Override
	public int getPriority() {
		// independent of other stuff
		return 0;
	}
}
