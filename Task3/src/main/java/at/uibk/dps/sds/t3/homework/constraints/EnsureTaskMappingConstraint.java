package at.uibk.dps.sds.t3.homework.constraints;

import net.sf.opendse.encoding.variables.M;
import net.sf.opendse.encoding.variables.T;
import net.sf.opendse.encoding.variables.Variables;
import net.sf.opendse.model.*;
import org.opt4j.satdecoding.Constraint;
import org.opt4j.satdecoding.Term;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Also, just FYI: If you use the comment syntax I am using for this comment for
 * class and method headers, the comment can be automatically transformed into
 * JavaDoc and the tooltips which Eclipse/IntelliJ give you when using
 * autocomplete. This is pretty handy :)
 */

/*
 * Constraint: Each task has to be mapped onto at least one resource
 */
public class EnsureTaskMappingConstraint implements IConstraint {

	@Override
	public int CountConstraintViolations(Specification implementation) {
		int violations = 0;

		Collection<Task> tasks = implementation.getApplication().getVertices();
		Mappings<Task, Resource> mappings = implementation.getMappings();

		for (Task task : tasks) {

			// get mapping for task
			Set<Mapping<Task, Resource>> taskMappings = mappings.get(task);

			// check if there exists at least one mapping for the current task
			if (taskMappings == null || taskMappings.size() == 0) {
				violations++;
			}
		}

		return violations;
	}

	@Override
	public Set<Constraint> ToConstraints(Set<T> processVariables, Specification specification) {
		var mappings = specification.getMappings();
		Set<Constraint> result = new HashSet<>();
		for (T tVar : processVariables) {
			Set<Mapping<Task, Resource>> taskMappings = mappings.get(tVar.getTask());

			// Another random recommendation:

			// when formulating constraints, I like to have a comment stating the constraint
			// equation there. Makes it much easier to read when seeing the code again after
			// a while. Like so:
			
			// sum(M) - T >= 0
			Constraint c = new Constraint(Constraint.Operator.GE, 0);
			c.add(new Term(-1, Variables.p(tVar)));
			for (Mapping<Task, Resource> mapping : taskMappings) {
				M mVar = Variables.varM(mapping);
				c.add(Variables.p(mVar));
			}
			result.add(c);
		}
		return result;
	}
}
