package Exercise_3_VEGA;

import java.util.Comparator;

/**
 * Created by Max Schrimpf
 */
public class FComparator implements Comparator<VEGAIndividuum> {

    /**
     * return zero if both objects are equal
     * return negative if object is worse than specified one
     * return positive if object is better than specified one
     */
    @Override
    public int compare(VEGAIndividuum o1, VEGAIndividuum o2) {
        if( o1.getFValue() > o2.getFValue()) {
            return -1;
        } else if( o1.getFValue() < o2.getFValue()) {
            return 1;
        }
        return 0;
    }
}
