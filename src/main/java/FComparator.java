import java.util.Comparator;

/**
 * Created by Max Schrimpf
 */
public class FComparator implements Comparator<Individuum> {

    /**
     * return zero if both objects are equal
     * return negative if object is worse than specified one
     * return positive if object is better than specified one
     */
    @Override
    public int compare(Individuum o1, Individuum o2) {
        if( o1.getFValue() > o2.getFValue()) {
            return -1;
        } else if( o1.getFValue() < o2.getFValue()) {
            return 1;
        }
        return 0;
    }
}
