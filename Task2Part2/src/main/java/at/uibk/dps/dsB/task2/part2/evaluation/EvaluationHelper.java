package at.uibk.dps.dsB.task2.part2.evaluation;

import at.uibk.dps.dsB.task2.part2.properties.PropertyProvider;
import at.uibk.dps.dsB.task2.part2.properties.PropertyProviderStatic;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;

public class EvaluationHelper {

    private static final String ITERATIVE_PEOPLE  = "ITERATIVE_PEOPLE";
    private static final String ITERATIVE_CARS   = "ITERATIVE_CARS";
    private static final String TYPE_ATTRIBUTE_NAME   = "TYPE";
    private static final String COST_ATTRIBUTE_NAME   = "COST";
    private static final String COST_ATTRIBUTE_CLOUD   = "CLOUD";

    private PropertyProvider propertyProvider = new PropertyProviderStatic();

    // check how often the given task needs to be executed
    public int getNumberOfExecutions(Task t) {
        Object type = t.getAttribute(TYPE_ATTRIBUTE_NAME);

        if(type == ITERATIVE_CARS) {
            // number of execution = number of cars
            return propertyProvider.getCarNumber();
        } else if(type == ITERATIVE_PEOPLE) {
            // number of execution = number of people
            return propertyProvider.getNumberOfPeople();
        }
        // number of execution = 1
        return 1;
    }

    // check how many instances of resource r are available
    // returns 1 for non-cloud resources
    public int getNumberOfAvailableInstances(Resource r) {
        if(!isCloudResource(r)) {
            // non-cloud resources are executed once
            return 1;
        }
        return propertyProvider.getNumberOfAvailableInstances(r);
    }

    // check whether resource r is a cloud resource
    public boolean isCloudResource(Resource r) {
        Object resourceType = r.getAttribute(TYPE_ATTRIBUTE_NAME);
        return resourceType != null && resourceType.toString().equals(COST_ATTRIBUTE_CLOUD);
    }

    // get cost attribute of resource r
    public double getCost(Resource r) {
        // get cost attribute of resource
        Object costAttribute = r.getAttribute(COST_ATTRIBUTE_NAME);
        if(costAttribute == null) {
            return 0;
        }

        // parse cost attribute
        return Double.parseDouble(costAttribute.toString());
    }

    // get execution time of mapping m
    public double getExecutionTime(Mapping<Task, Resource> m) {
        // delegate to propertyprovider
        return propertyProvider.getExecutionTime(m);
    }
}
