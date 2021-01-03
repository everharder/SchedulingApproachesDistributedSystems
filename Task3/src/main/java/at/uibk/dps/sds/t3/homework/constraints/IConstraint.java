package at.uibk.dps.sds.t3.homework.constraints;

import net.sf.opendse.encoding.variables.T;
import net.sf.opendse.model.Specification;
import org.opt4j.satdecoding.Constraint;

import java.util.Set;

public interface IConstraint {

    int CountConstraintViolations(Specification implementation);

    Set<Constraint> ToConstraints(Set<T> processVariables, Specification specification);
}
