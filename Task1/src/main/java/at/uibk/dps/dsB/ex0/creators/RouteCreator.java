package at.uibk.dps.dsB.ex0.creators;

import at.uibk.dps.dsB.ex0.containers.Node;
import at.uibk.dps.dsB.ex0.containers.RouteGenotype;
import org.opt4j.core.genotype.ListGenotype;
import org.opt4j.core.genotype.PermutationGenotype;
import org.opt4j.core.problem.Creator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * The {@link Creator} class which will be used to initialize the genotypes
 * encoding individual problem solutions.
 * 
 * @author Fedor Smirnov
 *
 */
public class RouteCreator implements Creator<RouteGenotype> {

	private final int maximumRouteLength;

	private final List<Node> nodes;

	public RouteCreator() {
		// generate the 'map' -> see route_layout.png
		this.nodes = Arrays.asList(
				new Node("START", 1, 0, true, false),
				new Node("S1",  2, 1),
				new Node("S2",  0, 2),
				new Node("S3",  4, 2),
				new Node("S4",  1, 3),
				new Node("S5",  3, 3),
				new Node("S6",  5, 3),
				new Node("S7",  5, 4),
				new Node("S8",  0, 5),
				new Node("S9",  4, 5),
				new Node("S10", 2, 6),
				new Node("DESTINATION", 5, 6, false, true)
		);
		this.maximumRouteLength = this.nodes.size() + 1;
	}

	@Override
	public RouteGenotype create() {
		Random random = new Random();
		int routeLength = random.nextInt(maximumRouteLength - 2) + 2;

		// generate a random route that makes sense(only travel from start to destination, no loops)
		return new RouteGenotype(this.selectNodesForRoute(routeLength));
	}

	private List<Node> selectNodesForRoute(int routeLength) {
		Random random = new Random();
		ArrayList<Node> tmpNodes = new ArrayList<>(this.nodes);
		ArrayList<Node> route = new ArrayList<>();

		// first node must be start node
		route.add(this.nodes.get(0));

		// each route needs to have a final node
		boolean hasFinalNode = false;

		// add routeLength - 1 nodes because the last node must be the final node
		while(route.size() < routeLength - 1) {
			// get a random node that has not been taken already
			Node current = tmpNodes.get(random.nextInt(tmpNodes.size()));

			// add to route
			route.add(current);

			// if the node is the final node we are already done
			if(current.getIsFinalNode()) {
				hasFinalNode = true;
				break;
			}

			Node finalCurrent = current;

			// remove all nodes that are in the wrong direction (leading to the start node) and the current one
			tmpNodes = new ArrayList<>(tmpNodes.stream().filter(x -> x != finalCurrent && x.getPosY() >= finalCurrent.getPosY()).collect(Collectors.toList()));
		}

		// if the final node has not been added yet, add it
		if(!hasFinalNode) {
			route.add(this.nodes.get(this.nodes.size()-1));
		}
		return route;
	}
}
