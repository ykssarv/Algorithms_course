package ee.ttu.algoritmid.bond;

public class Node {

    public String name;
    public Node parent;
    public int size;

    public Node(String name) {
        this.name = name;
        this.parent = this;
        this.size = 1;
    }

    public Node getRoot() {
        if (this.parent == this) {
            return this;
        }
        this.parent = this.parent.getRoot();
        return this.parent;
    }
}