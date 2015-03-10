import java.util.*;

/**
 * Created by Max Schrimpf
 */
public class Selector {

    public static List<Individuum> RankBasedSelectionF2(List<Individuum> in) {

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
        for (Individuum i : in)  {
            rank ++;
            range = rank/sum;
            accumulated += range;
            ranges.add(accumulated);
        }

        // dirty hack to avoid rounding problems with floats (ensure max index = 1.0)
        ranges.set((ranges.size()-1), 1f);

        // choose random individuums in the ranges
        Random rnd = new Random();
        List<Individuum> newList = new ArrayList<Individuum>();
        float curRnd;
        int index;
        for(int i = 0; i < EA.BLOCK_SIZE; i++) {
            curRnd = rnd.nextFloat();
            index = 0;

            while(index <= in.size() &&  curRnd > ranges.get(index) ){
                index++;
            }

            newList.add(in.get(index));
        }
        return newList;
    }

    public static List<Individuum> RankBasedSelectionF1(List<Individuum> in) {

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
        for (Individuum i : in)  {
            rank ++;
            range = rank/sum;
            accumulated += range;
            ranges.add(accumulated);
        }

        // dirty hack to avoid rounding problems with floats (ensure max index = 1.0)
        ranges.set((ranges.size() - 1), 1f);

        // choose random individuums in the ranges
        Random rnd = new Random();
        List<Individuum> newList = new ArrayList<Individuum>();
        float curRnd;
        int index;
        for(int i = 0; i < EA.BLOCK_SIZE; i++) {
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
