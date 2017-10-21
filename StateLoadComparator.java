import java.util.Comparator;

/**
 * Freight System - State Comparator class, use in the priority queue of the A*
 */

public class StateLoadComparator<E> implements Comparator<State<E>> {

    /**
     * compared method
     *
     * @param s1 - a state to be compared
     *        s2 - the other state to be compared
     * @pre s1 != null, s2 != null
     * @return -1 or 1 depending on the f costs of the states
     */

    @Override
    public int compare(State<E> s1, State<E> s2) {

        double s1Estimate = s1.getPercentageLoad();
        double s2Estimate = s2.getPercentageLoad();

        int result = 0;

        if (s1Estimate <= s2Estimate) {
            result = -1;
        } else if (s1Estimate > s2Estimate) {
            result = 1;
        } else {
            result = 0;
        }

        return result;

    }

}
