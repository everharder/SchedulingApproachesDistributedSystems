package at.uibk.dps.sds.t3.homework.constraints;

import at.uibk.dps.sds.t3.homework.PropertyService;
import net.sf.opendse.encoding.variables.M;
import net.sf.opendse.encoding.variables.T;
import net.sf.opendse.encoding.variables.Variables;
import net.sf.opendse.model.*;
import org.opt4j.satdecoding.Constraint;

import java.util.HashSet;
import java.util.Set;

/*
Constraint: Any task annotated as a secret must not be executed on cloud resources
 */
public class NoSecretsOnCloudConstraint implements IConstraint {

    @Override
    public int CountConstraintViolations(Specification implementation) {

        int violations = 0;
        Mappings<Task, Resource> mappings = implementation.getMappings();

        for (Mapping<Task, Resource> mapping : mappings) {
            // check if cloud resource and task is secret
            if (PropertyService.isCloud(mapping.getTarget()) && PropertyService.isSecret(mapping.getSource())) {
                violations++;
            }
        }

        return violations;
    }

    @Override
    public Set<Constraint> ToConstraints(Set<T> processVariables, Specification specification) {
        Mappings<Task, Resource> mappings = specification.getMappings();
        Set<Constraint> constraints = new HashSet<>();

        Constraint c = new Constraint(Constraint.Operator.EQ, 0);
        for (Mapping<Task, Resource> mapping : mappings) {
            if (PropertyService.isCloud(mapping.getTarget()) && PropertyService.isSecret(mapping.getSource())) {
                M mVar = Variables.varM(mapping);
                c.add(Variables.p(mVar));
            }
        }
        constraints.add(c);

        return constraints;
    }
}
