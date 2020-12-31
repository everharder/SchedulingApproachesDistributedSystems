package at.uibk.dps.sds.t3.modules;

import org.opt4j.core.start.Constant;

import at.uibk.dps.sds.t3.example.ExampleMappingConstraintManager;
import at.uibk.dps.sds.t3.example.ExampleMappingEncoding;
import net.sf.opendse.encoding.mapping.MappingConstraintManager;
import net.sf.opendse.optimization.DesignSpaceExplorationModule;

/**
 * Module to switch to example/homework encoding of the mappings.
 * 
 * @author Fedor Smirnov
 *
 */
public class ExampleEncodingModule extends DesignSpaceExplorationModule {

	@Constant(value = "noResourceSharing", namespace = ExampleMappingEncoding.class)
	public boolean encodeNoResourceSharing;

	@Constant(value = "taskMappingNecessity", namespace = ExampleMappingEncoding.class)
	public boolean encodeTaskMappingNecessity;

	public boolean isEncodeNoResourceSharing() {
		return encodeNoResourceSharing;
	}

	public void setEncodeNoResourceSharing(boolean encodeNoTaskSharing) {
		this.encodeNoResourceSharing = encodeNoTaskSharing;
	}

	public boolean isEncodeTaskMappingNecessity() {
		return encodeTaskMappingNecessity;
	}

	public void setEncodeTaskMappingNecessity(boolean encodeTaskMappingNecessity) {
		this.encodeTaskMappingNecessity = encodeTaskMappingNecessity;
	}

	@Override
	protected void config() {
		bind(MappingConstraintManager.class).to(ExampleMappingConstraintManager.class);
	}
}
