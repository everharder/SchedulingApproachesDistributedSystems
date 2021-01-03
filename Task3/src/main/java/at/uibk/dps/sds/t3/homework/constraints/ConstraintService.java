package at.uibk.dps.sds.t3.homework.constraints;

import net.sf.opendse.encoding.variables.T;
import net.sf.opendse.model.Specification;
import org.opt4j.satdecoding.Constraint;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ConstraintService {

    protected static final List<IConstraint> _constrainViolationCounters = Arrays.asList(
        new NoSecretsOnCloudConstraint(),
		new EnsureTaskMappingConstraint(),
		new MaximumTaskCountOnEdgeConstraint(2),
		new SecretCommunicationConstraint()
    );

    public static List<IConstraint> GetConstraints() {
        return _constrainViolationCounters;
    }

    public static int CountConstraintViolations(Specification implementation) {
        return ConstraintService.GetConstraints()
                .stream()
                .mapToInt(x -> x.CountConstraintViolations(implementation))
                .sum();
    }

    public static Set<Constraint> ToConstraints(Set<T> processVariables, Specification specification) {
        return ConstraintService.GetConstraints()
                .stream()
                .flatMap(x -> x.ToConstraints(processVariables, specification).stream())
                .collect(Collectors.toSet());
    }
}
