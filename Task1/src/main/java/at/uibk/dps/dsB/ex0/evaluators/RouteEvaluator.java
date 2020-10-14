package at.uibk.dps.dsB.ex0.evaluators;

import at.uibk.dps.dsB.ex0.containers.RoutePhenotype;
import org.opt4j.core.Objective;
import org.opt4j.core.Objective.Sign;
import org.opt4j.core.Objectives;
import org.opt4j.core.problem.Decoder;
import org.opt4j.core.problem.Evaluator;

/**
 * The {@link Evaluator} class which will be used to evaluate the phenotypes
 * returned by the {@link Decoder}.
 * 
 * @author Fedor Smirnov
 *
 */
public class RouteEvaluator implements Evaluator<RoutePhenotype> {

	@Override
	public Objectives evaluate(RoutePhenotype phenotype) {
		Objectives objectives = new Objectives();

		// search the route with the minimum distance
		objectives.add("objective", Sign.MIN, phenotype.getDistance());

		return objectives;
	}
}
