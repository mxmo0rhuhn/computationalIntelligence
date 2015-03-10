package Exercise_2_ES;

import Helper.EA;
import Helper.Individuum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Max Schrimpf
 */
public class EvolutionStrategy implements EA {

    public static final int NUM_CHILDREN = 49;
    public static final int NUM_PARENTS = 3;
    public static final int SIZE_GENERATION = 7;
    public static final int LIVE_GENERATIONS = 15;
    public static final int MAX_GENERATIONS = 100;

    private List<Individuum> individuums = new ArrayList<Individuum>();
    List<Individuum> topIndividuums = new ArrayList<Individuum>();
    int generation;

    private Individuum newIndividuum() {
        // TODO ... initialize Strategy vector
        ESIndividuum ind = new ESIndividuum(newRandomBinaryString(ESIndividuum.D_LEN), newRandomBinaryString(ESIndividuum.H_LEN), new ArrayList<Float>());
        return (Individuum) ind;
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
        return NUM_CHILDREN;
    }

    @Override
    public List<Individuum> firstGeneration() {
        for(int i = 0; i < SIZE_GENERATION; i++) {
            individuums.add(newIndividuum());
        }
        return individuums;
    }

    @Override
    public List<Individuum> runGeneration() {
        generation++;
        Random rnd = new Random();
        int curRnd;

        for(int i = 1; i <= NUM_CHILDREN; i++) {
            curRnd = Math.round(((rnd.nextFloat() * NUM_PARENTS)-0.5f));
        }

        individuums = Selection.RankBasedSelection(individuums);

        // Objektparameter
        // - Durschnittliche Rekombination
        // - Isotropic Mutation (Start 1%)

        // Strategieparameter
        // Diskrete Rekombination
        // Non-Isotropic Mutation (Start 1%) mit
        return individuums;
    }

    @Override
    public int getMaxF() {
        return 0;
    }

    @Override
    public int getMaxG() {
        return 0;
    }
}
