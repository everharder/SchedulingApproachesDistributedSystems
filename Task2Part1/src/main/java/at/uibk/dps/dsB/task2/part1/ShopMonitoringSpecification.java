package at.uibk.dps.dsB.task2.part1;

import net.sf.opendse.io.SpecificationReader;
import net.sf.opendse.io.SpecificationWriter;
import net.sf.opendse.model.Specification;
import net.sf.opendse.visualization.SpecificationViewer;

/**
 * The {@link ShopMonitoringSpecification} is the class used to practice the
 * creation, the visualization, and the persistent storage of
 * {@link Specification} graphs.
 * 
 * @author Fedor Smirnov
 *
 */
public final class ShopMonitoringSpecification {

	// Set this to true to visualize the specification
	private static final boolean VISUALIZE_SPECIFICATION = true;
	// Set this to true to create the specification using the SpecificationGenerator
	// (otherwise the specification is read from the specified file)
	private static final boolean CREATE_SPEC_FROM_CODE = true;
	// Set this to true if the created specification should be saved to the 
	// specified location.
	private static final boolean SAVE_SPEC_TO_FILE = true;
	// The path to the spec folder (TODO: you may have to adjust this if you work on
	// Windows)
	private static final String SPEC_FOLDER_PATH = "./specs/";
	// File name for saving
	private static final String FILE_NAME = "customerMonitoringSpec.xml";

	private ShopMonitoringSpecification() {
	}

	public static void main(String[] args) {
		String specFilePath = SPEC_FOLDER_PATH + FILE_NAME;
		Specification spec = null;
		if (CREATE_SPEC_FROM_CODE) {
			spec = SpecificationGenerator.generate();
		}else {
			SpecificationReader reader = new SpecificationReader();
			spec = reader.read(specFilePath);
		}
		if (VISUALIZE_SPECIFICATION) {
			// visualizing the spec
			SpecificationViewer.view(spec);
		} 
		if (SAVE_SPEC_TO_FILE){
			// saving the specification as .xml
			SpecificationWriter writer = new SpecificationWriter();
			writer.write(spec, SPEC_FOLDER_PATH + FILE_NAME);
		}
	}
}
