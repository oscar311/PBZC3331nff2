import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

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
    private static int    packetRate;

    private static Graph<String> graph;

    public static void main(String[] args) throws Exception  {

        if (args.length != 5) {

            System.out.println("Required arguments: NETWORK_SCHEME ROUTING_SCHEME TOPOLOGY_FILE WORKLOAD_FILE PACKET_RATE");
            return;

        }

        // networkScheme will takes values CIRCUIT or PACKET
        // corresponding to those network types, respectively.
        // circuit - same path for packets
        // packet - new path for each packet
        //        - evaluate routing protocol N times
        networkScheme = args[0];

        // routingScheme will take values SHP, SDP and LLP
        // corresponding to the 3 routing protocols: Shortest
        // Hop Path (SHP), Shortest Delay Path (SDP) and Least
        // Loaded Path (LLP), respectively
        routingScheme = args[1];

        // network topology specification
        // conn1 conn2 prop_delay virtual_circuits_can_accommodate
        // A     B     10         19
        // prop_delay = d; 0 < d < 200
        // capacities = C; 0 < C < 100
        topologyFile = args[2];

        // virtual connection requests in the network
        // start_time source dest time_alive
        // 0.123456   A      D    12.527453
        workloadFile = args[3];

        // number of packets per second which will be sent in each
        // virtual connection.
        packetRate = Integer.parseInt(args[4]);

        graph = new RoutingGraph<String>();

        buildGraph(topologyFile);

        graph.printGraph();

        scheduleWorkload(workloadFile);

    }

    private static void buildGraph(String file) {

        Scanner sc = null;

        try {

            sc = new Scanner(new FileReader(file));

            while(sc.hasNextLine()) {
                String[] p = sc.nextLine().split(" ");

                // source dest prop_delay capacity
                // A      B    10         19

                if(graph.findNode(p[0]) == null) graph.addNode(p[0]);
                if(graph.findNode(p[1]) == null) graph.addNode(p[1]);

                graph.addEdge(p[0],p[1],Integer.parseInt(p[2]),Integer.parseInt(p[3]));

            }

        } catch (FileNotFoundException e) {

        } finally {

            if (sc != null) sc.close();

        }
    }

    private static void scheduleWorkload(String file) {

        Scanner sc = null;

        try {

            sc = new Scanner(new FileReader(file));

            int    vcRequests     = 0;
            int    totalPackets   = 0;
            int    succPackets    = 0;
            int    blockedPackets = 0;
            double avgHops        = 0;
            int    cumDelay       = 0;

            int blockedRequests = 0;

            LinkedList<Tasker<String>> aliveThreads = new LinkedList<Tasker<String>>();

            Timer timer = new Timer();

            while(sc.hasNextLine()) {

                String[] p = sc.nextLine().split(" ");

                // start_time source dest time_alive
                // 0.123456   A      D    12.527453

                Tasker<String> tasker = new Tasker<String>(
                    timer,
                    Double.parseDouble(p[0])*1000000,
                    Double.parseDouble(p[3])*1000000,
                    graph,
                    p[1],
                    p[2],
                    routingScheme,
                    networkScheme,
                    packetRate
                );

                tasker.start();
                aliveThreads.add(tasker);
                vcRequests++;

            }

            while (!aliveThreads.isEmpty()) {

                if (aliveThreads.getFirst().join()) {

                    Tasker<String> temp = aliveThreads.poll();

                    // gather info
                    avgHops += temp.getHops();
                    totalPackets += temp.getPacketsSent();
                    blockedPackets += temp.getBlockedPackets();
                    blockedRequests += temp.getBlockedRequests();
                    cumDelay += temp.getCumDelay();

                }

            }

            avgHops = avgHops / vcRequests;
            succPackets = totalPackets - blockedPackets;
            cumDelay = cumDelay/(vcRequests - blockedRequests);

            DecimalFormat df = new DecimalFormat("#.##");

            System.out.println("total number of virtual circuit requests: " +
                               vcRequests + "\n" +
                               "total number of packets: " +
                               totalPackets + "\n" +
                               "number of successfully routed packets: " +
                               succPackets + "\n" +
                               "percentage of successfully routed packets: " +
                               df.format((double) succPackets / totalPackets *100) + "\n" +
                               "number of blocked packets: " +
                               blockedPackets + "\n" +
                               "percentage of blocked packets: " +
                               df.format((double) blockedPackets / totalPackets * 100) + "\n" +
                               "average number of hops per circuit: " +
                               df.format(avgHops) + "\n" +
                               "average cumulative propagation delay per circuit: " +
                               df.format(cumDelay) + "\n");

        } catch (FileNotFoundException e) {

        } finally {

            if (sc != null) sc.close();

        }
        
    }

}
