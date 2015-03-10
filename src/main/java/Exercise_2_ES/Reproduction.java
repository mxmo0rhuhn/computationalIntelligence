package Exercise_2_ES;

import java.util.*;

/**
 * Created by Max Schrimpf
 */
public class Reproduction {

    private static final float MUTATION_PROBABILITY = 0.010f;

    public static List<Float> discreteRealObjectRecombination(List<List<Float>> objects) {

        Random rnd =  new Random();
        int numParam = objects.get(0).size();
        int curRnd;

        List<Float> toReturn = new ArrayList<Float>();
        Float curParam;

        for(int i = 0; i < numParam ; i++) {
            curRnd = Math.round(((rnd.nextFloat() * objects.size())-0.5f));
            curParam = objects.get(curRnd).get(i);
            toReturn.add(curParam);
        }
        return toReturn;
    }

    public static List<Float> avgRealObjectRecombination(List<List<Float>> objects) {

        List<Float> toReturn = new ArrayList<Float>();
        Float curParam;

        int numObj = objects.size();
        int numParam = objects.get(0).size();

        for(int i = 0; i < numParam ; i++) {

            curParam = 0f;
            for(List<Float> curOj : objects) {
                curParam += curOj.get(i);
            }
            curParam = curParam/numObj;
            toReturn.add(curParam);
        }

        return toReturn;
    }

    public static List<Float> isotropicMutation(List<Float> params, float sigma) {

        Random rnd =  new Random();

        int numParam = params.size();
        float normalDistSample;

        List<Float> toReturn = new ArrayList<Float>();
        float curParam;

        for(int i = 0; i < numParam ; i++) {
            normalDistSample = (float) rnd.nextGaussian();
            curParam = params.get(i);
            curParam = (curParam + normalDistSample) * sigma;
            toReturn.add(curParam);
        }
        return toReturn;
    }

    public static List<Float> nonIsotropicMutation(List<Float> params, List<Float> sigmas) {

        if(params.size() != sigmas.size()) {
            throw new IllegalArgumentException("Number of sigmas provided is not eqal number of parameter provided.");
        }

        Random rnd =  new Random();

        int numParam = params.size();
        float normalDistSample;

        List<Float> toReturn = new ArrayList<Float>();
        float curParam;

        for(int i = 0; i < numParam ; i++) {
            normalDistSample = (float) rnd.nextGaussian();
            curParam = params.get(i);
            curParam = (curParam + normalDistSample) * sigmas.get(i);
            toReturn.add(curParam);
        }
        return toReturn;
    }
}
