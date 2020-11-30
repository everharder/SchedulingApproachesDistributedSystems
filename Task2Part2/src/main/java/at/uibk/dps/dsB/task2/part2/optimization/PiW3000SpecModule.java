package at.uibk.dps.dsB.task2.part2.optimization;

import net.sf.opendse.optimization.SpecificationWrapper;
import net.sf.opendse.optimization.io.IOModule;

/**
 * Module which provides the specification during the optimization.
 * 
 * (Does not need to be altered throughput the exercise)
 * 
 * @author Fedor Smirnov
 *
 */
public class PiW3000SpecModule extends IOModule {

	@Override
	protected void config() {
		bind(SpecificationWrapper.class).to(SpecWrap.class).asEagerSingleton();
	}
}
