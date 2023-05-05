package ee.ttu.algoritmid.bond;

public class AL07 {

    public enum Network {
        FRIENDLY, UNFRIENDLY, UNKNOWN;
    }

    public DisjointSubsets disjointSubsets = new DisjointSubsets();
    public String friendly = "A";
    public String unfriendly = "U";

    public AL07() {
        // don't remove
        disjointSubsets.addSubset("A");
        disjointSubsets.addSubset("U");
    }

    public DisjointSubsets getDisjointSubsets() {
        return disjointSubsets;
    }

    public void talkedToEachOther(String name1, String name2) {
        if (!disjointSubsets.map.containsKey(name1)) {
            disjointSubsets.addSubset(name1);
        }
        if (!disjointSubsets.map.containsKey(name2)) {
            disjointSubsets.addSubset(name2);
        }
        String root1 = disjointSubsets.find(name1);
        String root2 = disjointSubsets.find(name2);
        boolean isFriendly = root1.equals(friendly) || root2.equals(friendly);
        boolean isUnfriendly = root1.equals(unfriendly) || root2.equals(unfriendly);
        disjointSubsets.union(root1, root2);
        if (isFriendly) {
            friendly = disjointSubsets.find(root1);
        } else if (isUnfriendly) {
            unfriendly = disjointSubsets.find(root1);
        }
    }

    public void addPerson(String name) {
        if (!disjointSubsets.map.containsKey(name)) {
            disjointSubsets.addSubset(name);
        }
    }

    public void friendly(String name) {
        if (!disjointSubsets.map.containsKey(name)) {
            disjointSubsets.addSubset(name);
        }
        disjointSubsets.union(disjointSubsets.find(name), friendly);
        friendly = disjointSubsets.find(friendly);
    }

    public void unfriendly(String name) {
        if (!disjointSubsets.map.containsKey(name)) {
            disjointSubsets.addSubset(name);
        }
        disjointSubsets.union(disjointSubsets.find(name), unfriendly);
        unfriendly = disjointSubsets.find(unfriendly);
    }


    public Network memberOfNetwork(String name) {
        if (!disjointSubsets.map.containsKey(name)) {
            disjointSubsets.addSubset(name);
        }
        if (disjointSubsets.find(name).equals(friendly)) {
            return Network.FRIENDLY;
        }
        else if (disjointSubsets.find(name).equals(unfriendly)) {
            return Network.UNFRIENDLY;
        } else {
            return Network.UNKNOWN;
        }

    }
}