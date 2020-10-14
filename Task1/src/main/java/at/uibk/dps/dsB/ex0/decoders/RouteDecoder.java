package at.uibk.dps.dsB.ex0.decoders;

import at.uibk.dps.dsB.ex0.containers.Constants;
import at.uibk.dps.dsB.ex0.containers.Node;
import at.uibk.dps.dsB.ex0.containers.RouteGenotype;
import at.uibk.dps.dsB.ex0.containers.RoutePhenotype;
import org.opt4j.core.genotype.PermutationGenotype;
import org.opt4j.core.problem.Decoder;

import java.util.stream.Collectors;

/**
 * The {@link Decoder} class which will be used to decode the genotypes, i.e.,
 * transform them into a representation which can be processed by the evaluator.
 * 
 * @author Fedor Smirnov
 *
 */
public class RouteDecoder implements Decoder<RouteGenotype, RoutePhenotype> {

	// calculate the distance from start to destination
	@Override
	public RoutePhenotype decode(RouteGenotype genotype) {

		double distance = 0;

		// if our route does not contain both start and destination our distance is Double.MAX_VALUE (invalid route)
		if(genotype.size() < 2
			|| !genotype.get(0).getIsStartNode()
			|| !genotype.get(genotype.size()-1).getIsFinalNode()) {
			distance = Double.MAX_VALUE;

		} else {
			for (int i = 0; i < genotype.size() - 1; i++) {

				// calculate distance to the next node
				double distanceToNextNode = calculateDistance(genotype.get(i), genotype.get(i + 1));

				// if the distance is too far to travel without a break the route is invalid
				if (distanceToNextNode > Constants.MaximumDistanceBeforeBreak) {
					distance = Double.MAX_VALUE;
					break;
				}

				// check if a break has to be taken before the route can be traveled
				if (distance + distanceToNextNode > Constants.MaximumDistanceBeforeBreak) {
					distance += Constants.BreakCost;
				}

				// add currentdistance to distance aggregation
				distance += distanceToNextNode;
			}
		}

		// create some route info for pretty output
		String routeInfo = String.join(" -> ", genotype.stream().map(x -> x.getName()).collect(Collectors.toList()));

		return new RoutePhenotype(distance, routeInfo);
	}

	private double calculateDistance(Node n1, Node n2) {
		return Math.sqrt(Math.pow(Math.abs(n1.getPosX() - n2.getPosX()), 2) + Math.pow(Math.abs(n1.getPosY() - n2.getPosY()), 2));
	}
}
