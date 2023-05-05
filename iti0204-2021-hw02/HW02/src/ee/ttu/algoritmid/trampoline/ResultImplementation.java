package ee.ttu.algoritmid.trampoline;

import java.util.ArrayList;
import java.util.List;

public class ResultImplementation implements Result {

    private List<String> results;
    private int fineAmount;

    public ResultImplementation() {
        results = new ArrayList<>();
        fineAmount = 0;
    }

    public void addJump(boolean jumpEast, int jumpDistance, int fineAmount) {
        if (jumpEast) {
            results.add("E" + jumpDistance);
        } else {
            results.add("S" + jumpDistance);
        }
        this.fineAmount += fineAmount;
    }

    @Override
    public List<String> getJumps() {
        return results;
    }

    @Override
    public int getTotalFine() {
        return fineAmount;
    }
}
