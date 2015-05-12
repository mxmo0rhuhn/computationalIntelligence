package Exercise_4_Simulated_Annealing;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Max Schrimpf
 */
public class SimulatedAnnealing {
    ArrayList<City> cities;
    private static final int NR_CITIES = 36;

    String[] route;
    String next;
    int distanceFirst;

    private void nextNeighbor(List<City> cities) {
        route = new String[NR_CITIES];
        distanceFirst = 0;
        int i = 0;
        List<String> citiesCopy = new ArrayList<String>();
        for (City city : cities) {
            citiesCopy.add(city.toString());
        }

        for (City city : cities) {
            next = city.getNearestCity(citiesCopy);
            citiesCopy.remove(next);
            distanceFirst = city.getDistance(next);
            i++;
        }
    }

    private void innerLoop(){

    }
}
