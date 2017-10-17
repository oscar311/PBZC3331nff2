import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/** 
 * RouterAlgo
 */

public class RouterAlgo<E> {

    private Graph<E> g;

    private int hops;

    private LinkedList<E> path;

    public RouterAlgo(Graph<E> g) {
        this.g = g;
        this.hops = 0;
        this.path = new LinkedList<E>();
    }

    /**
     * Dijkstra’s algorithm using a link cost of 1 for each hop traversed.
     */
    public boolean shortestHopPath(E start, E end) {

        // initialization

        /*
        2 N' = {u}
        3 for all nodes v
        4 if v adjacent to u
        5 then D(v) = c(u,v)
        6 else D(v) = ∞
        */

        int c = 1; // c(u,v) is 1 for shortest hop path

        PriorityBlockingQueue< State<E> > open = new PriorityBlockingQueue< State<E> >(1000, new StateComparator<E>() );
        List< State<E> > closed = new LinkedList< State<E> >();

        State<E> s = new State<E>(this.g.findNode(start),null);
        
        closed.add(s);

        for(Node<E> node : this.g.getNodeList()) {

            State<E> curr = new State<E>(node,s);
            // if adjacent set D(v) = 1
            if (node.isAdjacent(this.g.findNode(start))) {
                curr.setDistFromStart(c);
            }
            // else D(v) = ∞

            if (!closed.contains(curr)) open.add(curr);
        }

        System.out.println("Start printing");


        /*while(!open.isEmpty()) {
            State<E> currt = open.poll();
            System.out.print("[" + currt.getNode().getName() + "] = "+currt.getDistFromStart() +", ");
        }
        System.out.println();
        */

        /*
        8 Loop
            9 find w not in N' such that D(w) is a minimum
            10 add w to N'
            11 update D(v) for all v adjacent to w and not in N' :
            12 D(v) = min( D(v), D(w) + c(w,v) )
            13      new cost to v is either old cost to v or known
            14 shortest path cost to w plus cost from w to v 
        15 until all nodes in N'
        */

        State<E> curr = null;
        while(!open.isEmpty()) {
            curr = open.poll();

            closed.add(curr);

            if(Objects.equals(curr.getName(),end)) {
                break;
            }

            for(Edge<E> e : curr.getConnections()) {
                State<E> next = new State<E>(e.getEnd(),curr);
                if(!closed.contains(next)) {
                    
                    // if adjacent set D(v) = 1

                    //System.out.print("[" + next.getName() + "]");


                    int newDist = curr.getDistFromStart() + c;
                    if(newDist < next.getDistFromStart()) {
                        next.setDistFromStart(newDist);
                    }

                    open.add(next);
                }
            }

            

        }

        int i = 1;
        System.out.print("[" + start + "]");
        path.add(start);
        for(Node<E> ed : constructPath(curr)) {
            System.out.print("[" + ed.getName() + "]");
            path.add(ed.getName());
            i++;
        }

        this.hops = i;

        /*for(State<E> ed : closed) {
            System.out.print("[" + ed.getName() + "]");
        }*/
        System.out.println();


        return true;
    }



    /**
     * The Shortest Delay Path (SDP)
     * Dijkstra’s algorithm where the link cost is the prop delay
     */
    public boolean shortestDelayPath(E start, E end) {

        // initialization

        /*
        2 N' = {u}
        3 for all nodes v
        4 if v adjacent to u
        5 then D(v) = c(u,v)
        6 else D(v) = ∞
        */

        PriorityBlockingQueue< State<E> > open = new PriorityBlockingQueue< State<E> >(1000, new StateComparator<E>() );
        List< State<E> > closed = new LinkedList< State<E> >();

        State<E> s = new State<E>(this.g.findNode(start),null);
        
        closed.add(s);

        for(Node<E> node : this.g.getNodeList()) {

            State<E> curr = new State<E>(node,s);
            // if adjacent set D(v) = 1
            if (node.isAdjacent(this.g.findNode(start))) {
                curr.setDistFromStart( this.g.findEdge(start,node.getName()).getEdgeCost1() );
            }
            // else D(v) = ∞

            if (!closed.contains(curr)) open.add(curr);
        }

        System.out.println("Start printing");


        /*while(!open.isEmpty()) {
            State<E> currt = open.poll();
            System.out.print("[" + currt.getNode().getName() + "] = "+currt.getDistFromStart() +", ");
        }
        System.out.println();
        */

        /*
        8 Loop
            9 find w not in N' such that D(w) is a minimum
            10 add w to N'
            11 update D(v) for all v adjacent to w and not in N' :
            12 D(v) = min( D(v), D(w) + c(w,v) )
            13      new cost to v is either old cost to v or known
            14 shortest path cost to w plus cost from w to v 
        15 until all nodes in N'
        */

        State<E> curr = null;
        while(!open.isEmpty()) {
            curr = open.poll();

            closed.add(curr);

            if(Objects.equals(curr.getName(),end)) {
                break;
            }

            for(Edge<E> e : curr.getConnections()) {
                State<E> next = new State<E>(e.getEnd(),curr);
                if(!closed.contains(next)) {
                    
                    // if adjacent set D(v) = 1

                    //System.out.print("[" + next.getName() + "]");


                    int newDist = curr.getDistFromStart() + e.getEdgeCost1();
                    if(newDist < next.getDistFromStart()) {
                        next.setDistFromStart(newDist);
                    }

                    open.add(next);
                }
            }

            

        }

        int i = 1;
        System.out.print("[" + start + "]");
        path.add(start);
        for(Node<E> ed : constructPath(curr)) {
            System.out.print("[" + ed.getName() + "]");
            path.add(ed.getName());
            i++;
        }
        this.hops = i;

        /*for(State<E> ed : closed) {
            System.out.print("[" + ed.getName() + "]");
        }*/
        System.out.println();


        return true;
    }


    public List<Node<E>> constructPath(State<E> state) {


        LinkedList<Node<E>> path = new LinkedList<Node<E>>();

        State<E> curr = state;

        while(curr.getParent() != null) {

            path.addFirst(curr.getNode());
            curr = curr.getParent();
        }
        return (List<Node<E>>) path;

    }


    public int getHops() {
        return this.hops;
    }

    public void sendThroughPath() {
        for(int i = 0; i < this.path.size(); i++) {
            

            try {
                E n1 = this.path.get(i);
                E n2 = this.path.get(i+1);
                Edge<E> e = this.g.findEdge(n1,n2);
                e.setNumOfConnections(e.getNumOfConnections() + 1);

                System.out.println(n1+" " +n2 +" "+"Active: " + e.getNumOfConnections());

            } catch (IndexOutOfBoundsException e) {

            }
        }
    }

    public void clearThroughPath() {
        for(int i = 0; i < this.path.size(); i++) {
            
            try {
                E n1 = this.path.get(i);
                E n2 = this.path.get(i+1);
                Edge<E> e = this.g.findEdge(n1,n2);
                e.setNumOfConnections(e.getNumOfConnections() - 1);

                System.out.println(n1+" " +n2 +" "+"Active: " + e.getNumOfConnections());

            } catch (IndexOutOfBoundsException e) {

            }
        }
    }


}