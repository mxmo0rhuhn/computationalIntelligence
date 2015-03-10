import java.util.Comparator;

/**
 * Created by Max Schrimpf
 */
public class GComparator implements Comparator<Individuum> {

    /**
     * return zero if both objects are equal
     * return negative if object is worse than specified one
     * return positive if object is better than specified one
     */
    @Override
    public int compare(Individuum o1, Individuum o2) {
        if( o1.getGValue() < o2.getGValue()) {
            return -1;
        } else if( o1.getGValue() > o2.getGValue()) {
            return 1;
        }
        return 0;
    }
}
