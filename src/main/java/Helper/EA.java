package Helper;

import java.util.List;

/**
 * Created by Max Schrimpf
 */
public interface EA {
    /**
     * get the current generation number
     *
     * @return the current Generation
     */
    public int getGeneration();

    /**
     * get maximum number of individuals in one generation
     *
     * @return the highest possible number of individuals in one generation
     */
    public int getMaxIndividual();

    /**
     * Setup the EA
     */
    public List<Individuum> firstGeneration();

    /**
     * Run one generation lifecycle
     */
    public List<Individuum> runGeneration();

    int getMaxF();
    int getMaxG();
}
