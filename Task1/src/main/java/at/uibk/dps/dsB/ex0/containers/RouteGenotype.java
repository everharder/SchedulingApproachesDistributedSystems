package at.uibk.dps.dsB.ex0.containers;

import org.opt4j.core.Genotype;
import org.opt4j.core.genotype.IntegerGenotype;
import org.opt4j.core.genotype.ListGenotype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// holds the route
public class RouteGenotype extends ArrayList<Node> implements ListGenotype<Node> {

    public RouteGenotype(List<Node> nodes) {
        super();
        this.addAll(nodes);
    }

    @Override
    public RouteGenotype newInstance() {
        return new RouteGenotype(this);
    }
}
