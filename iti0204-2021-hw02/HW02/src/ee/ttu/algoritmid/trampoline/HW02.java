package ee.ttu.algoritmid.trampoline;


public class HW02 implements TrampolineCenter {

    // Metadata about map
    public int width;
    public int height;

    // Nodes and data about their states
    public Node[][] nodes;
    public boolean[] solved;
    public boolean[] initialized;

    // Data about the best solution
    public int bestJumps = Integer.MAX_VALUE;
    public int bestFine = Integer.MAX_VALUE;

    @Override
    public Result play(Trampoline[][] map) {
        makeMap(map);
        markLastAsSolved();
        solve(map);
        return parseAnswer();
    }

    // Each node remembers where to jump next, so to get answer at the end, simply start from first node and remember each jump
    private ResultImplementation parseAnswer() {
        Node current = nodes[0][0];
        ResultImplementation result = new ResultImplementation();
        while (!(current.x == width - 1 && current.y == height - 1)) {
            result.addJump(current.jumpEast, current.jumpDistance, current.type == Trampoline.Type.WITH_FINE ? current.jump : 0);
            if (current.jumpEast) {
                current = nodes[current.y][current.x + current.jumpDistance];
            } else {
                current = nodes[current.y + current.jumpDistance][current.x];
            }
        }
        return result;
    }

    public void markLastAsSolved() {
        Node last = nodes[height - 1][width - 1];
        last.canFindFinish = true;
        last.jumpsToFinish = 0;
        last.fineAmount = 0;
        solved[last.y * width + last.x] = true;
    }

    public Node initializeFirstNode(Trampoline[][] map) {
        Node start = new Node(0, 0, map[0][0].getJumpForce(), map[0][0].getType());
        nodes[0][0] = start;
        initialized[0] = true;
        start.jumpsSoFar = 0;
        start.fineSoFar = 0;
        return start;
    }

    // Solution using stack
    private void solve(Trampoline[][] map) {
        // Initialize a "stack" (array and counter used instead, for performance reasons)
        Node[] nodeStack = new Node[width * height * 2];
        int stackCounter = -1;

        Node start = initializeFirstNode(map);

        // Initialize stack with first node
        stackCounter++;
        nodeStack[stackCounter] = start;

        while (stackCounter >= 0) {
            // Take next node from stack
            Node current = nodeStack[stackCounter];

            // If path so far is longer than the best solution, then we know that there can't be an optimal solution from here
            if (current.jumpsSoFar > bestJumps || current.jumpsSoFar == bestJumps && current.fineSoFar > bestFine) {
                // Mark node as not having a solution (or best solution for node, is worse than overall best)
                markJump(current, null, current.jumpsSoFar + 1, current.fineSoFar + (current.type == Trampoline.Type.WITH_FINE ? current.jump : 0));
                // Remove freshly solved neighbour from stack (solved in the sense that we know whether a solution exists)
                stackCounter--;
                continue;
            }

            // Initialize neighbours for graph structure on the fly as needed
            if (!current.neighboursAdded) {
                addNeighbours(current.x, current.y, map);
            }

            // Check if all neighbours are solved
            boolean allSolved = true;
            for (Node neighbour: current.neighbours) {
                if (!solved[neighbour.y * width + neighbour.x]) {
                    allSolved = false;

                    // Add unsolved neighbour to stack
                    stackCounter++;
                    nodeStack[stackCounter] = neighbour;
                }
            }
            // If any neighbour was unsolved, continue with taking elements from stack, to solve neighbour first
            if (!allSolved) {
                continue;
            }

            // If all neighbours were solved, pop node and find best answer
            // Remove freshly solved node from stack
            stackCounter--;
            Node bestNeighbour = null;
            for (Node neighbourNode: current.neighbours) {
                if (!neighbourNode.canFindFinish) {
                    continue;
                }
                if (
                    bestNeighbour == null
                        || neighbourNode.jumpsToFinish < bestNeighbour.jumpsToFinish
                        || neighbourNode.jumpsToFinish == bestNeighbour.jumpsToFinish && neighbourNode.fineAmount < bestNeighbour.fineAmount
                ) {
                    bestNeighbour = neighbourNode;
                }
            }
            // Mark best solution for node
            markJump(current, bestNeighbour, current.jumpsSoFar + 1, current.fineSoFar + (current.type == Trampoline.Type.WITH_FINE ? current.jump : 0));
        }
    }


    // Remember where to jump from given node
    private void markJump(Node from, Node to, int jumpsSoFar, int fineSoFar) {
        if (to != null) {
            int totalJumps = to.jumpsToFinish + jumpsSoFar;
            int totalFine = to.fineAmount + fineSoFar;
            if (totalJumps < bestJumps || totalJumps == bestJumps && totalFine < bestFine) {
                bestJumps = totalJumps;
                bestFine = totalFine;
            }
            from.canFindFinish = true;
            from.jumpsToFinish = to.jumpsToFinish + 1;
            from.fineAmount = to.fineAmount + (from.type == Trampoline.Type.WITH_FINE ? from.jump : 0);
            from.jumpEast = to.y == from.y;
            from.jumpDistance = to.x - from.x + to.y - from.y;
        } else {
            from.canFindFinish = false;
        }
        solved[from.y * width + from.x] = true;
    }

    // Initialize last node and make arrays for holding metadata about uninitialized nodes
    private void makeMap(Trampoline[][] map) {
        this.height = map.length;
        this.width = map[0].length;

        solved = new boolean[height * width];
        initialized = new boolean[height * width];

        nodes = new Node[this.height][this.width];

        int lastY = this.height - 1;
        int lastX = this.width - 1;
        nodes[lastY][lastX] = new Node(lastX, lastY, map[lastY][lastX].getJumpForce(), map[lastY][lastX].getType());
    }

    // Go right and down from given node, until jump distance reached or there is a wall in the way
    public void addNeighbours(int x, int y, Trampoline[][] map) {
        Node node = nodes[y][x];
        node.neighboursAdded = true;
        Node[] right = new Node[3];
        Node[] down = new Node[3];
        int rightCounter = 0;
        int downCounter = 0;

        int jumpForce = node.jump;
        if (jumpForce == 0) {
            node.addNeighbours(right, down, rightCounter, downCounter);
            return;
        }

        // Add right
        for (int j = 1; j <= jumpForce + 1; j++) {
            int rightX = x + j;
            if (rightX >= width) {
                break;
            }
            if (map[y][rightX].getType() == Trampoline.Type.WALL) {
                if (rightX - 1 > x && j < jumpForce) {
                    rightCounter = addNeighbour(node, rightX - 1, y, map, right, rightCounter);
                }
                break;
            }
            if (j - jumpForce >= -1) {
                rightCounter = addNeighbour(node, rightX, y, map, right, rightCounter);
            }
        }

        // Add down
        for (int j = 1; j <= jumpForce + 1; j++) {
            int downY = y + j;
            if (downY >= height) {
                break;
            }
            if (map[downY][x].getType() == Trampoline.Type.WALL) {
                if (downY - 1 > y && j < jumpForce) {
                    downCounter = addNeighbour(node, x, downY - 1, map, down, downCounter);
                }
                break;
            }
            if (j - jumpForce >= -1) {
                downCounter = addNeighbour(node, x, downY, map, down, downCounter);
            }
        }

        node.addNeighbours(right, down, rightCounter, downCounter);
    }

    public int addNeighbour(Node node, int x, int y, Trampoline[][] map, Node[] nodes, int counter) {
        Node neighbour;
        if (!solved[y * width + x]) {
            neighbour = new Node(x, y, map[y][x].getJumpForce(), map[y][x].getType());
            this.nodes[y][x] = neighbour;
            initialized[y * width + x] = true;
            neighbour.jumpsSoFar = node.jumpsSoFar + 1;
            neighbour.fineSoFar = node.fineSoFar + (node.type == Trampoline.Type.WITH_FINE ? node.jump : 0);
        } else {
            neighbour = this.nodes[y][x];
        }
        nodes[counter] = neighbour;
        return counter + 1;
    }
}