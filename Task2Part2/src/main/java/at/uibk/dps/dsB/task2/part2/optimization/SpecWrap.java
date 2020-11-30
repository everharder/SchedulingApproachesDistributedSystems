package at.uibk.dps.dsB.task2.part2.optimization;

import com.google.inject.Inject;

import net.sf.opendse.io.SpecificationReader;
import net.sf.opendse.model.Specification;
import net.sf.opendse.optimization.SpecificationWrapper;

/**
 * Class which reads in the specification from the file.
 * 
 * (Does not need to be altered throughput the exercise)
 * 
 * @author Fedor Smirnov
 */
public class SpecWrap implements SpecificationWrapper{

	protected static final String filePath = "./specs/piw3000.xml";
	protected final Specification spec;
	
	@Inject
	public SpecWrap() {
		SpecificationReader reader = new SpecificationReader();
		this.spec = reader.read(filePath);
				
	}
	
	@Override
	public Specification getSpecification() {
		return this.spec;
	}
}
