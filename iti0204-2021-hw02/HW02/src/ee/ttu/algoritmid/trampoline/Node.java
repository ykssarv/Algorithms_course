package ee.ttu.algoritmid.trampoline;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Node {

    // Values related to trampoline
    public int distance;
    public int x;
    public int y;
    public int jump;
    public Trampoline.Type type;
    public Node[] neighbours;

    // Solution from here
    public int jumpsToFinish;
    public int fineAmount;
    public boolean canFindFinish;

    // Where to jump next
    public boolean jumpEast;
    public int jumpDistance;

    // Status
    public boolean neighboursAdded;

    // Stack related info
    public int jumpsSoFar;
    public int fineSoFar;

    public Node(int x, int y, int jump, Trampoline.Type type) {
        this.x = x;
        this.y = y;
        this.jump = jump;
        this.type = type;
        this.distance = x + y;
    }

    public void addNeighbours(Node[] right, Node[] down, int rightAmount, int downAmount) {
        int outCounter = 0;
        int rightCounter = 0;
        int downCounter = 0;
        this.neighbours = new Node[rightAmount + downAmount];

        // Combine two sorted arrays into one sorted array
        while (rightCounter < rightAmount && downCounter < downAmount) {
            Node rightNode = right[rightCounter];
            Node downNode = down[downCounter];
            if (rightNode.distance < downNode.distance) {
                this.neighbours[outCounter] = right[rightCounter];
                rightCounter++;
            } else {
                this.neighbours[outCounter] = down[downCounter];
                downCounter++;
            }
            outCounter++;
        }

        while (rightCounter < rightAmount) {
            this.neighbours[outCounter] = right[rightCounter];
            outCounter++;
            rightCounter++;
        }

        while (downCounter < downAmount) {
            this.neighbours[outCounter] = down[downCounter];
            outCounter++;
            downCounter++;
        }
    }
}