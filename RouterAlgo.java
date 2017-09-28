import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/** 
 * RouterAlgo
 */

public class RouterAlgo<E> {

    private Graph<E> g;

    public RouterAlgo(Graph<E> g) {
        this.g = g;
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

        State<E> curr = new State<E>(this.g.findNode(start),null);
        curr.setDistFromStart(0);
        open.add(curr);

        for(Node<E> node : this.g.getNodeList()) {

            curr = new State<E>(node,null);
            // if adjacent set D(v) = 1
            if (node.isAdjacent(this.g.findNode(start))) {
                curr.setDistFromStart(c);
            }
            // else D(v) = ∞

            open.add(curr);
        }

        System.out.println("Start printing");


        for(State<E> s : open) {
            State<E> currt = open.poll();
            System.out.print("[" + currt.getNode().getName() + "] = "+currt.getDistFromStart() +", ");
        }
        System.out.println();


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


        return true;
    }

}