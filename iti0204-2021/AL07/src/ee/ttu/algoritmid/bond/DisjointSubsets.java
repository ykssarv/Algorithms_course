package ee.ttu.algoritmid.bond;


import java.util.HashMap;
import java.util.Map;

public class DisjointSubsets {

    public Map<String, Node> map = new HashMap<>();

    public String find(String element) throws IllegalArgumentException {
        if (map.containsKey(element)) {
            return map.get(element).getRoot().name;
        } else {
            // should throw IllegalArgumentException if the element is not present
            throw new IllegalArgumentException();
        }
    }

    // should throw IllegalArgumentException if any of elements is not present
    public void union(String element1, String element2) throws IllegalArgumentException {
        if (map.containsKey(element1) && map.containsKey(element2)) {
            Node root1 = map.get(element1).getRoot();
            Node root2 = map.get(element2).getRoot();
            if (root1.size < root2.size) {
                root1.parent = root2;
                root2.size += root1.size;
            } else {
                root2.parent = root1;
                root1.size += root2.size;
            }
            map.get(element1).getRoot().parent = map.get(element2).getRoot();
        } else {
            // should throw IllegalArgumentException if any of elements is not present
            throw new IllegalArgumentException();
        }
    }

    public void addSubset(String element) throws IllegalArgumentException {
        if (map.containsKey(element)) {
            // should throw IllegalArgumentException if the element is already present
            throw new IllegalArgumentException();
        } else {
            map.put(element, new Node(element));
        }
    }

}