package at.uibk.dps.sds.t3.homework.constraints;

import net.sf.opendse.encoding.variables.T;
import net.sf.opendse.model.Specification;
import org.opt4j.satdecoding.Constraint;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 
 * 
 * Add class comments
 *
 */
public class ConstraintService {

	/**
	 * Just as a tip: Since this is a static method container, you normally would
	 * want to make sure that no instance of this class can be instantiated. You
	 * would do this by (a) declaring the class final (so that you cannot define
	 * children) and (b) adding an empty private constructor.
	 * 
	 */

	/**
	 * In general, it is nice to have comments for every class and every method head
	 * 
	 */
	protected static final List<IConstraint> _constrainViolationCounters = Arrays.asList(
			new NoSecretsOnCloudConstraint(), new EnsureTaskMappingConstraint(),
			new MaximumTaskCountOnEdgeConstraint(2), new SecretCommunicationConstraint());

	/**
	 * 
	 * Per convention, Java method names are normally lower case. Also, with regard
	 * to the naming: getIConstraints() would be slightly better, since it otherwise
	 * sounds as if the method is returning {@link Constraint}s.
	 * 
	 */

	public static List<IConstraint> GetConstraints() {
		return _constrainViolationCounters;
	}

	/**
	 * For both methods below: very nice and elegant :)
	 * 
	 */
	
	
	public static int CountConstraintViolations(Specification implementation) {
		return ConstraintService.GetConstraints().stream().mapToInt(x -> x.CountConstraintViolations(implementation))
				.sum();
	}

	public static Set<Constraint> ToConstraints(Set<T> processVariables, Specification specification) {
		return ConstraintService.GetConstraints().stream()
				.flatMap(x -> x.ToConstraints(processVariables, specification).stream()).collect(Collectors.toSet());
	}
}
