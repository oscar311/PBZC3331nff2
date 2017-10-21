import java.util.*;

/**
 * RoutingPerformance - Generic State Class
 */

public class State<E> {

    private Node<E>  node;
    private State<E> parent;
    private int      distFromStart;
    private double   percentageLoad;

    public State(Node<E> node, State<E> parent) {

        this.node = node;
        this.parent = parent;

        this.distFromStart  = Integer.MAX_VALUE;
        this.percentageLoad = Double.MAX_VALUE;

    }


    /* === Getters === */


    /**
     * gets the node of this instance of the state
     *
     * @return node
     */

    public Node<E> getNode() {
        return this.node;
    }

    /**
     * gets the parent state of this instance of state
     *
     * @return parent state
     */

    public State<E> getParent() {
        return this.parent;
    }

    /**
     * gets the name of the state node
     *
     * @return name of the state node
     */

    public E getName() {
        return this.getNode().getName();
    }

    /**
     * gets the adjancey list of the state node
     *
     * @return list of edges connected to state node
     */

    public List<Edge<E>> getConnections() {
        return this.getNode().getConnections();
    }

    public int getDistFromStart() {
        return this.distFromStart;
    }

    public double getPercentageLoad() {
        return this.percentageLoad;
    }

    /* === Setters === */

    public void setDistFromStart(int distFromStart) {
        this.distFromStart = distFromStart;
    }

    public void setPercentageLoad(double percentageLoad) {
        this.percentageLoad = percentageLoad;
    }

    /* === Methods === */

    /**
     * equals method - equal if names are equals
     *
     * @param obj - to be compared
     * @return true or false depending on whether the states names are equal
     */

    @Override
    public boolean equals(Object obj) {

        // self check
        if (this == obj) return true;

        // null check
        if (obj == null) return false;

        // type check and cast
        if (this.getClass() != obj.getClass()) return false;

        State<?> o = (State<?>) obj;

        // field comparison
        if (Objects.equals(this.getName(), o.getName())) return true;

        return false;

    }

}
