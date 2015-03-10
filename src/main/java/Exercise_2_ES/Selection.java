package Exercise_2_ES;

import Helper.Individuum;

import java.util.*;

/**
 * Created by Max Schrimpf
 */
public class Selection {

    public static ESIndividuum getBest(List<ESIndividuum> in) {
        List<ESIndividuum> removedList = new ArrayList<ESIndividuum>();
        for (ESIndividuum i : in) {
            if (i.satisfiesConstraint()) {
                removedList.add(i);
            }
        }

        // sort results in ascending quality
        java.util.Collections.sort(removedList);
        return removedList.get(removedList.size() - 1);
    }

    public static List<Individuum> RankBasedSelection(List<Individuum> in) {

        // remove invalid results
        List<ESIndividuum> removedList = new ArrayList<ESIndividuum>();
        for (Individuum ind : in)  {
            ESIndividuum i = (ESIndividuum) ind;
            if(i.satisfiesConstraint()) {
                removedList.add( i);
            }
        }

        // sort results in ascending quality
        java.util.Collections.sort(removedList);

        // get sum of ranks
        float sum = 0;
        for(int i = 1; i <= removedList.size(); i++) {
            sum += i;
        }

        List<Float> ranges = new ArrayList<Float>();

        // creates a List of upper borders for the ranges
        float rank = 0;
        float range = 0;
        float accumulated = 0;
        for (ESIndividuum i : removedList)  {
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
        for(int i = 0; i < EvolutionStrategy.SIZE_GENERATION; i++) {
            curRnd = rnd.nextFloat();
            index = 0;

            while(index <= removedList.size() &&  curRnd > ranges.get(index) ){
                index++;
            }

            newList.add((Individuum) removedList.get(index));
        }
        return newList;
    }
}
