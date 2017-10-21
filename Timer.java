public class Timer {

    private static double initialTime;

    public Timer() {

        initialTime = System.nanoTime() / 1000;

    }

    public double getElapsedTime() {

        double currentTime = System.nanoTime() / 1000;
        return currentTime - initialTime;

    }

}
