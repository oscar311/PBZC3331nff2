import java.util.*;


/**
 * Freight System - Generic Edge class, represents edges with a starting node and ending node
 */

public class Edge<E> {  
    
    private int edgeCost1;
    private int edgeCost2;
    private Node<E> start;
    private Node<E> end;

    private int numOfConnections;

    /** 
     * Edge constructor
     * 
     * @param start - starting node
     *        end - ending node
     *        edgeCost - the cost of the edge
     * @pre start != null, end != null, edgeCost > 0
     * @post a instance of the edge class is initialised
     */
    public Edge(Node<E> start, Node<E> end, int edgeCost1, int edgeCost2) {
        this.edgeCost1 = edgeCost1;
        this.edgeCost2 = edgeCost2;
        this.start = start;
        this.end = end;

        this.numOfConnections = 0;
    }
    
    /* === Getters === */

    /** 
     * Gets the edges cost 
     * 
     * @pre this instance of the class has been initialised 
     * @return edge cost of this instance
     */
    public int getEdgeCost1() {
        return this.edgeCost1;
    }

    /** 
     * Gets the edges cost 
     * 
     * @pre this instance of the class has been initialised 
     * @return edge cost of this instance
     */
    public int getEdgeCost2() {
        return this.edgeCost2;
    }

    public int getNumOfConnections() {
        return this.numOfConnections;
    }

    /** 
     * Gets the node at the start of the edge 
     * 
     * @pre this instance of the class has been initialised 
     * @return start node
     */
    public Node<E> getStart() {
        return this.start;
    }

    /** 
     * Gets the node at the end of the edge 
     * 
     * @pre this instance of the class has been initialised 
     * @return end node
     */
    public Node<E> getEnd() {
        return this.end;
    }


    /* === Setters === */

    public void setNumOfConnections(int x) {
        this.numOfConnections = x;
    }

    /* === Methods === */

    /** 
     * Equals method
     * 
     * @param obj - object to be compared
     * @return true or false depending on whether the starting and ending nodes are equal
     */
    @Override
    public boolean equals(Object obj){
        // self check
        if(this == obj) return true;
        // null check
        if(obj == null) return false;
        // type check and cast
        if(getClass() != obj.getClass()) return false;
        Edge<?> o = (Edge<?>) obj; 
        
        // field comparison
        if ( this.getStart().equals(o.getStart()) && this.getEnd().equals(o.getEnd()) ) return true;
        return false;
    }

}
