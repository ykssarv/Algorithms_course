package ee.ttu.algoritmid.flights;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BinaryTree<T extends Comparable<T>> {

    private Node<T> root;

    public void setRoot(Node<T> root) {
        this.root = root;
    }

    public void addValue(T value) {
        if (root == null) {
            root = new Node<>(value, null, this);
        } else {
            root.addValue(value);
        }
    }

    public Node<T> find(T value) {
        if (root == null) {
            return null;
        }
        return root.find(value);
    }

    public void remove(T value) {
        Node<T> node = find(value);
        if (node != null && node.getValue().compareTo(value) == 0) {
            if (node == root) {
                if (root.left == null && root.right == null) {
                    root = null;
                } else if (root.left == null ^ root.right == null) {
                    root = root.left == null ? root.right : root.left;
                    root.parent = null;
                } else {
                    Node<T> min = root.right.getMin();
                    T tempValue = root.getValue();
                    root.setValue(min.getValue());
                    min.setValue(tempValue);
                    min.remove();
                    root.parent = null;
                }
            } else {
                node.remove();
            }
        }
    }

    public Node<T> getMin() {
        return root.getMin();
    }

    public List<Node<T>> getIncreasingList() {
        List<Node<T>> increasingList = new ArrayList<>();
        if (root == null) {
            return increasingList;
        }
        Node<T> min = root.getMin();
        while (min != null) {
            increasingList.add(min);
            min = min.getSuccessor();
        }
        return increasingList;
    }


    public static void main(String[] args) {

        Random random = new Random();
        BinaryTree<Double> tree = new BinaryTree<>();
        tree.addValue(149.13014446637817);
        tree.addValue(109.95017813853076);
        tree.addValue(135.2804314403976);
        tree.remove(135.2804314403976);
        System.out.println(tree.root.getValue());
        System.out.println(tree.root.getLeft());
        System.out.println(tree.root.getRight());
        tree.addValue(107.85754701632487);
        tree.addValue(109.19100446276553);
        tree.find(125.39529867531402);
    }
}
