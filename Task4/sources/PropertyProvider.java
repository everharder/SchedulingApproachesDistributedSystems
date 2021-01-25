package at.uibk.dps.dsB.task2.part2.properties;

import com.google.inject.ImplementedBy;

import net.sf.opendse.model.Communication;
import net.sf.opendse.model.Link;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;

/**
 * 
 * The interface for the classes used to retrieve certain properties of the
 * specification elements.
 * 
 * @author Fedor Smirnov
 */
@ImplementedBy(PropertyProviderStatic.class)
public interface PropertyProvider {

	/**
	 * Returns the transmission time of one instance of the provided message on the
	 * provided link
	 * 
	 * @param comm the provided message
	 * @param l    the provided link
	 * @return the transmission time of one instance of the provided message on the
	 *         provided link
	 */
	double getTransmissionTime(Communication comm, Link l);

	/**
	 * Returns the execution time of the task-to-resources assignment described by
	 * the provided mapping.
	 * 
	 * @param mapping the given mapping
	 * @return the execution time of the task-to-resources assignment described by
	 *         the provided mapping
	 */
	double getExecutionTime(Mapping<Task, Resource> mapping);

	/**
	 * Returns the number of cars in the currently processed frame
	 * 
	 * @return the number of cars in the currently processed frame
	 */
	int getCarNumber();

	/**
	 * Returns the number of people in the currently processed frame
	 * 
	 * @return the number of people in the currently processed frame
	 */
	int getNumberOfPeople();

	/**
	 * Returns the number of available instances of the cloud resource type modeled
	 * by the provided resource
	 * 
	 * @param cloudResource the resource modeling a type of cloud resources
	 * @return the number of available instances of the cloud resource type modeled
	 *         by the provided resource
	 */
	int getNumberOfAvailableInstances(Resource cloudResource);
}
