import java.io.*;
import java.util.*;

/** 
 * RoutingPerformance
 *
 * @author Oscar Downing (z5114817) and Tim Thacker ()
 * 
 * Usage 
 * $ javac RoutingPerformance.java 
 * $ java RoutingPerformance NETWORK_SCHEME ROUTING_SCHEME TOPOLOGY_FILE WORKLOAD_FILE PACKET_RATE 
 */


/*
    todo 


Focus first on making sure that your program can read in and model the network
topology. The 4-node example discussed earlier is a good one to start with.

Next focus on getting ONE routing algorithm working. The Shortest Hop Path
(SHP) is the easiest one, since it entails implementing Dijkstra’s algorithm using a
link cost of 1 for each hop traversed. Spend a lot of time testing your Dijkstra’s
algorithm to make sure that it is working properly. This will definitely pay off in
the long run.
Non-programming  assignment  is  allowed     as  an  exception   to  non-CSE     and    
non-EE&T    students    who do  not have    experience  with    programming
(e.g:   Mechatronics).          Note    that    non-CSE students    are encouraged  to  attempt 
the  programming     assignment.     Check   details     about   this    on  the     alternate  
assignment  page    on  OpenLearning.   You must    send    an  email   to  class   account 
indicating   that   you are opting   for    non-programming assignment  by  22nd    of  
September   2017,   12:00   PM  (noon). If  no  emails  is  received,   you are assumed 
to  have    opted   for standard    version of  the assignment.
Page 8 of 11

The Shortest Delay Path (SDP) is a simple variant of SHP and will involve a
simple modification (i.e. changing the link cost) to your implementation of SHP.
The Least Loaded Path (LLP) will require reasonable modifications but is still
relatively easy to implement.

Test your routing algorithm(s) with a SMALL network topology and a SMALL
virtual circuit workload file. Links with very low capacity (e.g. 1) are useful in
initial testing. Make sure that the routing is working, and that your program is able
to compute the required statistics.

Extend your program for the virtual packet network case and repeat all tests for all
three routing algorithms.

Once you are fairly confident that your program is working correctly for a small
topology, and then move on to the provided sample topology. It may be useful to
initially test out the workload file incrementally (e.g.: first 10 requests, first 50
requests, first 100 requests and so on)



*/


public class RoutingPerformance {

    private static String networkScheme;
    private static String routingScheme;
    private static String topologyFile;
    private static String workloadFile;
    private static int packetRate;

    public static void main(String[] args) throws Exception  {
        
        if(args.length != 5) {
            System.out.println("Required arguments: NETWORK_SCHEME ROUTING_SCHEME TOPOLOGY_FILE WORKLOAD_FILE PACKET_RATE");
            return;
        }

        // networkScheme will takes values CIRCUIT or PACKET 
        // corresponding to those network types, respectively.
        networkScheme = args[0];
        
        // routingScheme will take values SHP, SDP and LLP 
        // corresponding to the 3 routing protocols: Shortest 
        // Hop Path (SHP), Shortest Delay Path (SDP) and Least 
        // Loaded Path (LLP), respectively
        routingScheme = args[1];

        // network topology specification
        topologyFile = args[2];

        // virtual connection requests in the network
        workloadFile = args[3];

        // number of packets per second which will be sent in each 
        // virtual connection.
        packetRate = Integer.parseInt(args[4]);



    }
}
