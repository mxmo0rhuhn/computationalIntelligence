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

    public int getDistance(String city) {
        if(!distances.containsKey(city)) {
            throw new RuntimeException("City " + this.city + " has no known distance to " + city);
        }
        return distances.get(city);
    }

    public String getNearestCity(List<String> cities) {

        int min = Integer.MAX_VALUE;
        String result = null;

        for (String city : cities) {
            if(!distances.containsKey(city)) {
                throw new RuntimeException("City " + this.city + " has no known distance to " + city);
            }

            if(!city.equals(this.city) && distances.get(city) < min) {
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
