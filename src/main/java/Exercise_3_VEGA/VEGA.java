package Exercise_3_VEGA;

import Helper.EA;
import Helper.Individuum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VEGA implements EA{

    public static final int NUM_INDIVIDUUM = 30;
    public static final int NUM_RECOMBINATED = 10;
    public static final int BLOCK_SIZE = 15;

    public List<Individuum> individuums;
    public int generation;

    @Override
    public int getGeneration() {
        return generation;
    }

    @Override
    public int getMaxIndividual() {
        return NUM_INDIVIDUUM;
    }

    /**
     * Create the first generation
     */
    @Override
    public List<Individuum> firstGeneration() {
        individuums = new ArrayList<Individuum>();
        generation = 1;

        for(int i = 0; i < NUM_INDIVIDUUM; i++) {
            individuums.add(newIndividuum());
        }
        return individuums;
    }

    /**
     * Run one Generation lifecycle
     */
    @Override
    public List<Individuum> runGeneration() {
        List<VEGAIndividuum> block1 ;
        List<VEGAIndividuum> block2 ;
        Random rnd = new Random();
        int curRnd;

        generation++;

        block1 = new ArrayList<VEGAIndividuum>();
        for (Individuum i : individuums) {
            block1.add((VEGAIndividuum) i);
        }
        block2 = new ArrayList<VEGAIndividuum>();

        for(int i = 1; i <= BLOCK_SIZE; i++) {
            curRnd = Math.round(((rnd.nextFloat() * block1.size())-0.5f));

            block2.add(block1.get(curRnd));
            block1.remove(curRnd);
        }

        block1 = Selector.RankBasedSelectionF1(block1);
        block2 = Selector.RankBasedSelectionF2(block2);

        individuums = new ArrayList<Individuum>();
        individuums.addAll(block1);
        individuums.addAll(block2);

        // recombinator
        individuums = (List<Individuum>) Recombinator.singlePointRecombinationList(NUM_RECOMBINATED, individuums);

        // mutate
        for (int i = 0; i < NUM_INDIVIDUUM; i++) {
            individuums.set(i, Recombinator.mutate((VEGAIndividuum) individuums.get(i)));
        }

        return individuums;
    }

    @Override
    public int getMaxF() {
        return VEGAIndividuum.getMaxF();
    }

    @Override
    public int getMaxG() {
        return VEGAIndividuum.getMaxG();
    }

    private VEGAIndividuum newIndividuum() {
        VEGAIndividuum ind = new VEGAIndividuum(newRandomBinaryString(VEGAIndividuum.D_LEN), newRandomBinaryString(VEGAIndividuum.H_LEN));
        return ind;
    }

    private String newRandomBinaryString(int maxLen) {
        Random rnd = new Random();
        char[] binaryString = new char[maxLen];

        for(int i = 0; i < maxLen; i++) {
            if(rnd.nextBoolean()) {
                binaryString[i] = '1';
            } else {
                binaryString[i] = '0';
            }
        }
        return String.valueOf(binaryString);
    }

}
