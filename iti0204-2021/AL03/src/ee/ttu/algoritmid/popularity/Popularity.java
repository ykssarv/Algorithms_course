package ee.ttu.algoritmid.popularity;

import java.util.Comparator;
import java.util.HashMap;

public class Popularity {

    private final HashMap<String, Integer> coordinates = new HashMap<String, Integer>();
    private Integer maxValue = 0;

    public Popularity(int maxCoordinates) {
    }


    /**
     * @param x, y - coordinates
     */
    void addPoint(Integer x, Integer y) {
        String coordinate = x + " " + y;
        if (!coordinates.containsKey(coordinate)) {
            coordinates.put(coordinate, 1);
        }
        else {
            coordinates.put(coordinate, coordinates.get(coordinate) + 1);
        }
        if (coordinates.get(coordinate) > maxValue) {
            maxValue = coordinates.get(coordinate);
        }
    }

    /**
     * @param x, y - coordinates
     * @return the number of occurrences of the point
     */
    int pointPopularity(Integer x,Integer y) {
        String coordinate = x + " " + y;
        return coordinates.getOrDefault(coordinate, 0);
    }


    /**
     * @return the number of occurrences of the most popular point
     */
    int maxPopularity() {
        return maxValue;
    }
}