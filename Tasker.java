import java.util.*;

public class Tasker implements Runnable {

    private Thread t;

    private long initialTimeMicro;
    private long delay;
    private long timeToLive;

    public Tasker(Long initialTimeMicro, Long delay, Long timeToLive) {
        
        this.t = null;

        this.initialTimeMicro = initialTimeMicro;
    
        this.delay = delay;
        this.timeToLive = timeToLive;
    }


    @Override 
    public void run() {
        try {
            while(true) {

                Long timeD = System.nanoTime()/1000 - this.initialTimeMicro; 
                if( timeD >= this.delay) {

                    Thread thread = Thread.currentThread();

                    if (timeD == this.delay) {

                        System.out.println (thread.getId() + " Starting task..." + (double)timeD/1000000 );
                    }

                    //do task

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