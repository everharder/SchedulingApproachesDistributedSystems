package at.uibk.dps.sds.t3.modules;

import at.uibk.dps.sds.t3.homework.HomeworkMappingConstraintManager;
import net.sf.opendse.encoding.mapping.MappingConstraintManager;
import net.sf.opendse.optimization.DesignSpaceExplorationModule;

/**
 * The mode which binds the classes implementing the secrecy encoding (to be
 * implemented as the homework).
 * 
 * @author Fedor Smirnov
 *
 */
public class HomeworkEncodingModel extends DesignSpaceExplorationModule{

	@Override
	protected void config() {
		bind(MappingConstraintManager.class).to(HomeworkMappingConstraintManager.class);
	}
}
