package ee.ttu.algoritmid.trampoline;

import java.util.List;

public class SomeTests {

    public static void runTest(int[][] forceMap, int[][] typeMap, List<List<String>> solutions, int fine, int id) {
        Trampoline[][] map = new Trampoline[forceMap.length][forceMap[0].length];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                int finalI = i;
                int finalJ = j;
                map[i][j] = new Trampoline() {
                    @Override
                    public int getJumpForce() {
                        return forceMap[finalI][finalJ];
                    }

                    @Override
                    public Type getType() {
                        if (typeMap[finalI][finalJ] == 0) {
                            return Type.NORMAL;
                        }
                        if (typeMap[finalI][finalJ] == 1) {
                            return Type.WITH_FINE;
                        }
                        return Type.WALL;
                    }
                };
            }
        }

        HW02 solution = new HW02();
        Result gameResult = solution.play(map);

        boolean works = false;
        for (List<String> solutionCandidate: solutions) {
            if (solutionCandidate.equals(gameResult.getJumps())) {
                works = true;
                break;
            }
        }
        if (fine != gameResult.getTotalFine()) {
            works = false;
        }

        if (!works) {
            System.err.println("Case" + id + " does not work properly");
        } else {
            System.out.println("Case " + id + " is fine");
        }
    }

    public static void main(String[] args) {
        int[][] forceMap1 = {
            {1, 2, 2},
            {2, 10, 1},
            {3, 2, 0}
        };
        int[][] typeMap1 = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
        };
        List<List<String>> solutions1 = List.of(
            List.of("S2", "E2"),
            List.of("E2", "S2")
        );
        int fine1 = 0;

        int[][] forceMap2 = {
            {2, 2, 2, 2},
            {3, 10, 1, 1},
            {2, 2, 1, 0}
        };
        int[][] typeMap2 = {
            {0, 0, 1, 1},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        };
        List<List<String>> solutions2 = List.of(
            List.of("S2", "E3")
        );
        int fine2 = 0;

        int[][] forceMap3 = {
            {10, 2, -1, 2},
            {3, 1, 4, 1},
            {-1, 2, 2, 0}
        };
        int[][] typeMap3 = {
            {0, 0, 2, 0},
            {0, 0, 0, 0},
            {2, 0, 0, 0}
        };
        List<List<String>> solutions3 = List.of(
            List.of("E1", "S2", "E2"),
            List.of("S1", "E3", "S1")
        );
        int fine3 = 0;

        int[][] forceMap4 = {
            {2, 1, 1, 1, 1, 1, 1, 2},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {2, 1, 1, 1, 1, 1, 1, 0}
        };
        int[][] typeMap4 = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
        };
        List<List<String>> solutions4 = List.of(
            List.of("E3", "E2", "E2", "S3", "S2", "S2"),
            List.of("S3", "S2", "S2", "E3", "E2", "E2")
        );
        int fine4 = 0;

        runTest(forceMap1, typeMap1, solutions1, fine1, 1);
        runTest(forceMap2, typeMap2, solutions2, fine2, 2);
        runTest(forceMap3, typeMap3, solutions3, fine3, 3);
        runTest(forceMap4, typeMap4, solutions4, fine4, 4);

    }

}
