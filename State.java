import java.util.*;

/**
 * RoutingPerformance - Generic State Class
 */

public class State<E> {

    private Node<E> node;
    private State<E> parent;

    private int distFromStart;


    public State(Node<E> node, State<E> parent) {
        this.node = node;
        this.parent = parent;

        this.distFromStart = Integer.MAX_VALUE;

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

    /* === Setters === */

    public void setDistFromStart(int distFromStart) {
        this.distFromStart = distFromStart;
    }

}