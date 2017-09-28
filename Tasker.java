import java.util.*;

public class Tasker<E> implements Runnable {

    private Thread t;

    private long initialTimeMicro;
    private long delay;
    private long timeToLive;

    private Graph<E> g;
    private E start;
    private E end;

    public Tasker(Long initialTimeMicro, Long delay, Long timeToLive, Graph<E> g, E start, E end) {
        
        this.t = null;

        this.initialTimeMicro = initialTimeMicro;
    
        this.delay = delay;
        this.timeToLive = timeToLive;

        this.g = g;
        this.start = start;
        this.end = end;
    }


    @Override 
    public void run() {
        try {
            boolean done = false;
            while(true) {

                Long timeD = System.nanoTime()/1000 - this.initialTimeMicro; 
                if( timeD >= this.delay) {

                    Thread thread = Thread.currentThread();

                    if (timeD == this.delay) {

                        System.out.println (thread.getId() + " Starting task..." + (double)timeD/1000000 );
                    }

                    //do task

                    if(!done) {
                        RouterAlgo<E> r = new RouterAlgo<E>(this.g);
                        done = r.shortestHopPath(this.start, this.end);
                    }


                    timeD = System.nanoTime()/1000 - this.initialTimeMicro - this.delay; 
                    if( timeD >= this.timeToLive) {

                        System.out.println (thread.getId() + " Ending task..." + (double)timeD/1000000 );
                        Thread.sleep(10000);

                        break;
                    }   
                }   
                
                

            }
        
        } catch (InterruptedException e) {
             System.out.println("Thread interrupted.");
        }
    }


    public void start() {
        if(this.t == null) {
            this.t = new Thread(this,Long.toString(System.nanoTime()));
            this.t.start();
        }
    }

}