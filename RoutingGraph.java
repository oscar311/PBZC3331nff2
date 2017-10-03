import java.util.*;

/**
 * Concrete generic graph represention
 */

public class RoutingGraph<E> implements Graph<E> {

    private List< Node<E> > nodeList;

    /** 
     * Constructor for the graph
     * 
     * @post new instance of the graph will be created
     */
    public RoutingGraph() {
        this.nodeList = new LinkedList<Node<E>>();
    }

    @Override
    public LinkedList<Node<E>> getNodeList() {
        return (LinkedList<Node<E>>) this.nodeList;
    }

    /** 
     * adds a node to the graph
     * 
     * @param node - label to identify the node
     *        nodeCost - the unloading cost of the node
     * @pre node != null, nodeCost >= 0
     * @post nodeList is update with new node
     */
    @Override
    public void addNode(E node) {
        // since "node" is a arbitary object of type E
        // we have to first create a new node instance then add
        // this adds a node - where "node" is the name and with a list of neighbours
        this.nodeList.add( new Node<E>(node) );
    }


    /** 
     * adds a edge to the graph
     * 
     * @param node1 - label for the start node of the edge
     *        node2 - label for the end node of the edge
     *        edgeCost - the weighting of the node, in this case the travel cost
     * @pre node1 and node2 exists within the graph, edgeCost > 0
     * @post the connections are added to the adjancey list of the nodes, undirected 
     */
    @Override
    public void addEdge(E node1, E node2, int edgeCost1, int edgeCost2) {
        Node<E> start = findNode(node1);
        Node<E> end = findNode(node2);

        start.addConnection(start,end,edgeCost1,edgeCost2);
        end.addConnection(end,start,edgeCost1,edgeCost2);
    }


    /** 
     * Searches the graph for the node, based on the given label
     * 
     * @param name - label to identify node
     * @return either the node, if found, or null if not found
     */
    @Override
    public Node<E> findNode(E name) {

        for(Node<E> node : this.nodeList) {
            if(Objects.equals(node.getName(),name)) return node;
        }
        return null;
    }

    /** 
     * Searches the graph for a specified edge
     * 
     * @param n1 - label to identify start node
     *        n2 - label to identify end node
     * @return either the edge, if found, or null if not found
     */
    @Override
    public Edge<E> findEdge(E n1, E n2) {
        Node<E> start = findNode(n1);
        Node<E> end = findNode(n2);

        for(Edge<E> e : start.getConnections() ) {
            if( Objects.equals(e.getEnd().getName(),end.getName()) ) {
                return e;
            }
        }
        return null;
    }

    @Override
    public void printGraph() {

        for(Node<E> node : this.nodeList) {

            System.out.print("Node: " + node.getName()
                                + " connections -> ");

            for(Edge<E> conn : node.getConnections()) {            
                System.out.print(conn.getEnd().getName() +" ");
            }

            System.out.println();
        }

        return;
    }
}