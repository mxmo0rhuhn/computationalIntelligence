package Exercise_3_VEGA;

import java.util.*;

/**
 * Created by Max Schrimpf
 */
public class Selector {

    public static List<VEGAIndividuum> RankBasedSelectionF2(List<VEGAIndividuum> in) {

        // sort results in ascending quality
        java.util.Collections.sort(in, new GComparator());

        // get sum of ranks
        float sum = 0;
        for(int i = 1; i <= in.size(); i++) {
            sum += i;
        }

        List<Float> ranges = new ArrayList<Float>();

        // creates a List of upper borders for the ranges
        float rank = 0;
        float range = 0;
        float accumulated = 0;
        for (VEGAIndividuum i : in)  {
            rank ++;
            range = rank/sum;
            accumulated += range;
            ranges.add(accumulated);
        }

        // dirty hack to avoid rounding problems with floats (ensure max index = 1.0)
        ranges.set((ranges.size()-1), 1f);

        // choose random individuums in the ranges
        Random rnd = new Random();
        List<VEGAIndividuum> newList = new ArrayList<VEGAIndividuum>();
        float curRnd;
        int index;
        for(int i = 0; i < VEGA.BLOCK_SIZE; i++) {
            curRnd = rnd.nextFloat();
            index = 0;

            while(index <= in.size() &&  curRnd > ranges.get(index) ){
                index++;
            }

            newList.add(in.get(index));
        }
        return newList;
    }

    public static List<VEGAIndividuum> RankBasedSelectionF1(List<VEGAIndividuum> in) {

        // sort results in ascending quality
        java.util.Collections.sort(in, new FComparator());

        // get sum of ranks
        float sum = 0;
        for(int i = 1; i <= in.size(); i++) {
            sum += i;
        }

        List<Float> ranges = new ArrayList<Float>();

        // creates a List of upper borders for the ranges
        float rank = 0;
        float range = 0;
        float accumulated = 0;
        for (VEGAIndividuum i : in)  {
            rank ++;
            range = rank/sum;
            accumulated += range;
            ranges.add(accumulated);
        }

        // dirty hack to avoid rounding problems with floats (ensure max index = 1.0)
        ranges.set((ranges.size() - 1), 1f);

        // choose random individuums in the ranges
        Random rnd = new Random();
        List<VEGAIndividuum> newList = new ArrayList<VEGAIndividuum>();
        float curRnd;
        int index;
        for(int i = 0; i < VEGA.BLOCK_SIZE; i++) {
            curRnd = rnd.nextFloat();
            index = 0;

            while(index <= in.size() &&  curRnd > ranges.get(index) ){
                index++;
            }

            newList.add(in.get(index));
        }
        return newList;
    }
}
