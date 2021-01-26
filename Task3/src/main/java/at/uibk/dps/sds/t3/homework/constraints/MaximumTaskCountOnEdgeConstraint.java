package at.uibk.dps.sds.t3.homework.constraints;

import at.uibk.dps.sds.t3.homework.PropertyService;
import net.sf.opendse.encoding.variables.M;
import net.sf.opendse.encoding.variables.T;
import net.sf.opendse.encoding.variables.Variables;
import net.sf.opendse.model.*;
import org.opt4j.satdecoding.Constraint;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
Constraint: Due to their restricted capacity, at most N tasks can be executed on a single edge resource
 */
public class MaximumTaskCountOnEdgeConstraint implements IConstraint {

	protected final int maximumTaskCount; // attribute instead of hard-coding the capacity NICE

	public MaximumTaskCountOnEdgeConstraint(int maximumTaskCount) { // making the attribute configurable SUPER NICE
		if (maximumTaskCount <= 0) {
			throw new IllegalArgumentException("maximumTaskCount must be greater than 0");
		}
		this.maximumTaskCount = maximumTaskCount;
	}

	@Override
	public int CountConstraintViolations(Specification implementation) {

		int violations = 0;
		Mappings<Task, Resource> mappings = implementation.getMappings();

		// collect edge resources
		List<Resource> edgeResources = getEdgeResources(mappings);

		// count tasks per edge resource
		for (Resource res : edgeResources) {
			if (mappings.get(res).size() > maximumTaskCount) {
				violations++;
			}
		}

		return violations;
	}

	@Override
	public Set<Constraint> ToConstraints(Set<T> processVariables, Specification specification) {
		Mappings<Task, Resource> mappings = specification.getMappings();
		Set<Constraint> constraints = new HashSet<>();

		// collect edge resources
		List<Resource> edgeResources = getEdgeResources(mappings);

		// add constraint for each edge resource
		for (Resource resource : edgeResources) {
			
			//   sum(M) <= maximumTaskCount
			Constraint c = new Constraint(Constraint.Operator.LE, maximumTaskCount);
			for (Mapping<Task, Resource> mapping : mappings.get(resource)) {
				M mVar = Variables.varM(mapping);
				c.add(Variables.p(mVar));
			}
			constraints.add(c);
		}

		return constraints;
	}

	private List<Resource> getEdgeResources(Mappings<Task, Resource> mappings) {
		return mappings.getAll().stream().map(x -> x.getTarget()).filter(x -> PropertyService.isEdge(x)).distinct() // fancy :)
				.collect(Collectors.toList());
	}
}
