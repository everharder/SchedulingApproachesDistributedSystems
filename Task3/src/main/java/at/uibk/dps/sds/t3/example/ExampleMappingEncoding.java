package at.uibk.dps.sds.t3.example;

import java.util.HashSet;
import java.util.Set;

import org.opt4j.core.start.Constant;
import org.opt4j.satdecoding.Constraint;
import org.opt4j.satdecoding.Constraint.Operator;
import org.opt4j.satdecoding.Term;

import com.google.inject.Inject;

import net.sf.opendse.encoding.mapping.MappingConstraintGenerator;
import net.sf.opendse.encoding.variables.M;
import net.sf.opendse.encoding.variables.T;
import net.sf.opendse.encoding.variables.Variables;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Mappings;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;

public class ExampleMappingEncoding implements MappingConstraintGenerator {

	protected final boolean encodeNoResourceSharing;
	protected final boolean encodeTaskMappingNecessity;

	@Inject
	public ExampleMappingEncoding(
			@Constant(value = "noResourceSharing", namespace = ExampleMappingEncoding.class) boolean encodeNoResourceSharing,
			@Constant(value = "taskMappingNecessity", namespace = ExampleMappingEncoding.class) boolean encodeTaskMappingNecessity) {
		this.encodeNoResourceSharing = encodeNoResourceSharing;
		this.encodeTaskMappingNecessity = encodeTaskMappingNecessity;
	}

	@Override
	public Set<Constraint> toConstraints(Set<T> processVariables, Mappings<Task, Resource> mappings) {
		Set<Constraint> result = new HashSet<>();
		if (encodeNoResourceSharing) {
			result.addAll(encodeNoResourceSharingConstraints(mappings));
		}
		if (encodeTaskMappingNecessity) {
			result.addAll(encodeTaskMappingNecessityConstraints(processVariables, mappings));
		}
		return result;
	}

	/**
	 * Encodes that each task is mapped at least once.
	 * 
	 * @param processVariables the variables encoding the activation of processes
	 * @param mappings         the mappings
	 * @return constraint set encoding that each task is mapped at least once
	 */
	protected Set<Constraint> encodeTaskMappingNecessityConstraints(Set<T> processVariables,
			Mappings<Task, Resource> mappings) {
		Set<Constraint> result = new HashSet<>();
		for (T tVar : processVariables) {
			Set<Mapping<Task, Resource>> taskMappings = mappings.get(tVar.getTask());
			result.add(encodeTaskMappingNecessityConstraint(tVar, taskMappings));
		}
		return result;
	}

	/**
	 * Encodes the constraint stating that the task encoded by the given variable is
	 * mapped on at least one resource.
	 * 
	 * - T + sum (M) >= 0
	 * 
	 * @param tVar         The encoding variable of the task
	 * @param taskMappings a set of the task mappings
	 * @return the constraint stating that the task encoded by the given variable is
	 *         mapped on at least one resource
	 */
	protected Constraint encodeTaskMappingNecessityConstraint(T tVar, Set<Mapping<Task, Resource>> taskMappings) {
		Constraint result = new Constraint(Operator.GE, 0);
		result.add(new Term(-1, Variables.p(tVar))); // Here you have to pay attention to use the Variables from the encoding
										// project, not from the optimization project
		for (Mapping<Task, Resource> mapping : taskMappings) {
			M mVar = Variables.varM(mapping);
			result.add(Variables.p(mVar));
		}
		return result;
	}

	/**
	 * Encodes the constraints preventing resource sharing.
	 * 
	 * @param mappings
	 * @return
	 */
	protected Set<Constraint> encodeNoResourceSharingConstraints(Mappings<Task, Resource> mappings) {
		Set<Constraint> result = new HashSet<>();
		Set<Resource> resources = new HashSet<>();
		for (Mapping<Task, Resource> m : mappings) {
			resources.add(m.getTarget());
		}
		for (Resource res : resources) {
			result.add(encodeNoResourceSharingConstraint(mappings.get(res)));
		}
		return result;
	}

	/**
	 * Encodes that resources are not shared.
	 * 
	 * sum (M(R)) <= 1
	 * 
	 * @param resMapping the resource mappings on that resource
	 * @return constraints preventing resource sharing
	 */
	protected Constraint encodeNoResourceSharingConstraint(Set<Mapping<Task, Resource>> resMappings) {
		Constraint result = new Constraint(Operator.LE, 1);
		for (Mapping<Task, Resource> m : resMappings) {
			M mVar = Variables.varM(m);
			result.add(Variables.p(mVar));
		}
		return result;
	}
}
