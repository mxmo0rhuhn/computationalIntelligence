package Exercise_4_Simulated_Annealing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Max Schrimpf
 */
public class City {

    private final String city;
    private Map<String, Integer> distances = new HashMap<String, Integer>();

    public City(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return city;
    }

    public int getDistance(City city) {
        if(!distances.containsKey(city.toString())) {
            throw new RuntimeException("City " + this.city + " has no known distance to " + city);
        }
        return distances.get(city.toString());
    }

    public City getNearestCity(List<City> cities) {

        int min = Integer.MAX_VALUE;
        City result = null;

        for (City city : cities) {
            if(!distances.containsKey(city.toString())) {
                throw new RuntimeException("City " + this.city + " has no known distance to " + city);
            }

            if(!city.toString().equals(this.city.toString()) && distances.get(city.toString()) < min) {
                min = distances.get(city);
                result = city;
            }
        }
        if(result == null) {
            throw new RuntimeException("no valid distance found for " + city + " Possibilities: " + cities.toString());
        }
       return result;
    }
}
