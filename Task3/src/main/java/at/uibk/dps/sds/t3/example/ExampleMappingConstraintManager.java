package at.uibk.dps.sds.t3.example;

import com.google.inject.Inject;

import net.sf.opendse.encoding.mapping.MappingConstraintGenerator;
import net.sf.opendse.encoding.mapping.MappingConstraintManager;
import net.sf.opendse.model.properties.ProcessPropertyService.MappingModes;

/**
 * You can ignore this class.
 * 
 * @author Fedor Smirnov
 *
 */
public class ExampleMappingConstraintManager implements MappingConstraintManager {

	protected final ExampleMappingEncoding exampleEncoding;

	@Inject
	public ExampleMappingConstraintManager(ExampleMappingEncoding exampleEncoding) {
		this.exampleEncoding = exampleEncoding;
	}

	@Override
	public MappingConstraintGenerator getMappingConstraintGenerator(MappingModes mappingMode) {
		return exampleEncoding;
	}
}
