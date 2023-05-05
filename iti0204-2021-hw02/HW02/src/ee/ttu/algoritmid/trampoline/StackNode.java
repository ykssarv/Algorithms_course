package ee.ttu.algoritmid.trampoline;

public class StackNode {
    public int x;
    public int y;
    public int jumps;
    public int fine;

    public StackNode(int x, int y, int jumps, int fine) {
        this.x = x;
        this.y = y;
        this.jumps = jumps;
        this.fine = fine;
    }
}
