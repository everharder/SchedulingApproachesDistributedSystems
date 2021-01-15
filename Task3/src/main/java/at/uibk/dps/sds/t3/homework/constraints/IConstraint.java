package at.uibk.dps.sds.t3.homework.constraints;

import net.sf.opendse.encoding.variables.T;
import net.sf.opendse.model.Specification;
import org.opt4j.satdecoding.Constraint;

import java.util.Set;

public interface IConstraint {

	int CountConstraintViolations(Specification implementation);

	Set<Constraint> ToConstraints(Set<T> processVariables, Specification specification);

	/**
	 * (1) It is very cool that you are thinking about the definition of interfaces
	 * and the general structure beyond the provided classes.
	 * 
	 * (2) The interface/class architecture you have chosen enables a very elegant
	 * and nicely readable implementation of the HomeworkMappingEncoder. Well done!
	 * 
	 * (3) One minor criticism with regard to the architecture is that you could
	 * further improve cohesiveness by splitting the IConstraint interface into 2
	 * interfaces, e.g., IViolationCounter and IConstraintGenerator which would,
	 * instead of being responsible for BOTH the counting of constraint violations
	 * and the constraint formulation, be responsible for a SINGLE aspect (in fact,
	 * the counting of violations and the formulation of constraints actually never
	 * occurs at the same time, so that it can be easily divided to create simpler
	 * classes).
	 */

}
