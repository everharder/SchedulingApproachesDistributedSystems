package at.uibk.dps.dsB.task2.part1;

import net.sf.opendse.model.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * The {@link SpecificationGenerator} generates the {@link Specification}
 * modeling the orchestration of the customer modeling application discussed in
 * Lecture 1.
 * 
 * @author Fedor Smirnov
 */
public final class SpecificationGenerator {

	private SpecificationGenerator() {
	}

	/**
	 * Generates the specification modeling the orchestration of the customer
	 * monitoring application.
	 * 
	 * @return the specification modeling the orchestration of the customer
	 *         monitoring application
	 */
	public static Specification generate() {
		/*
		 * The specification consists of the application, architecture, and
		 * mappings. Additionally it is possible to specify routings for
		 * communication tasks.
		 */

		Application<Task, Dependency> appl = generateApplication();
		Architecture<Resource, Link> arch = generateArchitecture();
		Mappings<Task, Resource> mappings = generateMappings(arch, appl);
		return new Specification(appl, arch, mappings);
	}

	/**
	 * Generates the application graph
	 * 
	 * @return the application graph
	 */
	private static Application<Task, Dependency> generateApplication() {

		/*
		 * The application is defined by data-dependent tasks. In general, two
		 * tasks have to be implemented either on the same or adjacent
		 * resources.
		 */

		Application<Task, Dependency> application = new Application<Task, Dependency>();

		// create tasks
		Task[] tasks = {
				new Task("t0"),
				new Task("t1"),
				new Task("t2"),
				new Task("t3"),
				new Task("t4"),
				new Task("t5")
		};

		// add tasks to application
		for(Task t : tasks) {
			application.addVertex(t);
		}

		// create communications
		Communication[] comms = {
				new Communication("c0"),
				new Communication("c1"),
				new Communication("c2"),
				new Communication("c3"),
				new Communication("c4"),
		};
		// add communications to application
		for(Communication c : comms) {
			application.addVertex(c);
		}

		// add dependencies
		application.addEdge(new Dependency("d0"), tasks[0], comms[0]);
		application.addEdge(new Dependency("d1"), comms[0], tasks[1]);
		application.addEdge(new Dependency("d2"), tasks[1], comms[1]);
		application.addEdge(new Dependency("d3"), tasks[1], comms[2]);
		application.addEdge(new Dependency("d4"), comms[1], tasks[2]);
		application.addEdge(new Dependency("d5"), comms[2], tasks[3]);
		application.addEdge(new Dependency("d6"), tasks[2], comms[3]);
		application.addEdge(new Dependency("d7"), tasks[3], comms[4]);
		application.addEdge(new Dependency("d8"), comms[3], tasks[4]);
		application.addEdge(new Dependency("d9"), comms[3], tasks[5]);
		application.addEdge(new Dependency("d10"), comms[4], tasks[4]);
		application.addEdge(new Dependency("d11"), comms[4], tasks[5]);

		return application;
	}

	/**
	 * Generates the architecture graph
	 * 
	 * @return the architecture graph
	 */
	private static Architecture<Resource, Link> generateArchitecture() {
		/*
		 * The architecture is defined by resources that can be linked (linked
		 * resources are considered to have a way to communicate). Note that it
		 * is possible to set attributes to each resources like the costs inthis
		 * case. Attributes might be integers, doubles, or strings. It is also
		 * possible to set attributes of tasks, mappings, etc.
		 */
		Architecture<Resource, Link> architecture = new Architecture<Resource, Link>();

		// create resources
		Resource[] res = {
				new Resource("r0"),
				new Resource("r1"),
				new Resource("r2"),
				new Resource("r3"),
				new Resource("r4"),
				new Resource("r5"),
				new Resource("r6"),
				new Resource("r7"),
		};

		// add resources to architecture
		for(Resource r : res) {
			architecture.addVertex(r);
		}

		// add resource links
		architecture.addEdge(new Link("l0"), res[0], res[2]);
		architecture.addEdge(new Link("l1"), res[0], res[2]);
		architecture.addEdge(new Link("l3"), res[1], res[2]);
		architecture.addEdge(new Link("l4"), res[1], res[2]);
		architecture.addEdge(new Link("l5"), res[3], res[4]);
		architecture.addEdge(new Link("l6"), res[4], res[5]);
		architecture.addEdge(new Link("l7"), res[3], res[5]);
		architecture.addEdge(new Link("l8"), res[3], res[2]);
		architecture.addEdge(new Link("l9"), res[4], res[2]);
		architecture.addEdge(new Link("l10"), res[5], res[2]);
		architecture.addEdge(new Link("l11"), res[6], res[2]);
		architecture.addEdge(new Link("l12"), res[7], res[2]);

		return architecture;
	}

	/**
	 * Generates the mapping edges
	 * 
	 * @param arch the architecture graph
	 * @param appl the application graph
	 * @return the mapping edges
	 */
	private static Mappings<Task, Resource> generateMappings(Architecture<Resource, Link> arch,
			Application<Task, Dependency> appl) {
		/*
		 * The mappings define how tasks are mapped to resources. For a
		 * specification is it possible to define more than one possible mapping
		 * for a task such that the optimization selects the optimal mapping.
		 */
		Mappings<Task, Resource> mappings = new Mappings<Task, Resource>();

		List<Mapping<Task, Resource>> map = Arrays.asList(
				new Mapping<>("m0", appl.getVertex("t0"), arch.getVertex("r0")),
				new Mapping<>("m1", appl.getVertex("t0"), arch.getVertex("r1")),
				new Mapping<>("m2", appl.getVertex("t1"), arch.getVertex("r6")),
				new Mapping<>("m3", appl.getVertex("t1"), arch.getVertex("r7")),
				new Mapping<>("m4", appl.getVertex("t1"), arch.getVertex("r2")),
				new Mapping<>("m5", appl.getVertex("t1"), arch.getVertex("r1")),
				new Mapping<>("m6", appl.getVertex("t2"), arch.getVertex("r2")),
				new Mapping<>("m7", appl.getVertex("t3"), arch.getVertex("r7")),
				new Mapping<>("m8", appl.getVertex("t4"), arch.getVertex("r2")),
				new Mapping<>("m9", appl.getVertex("t5"), arch.getVertex("r3"))
		);

		for(Mapping<Task, Resource> m : map) {
			mappings.add(m);
		}

		return  mappings;
	}

}
