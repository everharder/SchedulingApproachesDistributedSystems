package at.uibk.dps.sds.t3.homework;

import com.google.inject.Inject;

import net.sf.opendse.encoding.mapping.MappingConstraintGenerator;
import net.sf.opendse.encoding.mapping.MappingConstraintManager;
import net.sf.opendse.model.properties.ProcessPropertyService.MappingModes;
import net.sf.opendse.optimization.SpecificationWrapper;

public class HomeworkMappingConstraintManager implements MappingConstraintManager {

	protected final HomeworkMappingEncoding homeWorkMappingEncoding;

	@Inject
	public HomeworkMappingConstraintManager(SpecificationWrapper specWrapper) {
		this.homeWorkMappingEncoding = new HomeworkMappingEncoding(specWrapper);
	}

	@Override
	public MappingConstraintGenerator getMappingConstraintGenerator(MappingModes mappingMode) {
		return homeWorkMappingEncoding;
	}
}
