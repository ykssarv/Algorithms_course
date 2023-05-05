package ee.ttu.algoritmid.guessinggame;

import java.util.Arrays;
import java.util.Comparator;

public class GuessingGame {

    Oracle oracle;

    public GuessingGame(Oracle oracle) {
        this.oracle = oracle;
    }

    /**
     * @param fruitArray - All the possible fruits.
     * @return the name of the fruit.
     */
    public String play(Fruit[] fruitArray) {
        Arrays.sort(fruitArray, Comparator.comparing(Fruit::getWeight));
        int low = 0;
        int high = fruitArray.length;

        while (true) {
            int mid = (high + low) / 2;
            Fruit fruit = fruitArray[mid];
            String answer = oracle.isIt(fruit);
            if (answer.equals("correct!")) {
                return fruit.getName();
            }
            if (answer.equals("lighter")) {
                high = mid;
            } else {
                low = mid;
            }
        }
    }

}
