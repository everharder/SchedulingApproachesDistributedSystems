package at.uibk.dps.dsB.ex0.modules;

import org.opt4j.core.problem.ProblemModule;

import at.uibk.dps.dsB.ex0.creators.RouteCreator;
import at.uibk.dps.dsB.ex0.decoders.RouteDecoder;
import at.uibk.dps.dsB.ex0.evaluators.RouteEvaluator;

/**
 * In general, Opt4J's modules are use to configure the binding of interfaces to
 * concrete classes. In this case, we are binding the Creator, Decoder, and
 * Evaluator interfaces to the classes defined throughout the Exercise0 project.
 * 
 * You don't have to alter anything within this class.
 * 
 * @author Fedor Smirnov
 */
public class RouteCalculationModule extends ProblemModule {
	@Override
	protected void config() {
		bindProblem(RouteCreator.class, RouteDecoder.class, RouteEvaluator.class);
	}
}
