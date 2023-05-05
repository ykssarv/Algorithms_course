package ee.ttu.algoritmid.interestingstamps;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
public class InterestingStamps {
    private static int bestCount;
    private static List<Integer> answer;
    private static int stampAmount;
    private static int interestingCount;
    private static int bestInterestingCount;

    public static List<Integer> findStamps(int sum, List<Integer> stampOptions) throws IllegalArgumentException {
        ArrayList<Integer> counts = new ArrayList<>(stampOptions.size());
        for (int i = 0; i < stampOptions.size(); i++) { counts.add(0); }
        stampAmount = 0;
        interestingCount = 0;

        bestCount = Integer.MAX_VALUE;
        bestInterestingCount = -1;
        //System.out.println(stampOptions.toString());
        stampOptions.sort(Comparator.reverseOrder());
        //System.out.println(stampOptions.toString());
        findRecursive(0, stampOptions, sum, counts);

        if (answer == null) {
            throw new IllegalArgumentException();
        }

        List<Integer> rightAnswer = new ArrayList<>();
        for (int i = 0; i < answer.size() ; i++) {
            for (int j = 0; j < answer.get(i); j++) {
                rightAnswer.add(stampOptions.get(i));
            }
        }

        return rightAnswer;
    }

    private static void findRecursive(int stamp, List<Integer> stamps, int sum, ArrayList<Integer> counts) {
        if (sum == 0) {
            if (stampAmount < bestCount || stampAmount == bestCount && interestingCount > bestInterestingCount) {
                bestCount = stampAmount;
                bestInterestingCount = interestingCount;
                answer = new ArrayList<>(counts);
            }
        } else if (stamp < stamps.size()) {
            // sum = 100
            // stamps.get(stamp) = 20
            // bestCount = 7
            // stampAmount = 4
            int needed = sum / stamps.get(stamp);
            for (int j = 0; j <= needed; j++) {
                int allowed = bestCount - stampAmount;
                if (allowed < needed) {
                    break;
                }
                int isInteresting = (stamps.get(stamp) == 1 || stamps.get(stamp) % 10 == 0) ? 0 : 1;
                stampAmount -= counts.get(stamp);
                interestingCount -= counts.get(stamp) * isInteresting;
                counts.set(stamp, j);
                stampAmount += j;
                interestingCount += j * isInteresting;
                findRecursive(stamp + 1, stamps, sum - j * stamps.get(stamp), counts);
                stampAmount -= counts.get(stamp);
                interestingCount -= counts.get(stamp) * isInteresting;
                counts.set(stamp, 0);
            }
        }
    }

    public static void main(String[] args) {
        List<Integer> rightAnswer = InterestingStamps.findStamps(100, Arrays.asList(10, 1, 24, 30, 33, 36));
        System.out.println(rightAnswer.toString());
    }
}