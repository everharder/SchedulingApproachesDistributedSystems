package at.uibk.dps.sds.t3.example;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import net.sf.opendse.model.Application;
import net.sf.opendse.model.Architecture;
import net.sf.opendse.model.Dependency;
import net.sf.opendse.model.Link;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Mappings;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Specification;
import net.sf.opendse.model.Task;
import net.sf.opendse.optimization.SpecificationWrapper;

/**
 * Wrapper for the specification used as example for SAT constraints in the lecture. 
 * 
 * @author Fedor Smirnov
 */
@Singleton
public class ExampleSpecWrapper implements SpecificationWrapper{

	public static final String costAttrName = "costs [$]";
	
	protected final Specification spec;
	
	@Inject
	public ExampleSpecWrapper() {
		this.spec = generateSpec();
	}
	
	
	
	
	/**
	 * Generates the specification
	 * 
	 * @return the generated specification
	 */
	protected Specification generateSpec() {
		Application<Task, Dependency> appl = new Application<>();
		Task t0 = new Task("t0");
		Task t1 = new Task("t1");
		appl.addVertex(t0);
		appl.addVertex(t1);
		
		Architecture<Resource, Link> arch = new Architecture<>();
		Resource r0 = new Resource("r0");
		Resource r1 = new Resource("r1");
		Resource r2 = new Resource("r2");
		Resource r3 = new Resource("r3");
		arch.addVertex(r0);
		arch.addVertex(r1);
		arch.addVertex(r2);
		arch.addVertex(r3);
		
		Mappings<Task, Resource> mappings = new Mappings<>();
		
		Mapping<Task, Resource> m0 = new Mapping<Task, Resource>("m0", t0, r0);
		m0.setAttribute(costAttrName, 6);
		mappings.add(m0);
		Mapping<Task, Resource> m1 = new Mapping<Task, Resource>("m1", t0, r1);
		m1.setAttribute(costAttrName, 2);
		mappings.add(m1);
		Mapping<Task, Resource> m2 = new Mapping<Task, Resource>("m2", t0, r2);
		m2.setAttribute(costAttrName, 1);
		mappings.add(m2);
		Mapping<Task, Resource> m3 = new Mapping<Task, Resource>("m3", t0, r3);
		m3.setAttribute(costAttrName, 2);
		mappings.add(m3);
		
		Mapping<Task, Resource> m4 = new Mapping<Task, Resource>("m4", t1, r0);
		m4.setAttribute(costAttrName, 2);
		mappings.add(m4);
		Mapping<Task, Resource> m5 = new Mapping<Task, Resource>("m5", t1, r1);
		m5.setAttribute(costAttrName, 5);
		mappings.add(m5);
		Mapping<Task, Resource> m6 = new Mapping<Task, Resource>("m6", t1, r2);
		m6.setAttribute(costAttrName, 1);
		mappings.add(m6);
		Mapping<Task, Resource> m7 = new Mapping<Task, Resource>("m7", t1, r3);
		m7.setAttribute(costAttrName, 4);
		mappings.add(m7);
		
		return new Specification(appl, arch, mappings);
	}

	@Override
	public Specification getSpecification() {
		return this.spec;
	}
	
}
