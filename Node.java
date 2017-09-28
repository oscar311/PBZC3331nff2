import java.util.*;

/**
 * Node class - node is a generic type E
 * -> weight is unloading cost
 */

public class Node<E> {

    private E name;
    private List<Edge<E>> connections;

   
    /** 
     * 
     * 
     * @param 
     * @pre 
     * @post
     * @return
     */
    public Node(E name) {
        this.name = name;
        this.connections = new LinkedList<Edge<E>>();

        /* Cost are initialised to 0
           then are set when explored */
    }

    /* === Getters === */

    /** 
     * gets the name of the node
     * 
     * @return name of the node 
     */
    public E getName() {
        return this.name;
    }

    /** 
     * gets the adjancey list of the node
     * 
     * @return list of edges connected to node
     */
    public List<Edge<E>> getConnections() {
        return this.connections;
    }

    /* === Methods === */

    /** 
     * adds a connection into the nodes in the adjancey list
     * 
     * @param start - label for the start node of the edge 
     *        end - label for the end node of the edge
     *        edgeWeight - the weighting of the edge
     * @pre node1 and node2 exists within the graph, edgeCost > 0
     * @post the connections are added to the adjancey list of the node 
     */
    public void addConnection(Node<E> start, Node<E> end, int edgeWeight1, int edgeWeight2) {
        this.getConnections().add( new Edge<E>(start,end,edgeWeight1,edgeWeight2) );
    }

    public boolean isAdjacent(Node<E> n) {
        for(Edge<E> e : this.getConnections()) {
            if(Objects.equals(e.getEnd().getName(), n.getName())) {
                return true;
            }
        }

        return false;
    }
}
