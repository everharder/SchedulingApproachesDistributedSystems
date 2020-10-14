package at.uibk.dps.dsB.ex0.containers;

// holds the evaluated distance of the route
public class RoutePhenotype {
    private final double distance;
    private final String routeInfo;

    public RoutePhenotype(double distance, String routeInfo) {
        this.distance = distance;
        this.routeInfo = routeInfo;
    }

    public double getDistance() {
        return this.distance;
    }

    @Override
    public String toString() {
        return this.routeInfo;
    }
}
