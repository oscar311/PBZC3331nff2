import java.util.*;

/**
 * Generic Graph Interface
 * 
 * nodes - defined as arbitary objects 
 * edges - a binary relation between two objects with weights
 */

public interface Graph<E> {

    /** 
     * adds a node to the graph
     * 
     * @param node - label to identify the node
     *        nodeCost - the unloading cost of the node
     * @pre node != null, nodeCost >= 0
     * @post nodeList is update with new node
     */
    void addNode(E node);
    
    /** 
     * adds a edge to the graph
     * 
     * @param node1 - label for the start node of the edge
     *        node2 - label for the end node of the edge
     *        edgeCost - the weighting of the edge
     * @pre node1 and node2 exists within the graph, edgeCost > 0
     * @post the connections are added to the adjancey list of the node, undirected 
     */
    void addEdge(E node1, E node2, int propDelay, int capacity);
    
    /** 
     * Searches the graph for a specific node
     * 
     * @param name - label to identify node
     * @return either the node, if found, or null if not found
     */
    Node<E> findNode(E name);

    Edge<E> findEdge(E n1, E n2);

    void printGraph(); 

    LinkedList<Node<E>> getNodeList();


}