package at.uibk.dps.dsB.task2.part1;

import net.sf.opendse.model.Application;
import net.sf.opendse.model.Architecture;
import net.sf.opendse.model.Dependency;
import net.sf.opendse.model.Link;
import net.sf.opendse.model.Mappings;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Specification;
import net.sf.opendse.model.Task;

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
		// TODO implement this method
		throw new IllegalStateException("Not yet implemented.");
	}

	/**
	 * Generates the architecture graph
	 * 
	 * @return the architecture graph
	 */
	private static Architecture<Resource, Link> generateArchitecture() {
		// TODO implement this method
		throw new IllegalStateException("Not yet implemented.");
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
		// TODO implement this method
		throw new IllegalStateException("Not yet implemented.");
	}

}
