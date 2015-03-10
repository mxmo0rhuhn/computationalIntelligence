package Exercise_1_GA;
import Helper.EA;
import Helper.Individuum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Max Schrimpf
 */
public class GeneticAlgorithm implements EA {
    public static final int NUM_INDIVIDUUM = 30;
    public static final int NUM_GENERATION = 100;
    public static final int NUM_RECOMBINATED = 10;

    int generation = 0;

    List<Individuum> individuums = new ArrayList<Individuum>();
    List<Individuum> topIndividuums = new ArrayList<Individuum>();

    private GAIndividuum newIndividuum() {
        GAIndividuum ind = new GAIndividuum(newRandomBinaryString(GAIndividuum.D_LEN), newRandomBinaryString(GAIndividuum.H_LEN));
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

    @Override
    public int getGeneration() {
        return generation;
    }

    @Override
    public int getMaxIndividual() {
        return NUM_INDIVIDUUM;
    }

    @Override
    public List<Individuum> firstGeneration() {
        generation = 1;
        for(int i = 0; i < NUM_INDIVIDUUM; i++) {
            individuums.add(newIndividuum());
        }
        return individuums;
    }

    @Override
    public List<Individuum> runGeneration() {
        generation++;
        individuums = Selector.RankBasedSelection(individuums);

        // recombinator
        individuums = Recombinator.singlePointRecombinationList(NUM_RECOMBINATED, individuums);

        // mutate
        for (int i = 0; i < NUM_INDIVIDUUM; i++) {
            individuums.set(i, Recombinator.mutate((GAIndividuum) individuums.get(i)));
        }

        topIndividuums.add(Selector.getBest(individuums));
        return individuums;
    }

    @Override
    public int getMaxF() {
        return GAIndividuum.getMaxF();
    }

    @Override
    public int getMaxG() {
        return GAIndividuum.getMaxG();
    }

    @Override
    public String getBest() {
        return Selector.getBest(individuums).toExtendedString();
    }
}
