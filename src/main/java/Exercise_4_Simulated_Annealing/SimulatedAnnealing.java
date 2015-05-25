package Exercise_4_Simulated_Annealing;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Max Schrimpf
 */
public class SimulatedAnnealing {
    private static final float C = 0.8f;
    ArrayList<City> cities;
    private static final int NR_CITIES = 36;
    private float temp = 3000;

    String[] route;
    int distanceFirst;

    ArrayList<String[]> routes = new ArrayList<String[]>();
    ArrayList<Integer> distances = new ArrayList<Integer>();

    public static void main(String[] args) {

        // Source Data
        // nextNeighbour
        // Annealing

    }
    private void nextNeighbor(List<City> cities) {
        route = new String[NR_CITIES];
        distanceFirst = 0;
        int i = 0;
        City last = null;
        City next = null;
        City first = cities.get(0);
        City cur = first;

        while (cities.size() > 1) {
            cities.remove(cities.indexOf(cur));
            next = cur.getNearestCity(cities);
            route[i] = cur.toString();
            distanceFirst = cur.getDistance(next);
            i++;
            cur = next;

            if (i==NR_CITIES) {
                last = next;
            }
        }
        distanceFirst += last.getDistance(first);
        route[i] = last.toString();
    }

    private void simulatedAnnealing(String[] r){



        temp = temp - C;
    }
}
