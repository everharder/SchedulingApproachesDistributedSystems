package at.uibk.dps.sds.t3.homework.constraints;

import at.uibk.dps.sds.t3.homework.PropertyService;
import net.sf.opendse.encoding.variables.T;
import net.sf.opendse.encoding.variables.Variables;
import net.sf.opendse.model.*;
import net.sf.opendse.model.properties.TaskPropertyService;
import org.opt4j.satdecoding.Constraint;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
Constraint: If two tasks exchange messages and are both annotated as secrets, they both must be executed within the same region
 */
public class SecretCommunicationConstraint implements IConstraint {

	@Override
	public int CountConstraintViolations(Specification implementation) {
		int violations = 0;
		Application<Task, Dependency> application = implementation.getApplication();
		Mappings<Task, Resource> mappings = implementation.getMappings();

		for (Task sender : application.getVertices()) {

			// check if task is communication and is secret
			// if sender is not secret, no violation can happen, since this constraint
			// checks if BOTH sender and receiver are secret
			if (!TaskPropertyService.isCommunication(sender) || !PropertyService.isSecret(sender)) {
				continue;
			}

			// get all tasks that receive messages from the current task and are marked as
			// secret
			// no need to check for incoming edges, because we iterate over all tasks
			List<Task> receivingTasks = getReceivingTasks(application, sender);

			// get regions for resources of sending task
			List<String> regionsSender = getRegions(sender, mappings);

			for (Task receiver : receivingTasks) {

				// get regions for resources of receiving task
				List<String> regionsReceiver = getRegions(receiver, mappings);

				// check if sender and receiver have exactly the same regions
				if (regionsReceiver.size() != regionsSender.size() || !regionsReceiver.containsAll(regionsSender)) {
					violations++;
				}
			}
		}

		return violations;
	}

	@Override
	public Set<Constraint> ToConstraints(Set<T> processVariables, Specification specification) {

		Mappings<Task, Resource> mappings = specification.getMappings();
		Application<Task, Dependency> application = specification.getApplication();
		Set<Constraint> constraints = new HashSet<>();

		List<Task> tasks = mappings.getAll().stream().map(x -> x.getSource()).collect(Collectors.toList());

		for (Task sender : tasks) {

			// check if task is communication and is secret
			// if sender is not secret, no violation can happen, since this constraint
			// checks if BOTH sender and receiver are secret
			if (!TaskPropertyService.isCommunication(sender) || !PropertyService.isSecret(sender)) {
				continue;
			}

			// get all tasks that receive messages from the current task and are marked as
			// secret
			// no need to check for incoming edges, because we iterate over all tasks
			List<Task> receivingTasks = getReceivingTasks(application, sender);

			// get regions for resources of sending task
			Set<Mapping<Task, Resource>> mappingsSender = mappings.get(sender);
			List<String> regionsSender = getRegions(sender, mappings);

			for (Task receiver : receivingTasks) {

				Set<Mapping<Task, Resource>> mappingsReceiver = mappings.get(receiver);
				List<String> regionsReceiver = getRegions(receiver, mappings);

				// constraint -> difference in regions must be 0
				Constraint c = new Constraint(Constraint.Operator.EQ, 0);

				// super minor: these two are duplications. Could be put into an extra method

				// find regions of sender missing in receiver
				mappingsSender.stream().filter(x -> !regionsReceiver.contains(PropertyService.getRegion(x.getTarget())))
						.forEach(x -> c.add(Variables.p(Variables.varM(x))));

				// find regions of receiver missing in sender
				mappingsReceiver.stream().filter(x -> !regionsSender.contains(PropertyService.getRegion(x.getTarget())))
						.forEach(x -> c.add(Variables.p(Variables.varM(x))));

				constraints.add(c);

				// This is not entirely correct:

				// Let's look at an example:

				// Task 1 can be put onto (a resource in) Region A, B, or C
				// Task 2 can be put onto (a resource in) Region A, B, or D

				// With the constraints you are formulating, you prevent the situations where
				// Task 1 is mapped in region C (since Task 2 cannot be mapped there at all) and
				// the situations where Task 2 is mapped in D (analogical reason). This is so
				// far fine.

				// What can still happen is that Task 1 is mapped (once) in region A, while Task
				// 2 is mapped in region B, necessitating a communication over the cloud
				// resources.

			}
		}

		return constraints;
	}

	/*
	 * gets the regions associated with the task resources
	 */
	private List<String> getRegions(Task t, Mappings<Task, Resource> mappings) {
		return mappings.get(t).stream().map(x -> PropertyService.getRegion(x.getTarget())).distinct()
				.collect(Collectors.toList());
	}

	private List<Task> getReceivingTasks(Application<Task, Dependency> application, Task sender) {
		return application.getOutEdges(sender).stream().map(x -> application.getDest(x))
				.filter(x -> PropertyService.isSecret(x)).collect(Collectors.toList());
	}
}
