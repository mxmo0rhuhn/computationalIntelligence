package Exercise_3_VEGA;

import Helper.Individuum;

import java.util.*;

/**
 * Created by Max Schrimpf
 */
public class Recombinator {

    private static final float MUTATION_PROBABILITY = 0.010f;

    public static List<Individuum> singlePointRecombinationList(int nr, List<Individuum> inList) {

        if(nr > inList.size()) {
            throw new IllegalArgumentException("Number of parents > elements in recombination list");
        }

        List<Individuum> parentSet = new ArrayList<Individuum>();

        Random rnd = new Random();
        int curRnd;

        while (parentSet.size() < nr) {
            curRnd = Math.round(rnd.nextFloat() * (inList.size()-1));
            parentSet.add(inList.get(curRnd));
            inList.remove(curRnd);
        }

        List<Individuum> result;
        for(int i = 0; i < parentSet.size() ; i = i + 2 ) {
            result = singlePointRecombination((VEGAIndividuum) parentSet.get(i),(VEGAIndividuum) parentSet.get(i+1));
            inList.add(result.get(0));
            inList.add(result.get(1));
        }

        return inList;
    }
    public static List<Individuum> singlePointRecombination(VEGAIndividuum p1, VEGAIndividuum p2) {

        int maxNumChars = p1.getBinaryRepresentationLength();
        int pos =  Math.round(new Random().nextFloat() * maxNumChars);

        String newIndividuum1 = p1.toString().substring(0,pos);
        String newIndividuum2 = p2.toString().substring(0,pos);
        newIndividuum1 += p2.toString().substring(pos, maxNumChars);
        newIndividuum2 += p1.toString().substring(pos, maxNumChars);

        List<Individuum> newOnes = new ArrayList<Individuum>();
        newOnes.add(new VEGAIndividuum(newIndividuum1));
        newOnes.add(new VEGAIndividuum(newIndividuum2));
        return newOnes;
    }

    public static Individuum mutate(VEGAIndividuum ind) {

        int maxNumChars = ind.getBinaryRepresentationLength();
        Random rnd =  new Random();

        String oldIndividuum = ind.toString();
        String newIndividuum = "";

        for (int i = 0; i < maxNumChars; i++) {
            if (rnd.nextFloat() <= MUTATION_PROBABILITY) {
                if (oldIndividuum.charAt(i) == '1') {
                    newIndividuum += "0";
                } else {
                    newIndividuum += "1";
                }
            } else {
                newIndividuum += oldIndividuum.charAt(i);
            }
        }
        return (Individuum) new VEGAIndividuum(newIndividuum);
    }
}
