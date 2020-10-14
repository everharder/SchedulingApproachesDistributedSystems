package at.uibk.dps.dsB.ex0.containers;

// represents a single node (stop) on the route
public class Node {
    private final String name;
    private final int posX;
    private final int posY;
    private final boolean isFinalNode;
    private final boolean isStartNode;

    public Node(String name, int posX, int posY) {
        this(name, posX, posY, false, false);
    }

    public Node(String name, int posX, int posY, boolean isStartNode, boolean isFinalNode) {
        this.name = name;
        this.posX = posX;
        this.posY = posY;
        this.isFinalNode = isFinalNode;
        this.isStartNode = isStartNode;
    }

    public String getName() {
        return this.name;
    }

    public boolean getIsFinalNode() {
        return this.isFinalNode;
    }

    public boolean getIsStartNode() {
        return this.isStartNode;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
