package at.uibk.dps.dsB.task4.evaluation;

import at.uibk.dps.dsB.task4.properties.PropertyProvider;
import at.uibk.dps.dsB.task4.properties.PropertyProviderDynamic;
import at.uibk.dps.dsB.task4.properties.PropertyService;
import net.sf.opendse.model.*;
import net.sf.opendse.model.properties.TaskPropertyService;

/*
This class is used as a Facade for accessing methods of the PropertyProvider, PropertyService and TaskPropertyService
 */
public class EvaluationHelper {

    // number of iterations that are used in order to calculate average cost and worst-case makespan
    protected final int iterationCount = 100;

    // the actual property provider
    protected final PropertyProvider propertyProvider = new PropertyProviderDynamic();

    public int getIterationCount() {
        if(iterationCount < 1) {
            throw new UnsupportedOperationException("iterationCount must not be smaller than 1!");
        }
        return iterationCount;
    }

    public double getLinkCost(Link link) {
        return PropertyService.getLinkCost(link);
    }

    public double getExecutionTime(Mapping<Task, Resource> m) {
        return propertyProvider.getExecutionTime(m);
    }

    public int getNumberOfExecutions(Task task) {
        if(PropertyService.isSingleExecTask(task)) {
            return 1;
        }
        if(PropertyService.isIterativeCars(task)) {
            // getCarNumber sometimes returns a value < 0, which cannot happen (we cannot detect a negative number of cars)
            return Math.max(0, propertyProvider.getCarNumber());
        }
        if(PropertyService.isIterativePeople(task)) {
            // getNumberOfPeople sometimes returns a value < 0, which cannot happen (we cannot detect a negative number of people)
            return Math.max(0, propertyProvider.getNumberOfPeople());
        }
        throw new UnsupportedOperationException("task is neither SingleExec, IterativeCars nor IterativePeople. Check specification!");
    }

    public double getResourceCosts(Resource resource) {
        return PropertyService.getResourceCosts(resource);
    }

    public boolean isCloudResource(Resource resource) {
        return PropertyService.isCloudResource(resource);
    }

    public boolean isCommunication(Task t) {
        return TaskPropertyService.isCommunication(t);
    }

    public boolean isProcess(Task t) {
        return TaskPropertyService.isProcess(t);
    }

    public int getNumberOfAvailableInstances(Resource resource) {
        // getNumberOfAvailableInstances sometimes returns a value < 1, which of course should not happen when renting cloud hardware
        // it is debatable whether we could return 0 here and evaluate the execution time to double.MAXVALUE
        return Math.max(1, propertyProvider.getNumberOfAvailableInstances(resource));
    }

    public double getTransmissionTime(Link link, Communication communication) {
        return propertyProvider.getTransmissionTime(communication, link);
    }
}
