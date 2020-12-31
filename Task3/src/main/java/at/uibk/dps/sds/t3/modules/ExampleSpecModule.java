package at.uibk.dps.sds.t3.modules;

import at.uibk.dps.sds.t3.example.ExampleSpecWrapper;
import net.sf.opendse.optimization.SpecificationWrapper;
import net.sf.opendse.optimization.io.IOModule;

/**
 * The {@link ExampleSpecModule} binds the {@link ExampleSpecWrapper} spec wrapper.
 * 
 * @author Fedor Smirnov
 *
 */
public class ExampleSpecModule extends IOModule{

	@Override
	protected void config() {
		bind(SpecificationWrapper.class).to(ExampleSpecWrapper.class);
	}
}
