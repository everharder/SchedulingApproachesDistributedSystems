package at.uibk.dps.sds.t3.homework.constraints;

import net.sf.opendse.model.Specification;

public interface IViolationCounter {

    int CountConstraintViolations(Specification implementation);

}
