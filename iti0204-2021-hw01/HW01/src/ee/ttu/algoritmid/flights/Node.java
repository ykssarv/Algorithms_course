package ee.ttu.algoritmid.flights;

public class Node<T extends Comparable<T>> {
    private T value;
    public Node<T> left;
    public Node<T> right;
    public Node<T> parent;
    public int balanceFactor;
    public BinaryTree<T> tree;

    public Node(T value, Node<T> left, Node<T> right, Node<T> parent, BinaryTree<T> tree) {
        this.value = value;
        this.left = left;
        this.right = right;
        this.parent = parent;
        this.balanceFactor = 0;
        this.tree = tree;


    }

    public Node(T value, Node<T> parent, BinaryTree<T> tree) {
        this.value = value;
        this.parent = parent;
        this.balanceFactor = 0;
        this.tree = tree;
    }

    public int getBalanceFactor() {
        return balanceFactor;
    }

    public T getValue() {
        return value;
    }

    public Node<T> getLeft() {
        return left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void addValue(T value) {
        if (value.compareTo(this.value) < 0) { // compareTo returns -1 if left object is smaller
            if (this.left != null) {
                this.left.addValue(value);
            } else {
                this.left = new Node<T>(value,this, this.tree);
                this.updateAfterAdd(this.left);
            }
        } else {
            if (this.right != null) {
                this.right.addValue(value);
            } else {
                this.right = new Node<T>(value, this, this.tree);
                this.updateAfterAdd(this.right);
            }
        }
    }



    public void updateAfterAdd(Node<T> child) {
        if (this.left == child) {
            this.balanceFactor -= 1;
        } else if (this.right == child) {
            this.balanceFactor += 1;
        }
        if (this.balanceFactor == 2) {
            if (this.right.balanceFactor == -1) {
                this.right.rightRotation();
            }
            this.leftRotation();
        } else if (this.balanceFactor == -2) {
            if (this.left.balanceFactor == 1) {
                this.left.leftRotation();
            }
            this.rightRotation();
        } else if (this.balanceFactor == 1 || this.balanceFactor == -1) {
            if (this.parent != null) {
                this.parent.updateAfterAdd(this);
            }
        }
    }

    public void rightRotation() {
        Node<T> c = this.left;
        this.left = c.right;
        if (c.right != null) {
            c.right.parent = this;
        }
        c.right = this;
        c.parent = this.parent;
        if (this.parent != null) {
            this.parent.replace(this, c);
        } else {
            this.tree.setRoot(c);
        }
        this.parent = c;
        int oldA = this.balanceFactor;
        int oldB = c.balanceFactor;
        this.balanceFactor = oldA + 1 - Math.min(oldB, 0);
        c.balanceFactor = oldB + 1 + Math.max(this.balanceFactor, 0);
    }

    public void leftRotation() {
        Node<T> b = this.right;
        this.right = b.left;
        if (b.left != null) {
            b.left.parent = this;
        }
        b.left = this;
        b.parent = this.parent;
        if (this.parent != null) {
            this.parent.replace(this, b);
        } else {
            this.tree.setRoot(b);
        }
        this.parent = b;
        int oldA = this.balanceFactor;
        int oldB = b.balanceFactor;
        this.balanceFactor = oldA - 1 - Math.max(oldB, 0);
        b.balanceFactor = oldB - 1 + Math.min(this.balanceFactor, 0);
    }


    public Node<T> find(T value) {
        if (value.compareTo(this.value) == 0) {
            return this;
        }
        if (value.compareTo(this.value) < 0) {
            if (this.left != null) {
                return this.left.find(value);
            } else {
                return this;
            }
        } else {
            if (this.right != null) {
                return this.right.find(value);
            } else {
                return this;
            }
        }
    }
    public void remove() {
        if (this.left == null && this.right == null) {
            boolean deletedLeft = this == this.parent.left;
            this.parent.replace(this);
            this.parent.updateAfterRemove(deletedLeft);
        } else if (this.left == null ^ this.right == null) {
            boolean deletedLeft = this == this.parent.left;
            this.parent.replace(this, this.left == null ? this.right : this.left);
            this.parent.updateAfterRemove(deletedLeft);
        } else {
            Node<T> min = this.right.getMin();
            T tempValue = this.getValue();
            this.setValue(min.getValue());
            min.setValue(tempValue);
            min.remove();
        }
    }

    public void updateAfterRemove(boolean deletedLeft) {
        if (deletedLeft) {
            this.balanceFactor += 1;
        } else {
            this.balanceFactor -= 1;
        }
        if (this.balanceFactor == 2) {
            if (this.right.balanceFactor == -1) {
                this.right.rightRotation();
            }
            this.leftRotation();
            if (this.parent != null) {
                if (this.balanceFactor == 0) {
                    this.parent.updateAfterRemove(this == this.parent.left);
                }
            }
        } else if (this.balanceFactor == -2) {
            if (this.left.balanceFactor == 1) {
                this.left.leftRotation();
            }
            this.rightRotation();
            if (this.parent != null) {
                if (this.balanceFactor == 0) {
                    this.parent.updateAfterRemove(this == this.parent.left);
                }
            }
        } else if (this.balanceFactor == 0) {
            if (this.parent != null) {
                this.parent.updateAfterRemove(this == this.parent.left);
            }
        }
    }

    public void replace(Node<T> child, Node<T> target) {
        if (child == this.right) {
            this.right = target;
            target.parent = this;
        } else {
            this.left = target;
            target.parent = this;
        }
    }

    public void replace(Node<T> child) {
        if (child == this.right) {
            this.right = null;
        } else {
            this.left = null;
        }
    }

    public Node<T> getMin() {
        if (this.left == null) {
            return this;
        }
        return this.left.getMin();
    }

    public Node<T> getMax() {
        if (this.right == null) {
            return this;
        }
        return this.right.getMax();
    }

    public Node<T> getSuccessor() {
        if (this.right != null) {
            return this.right.getMin();
        }
        return this.getSuccessorUp();
    }

    public Node<T> getSuccessorUp() {
        if (this.parent == null) {
            return null;
        }
        if (this == this.parent.left) {
            return this.parent;
        }
        return this.parent.getSuccessorUp();
    }

    public Node<T> getPredecessor() {
        if (this.left != null) {
            return this.left.getMax();
        }
        return this.getPredecessorUp();
    }

    public Node<T> getPredecessorUp() {
        if (this.parent == null) {
            return null;
        }
        if (this == this.parent.right) {
            return this.parent;
        }
        return this.parent.getPredecessorUp();
    }

}
