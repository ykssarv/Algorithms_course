package ee.ttu.algoritmid.flights;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HW01 implements FlightCrewRegistrationSystem {

    protected BinaryTree<FlightCrewMemberUse> pilotWaitTree = new BinaryTree<>();
    protected BinaryTree<FlightCrewMemberUse> coPilotWaitTree = new BinaryTree<>();
    protected BinaryTree<FlightCrewMemberUse> stewardWaitTree = new BinaryTree<>();

    @Override
    public FlightCrew registerToFlight(FlightCrewMember participant) throws IllegalArgumentException {
        // Every attribute is required and work experience is a positive number (≥ 0).
        if (participant != null && participant.getName() != null && !participant.getName().equals("") && participant.getRole() != null && participant.getWorkExperience() >= 0) {
            //If found a fitting team, return whole team
            //If not return null
            return findTeam(participant);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public List<FlightCrewMember> crewMembersWithoutTeam() {
        // Takes all separate role waiting lists (that are all trees) and merges them into a singular list.
        // Sorted by work experience in growing order and then roles (attendant, copilot, pilot).
        List<Node<FlightCrewMemberUse>> pilots = pilotWaitTree.getIncreasingList();
        List<Node<FlightCrewMemberUse>> coPilots = coPilotWaitTree.getIncreasingList();
        List<Node<FlightCrewMemberUse>> stewards = stewardWaitTree.getIncreasingList();

        List<Node<FlightCrewMemberUse>> firstMerge = mergeList(coPilots, stewards);
        List<Node<FlightCrewMemberUse>> secondMerge = mergeList(pilots, firstMerge);

        return secondMerge.stream().map(i -> i.getValue().getMember()).collect(Collectors.toList());
    }

    public FlightCrewMemberUse findBestSteward(FlightCrewMember copilot) {
        // Finds the best fitting steward based on the copilots work experience
        return findMaxFromRange(0, copilot.getWorkExperience() - 3, stewardWaitTree);
    }

    public FlightCrewMemberUse findBestPilot(FlightCrewMember copilot) {
        // Finds the best fitting pilot based on the copilots work experience
        return findMinFromRange(copilot.getWorkExperience() + 5, copilot.getWorkExperience() + 10, pilotWaitTree);
    }

    public FlightCrewMemberUse findBestCopilotForPilot(FlightCrewMember pilot) {
        // Finds the best fitting copilot based on the pilots work experience
        return findMaxFromRange(pilot.getWorkExperience() - 10, pilot.getWorkExperience() - 5, coPilotWaitTree);
    }

    public FlightCrewMemberUse findBestCopilotForSteward(FlightCrewMember steward) {
        // Finds the best fitting copilot based on the stewards work experience
        return findMinFromRange(steward.getWorkExperience() + 3, Double.MAX_VALUE, coPilotWaitTree);
    }

    public FlightCrew findTeam(FlightCrewMember participant) {
        FlightCrewMemberUse myParticipant = new FlightCrewMemberUse(participant);

        FlightCrewMemberUse pilot;
        FlightCrewMemberUse copilot;
        FlightCrewMemberUse steward;
        FlightCrewUse flightCrew = null;

        if (participant.getRole() == FlightCrewMember.Role.PILOT) {
            pilot = myParticipant;
            pilotWaitTree.addValue(myParticipant);
            copilot = findBestCopilotForPilot(pilot);
            if (copilot != null) {
                steward = findBestSteward(copilot);
                if (steward != null) {
                    flightCrew = new FlightCrewUse(pilot, copilot, steward);
                }
            }
        }

        if (participant.getRole() == FlightCrewMember.Role.COPILOT) {
            copilot = myParticipant;
            coPilotWaitTree.addValue(myParticipant);
            pilot = findBestPilot(copilot);
            steward = findBestSteward(copilot);
            if (pilot != null && steward != null) {
                flightCrew = new FlightCrewUse(pilot, copilot, steward);
            }
        }

        if (participant.getRole() == FlightCrewMember.Role.FLIGHT_ATTENDANT) {
            steward = myParticipant;
            stewardWaitTree.addValue(myParticipant);
            copilot = findBestCopilotForSteward(steward);
            if (copilot != null) {
                pilot = findBestPilot(copilot);
                if (pilot != null) {
                    flightCrew = new FlightCrewUse(pilot, copilot, steward);
                }
            }
        }

        if (flightCrew != null) {
            FlightCrewMemberUse myPilot = flightCrew.getPilot();
            FlightCrewMemberUse myCoPilot = flightCrew.getCopilot();
            FlightCrewMemberUse mySteward = flightCrew.getFlightAttendant();

            pilotWaitTree.remove(myPilot);
            coPilotWaitTree.remove(myCoPilot);
            stewardWaitTree.remove(mySteward);

            return new FlightCrewReturnable(myPilot.getMember(), myCoPilot.getMember(), mySteward.getMember());
        }

        return null;
    }

    public FlightCrewMemberUse findMinFromRange(double minimal, double maximum, BinaryTree<FlightCrewMemberUse> tree) {
        // Find the crew member (of a certain role) with the smallest work experience from a range.
        FlightCrewMemberUse smallerMember = new FlightCrewMemberUse(minimal);
        FlightCrewMemberUse largerMember = new FlightCrewMemberUse(maximum);

        Node<FlightCrewMemberUse> minimalCrewMember = tree.find(smallerMember);

        if (minimalCrewMember == null) {
            return null;
        }

        if (0 > minimalCrewMember.getValue().compareTo(smallerMember)) {
            minimalCrewMember = minimalCrewMember.getSuccessor();
            if (minimalCrewMember == null) {
                return null;
            }
        }

        if (0 < minimalCrewMember.getValue().compareTo(largerMember)) {
            return null;
        }

        return minimalCrewMember.getValue();
    }

    public FlightCrewMemberUse findMaxFromRange(double minimal, double maximum, BinaryTree<FlightCrewMemberUse> tree) {
        // Find the crew member (of a certain role) with the largest work experience from a range.
        FlightCrewMemberUse smallerMember = new FlightCrewMemberUse(minimal);
        FlightCrewMemberUse largerMember = new FlightCrewMemberUse(maximum);

        Node<FlightCrewMemberUse> maximumCrewMember = tree.find(largerMember);

        if (maximumCrewMember == null) {
            return null;
        }

        if (0 < maximumCrewMember.getValue().compareTo(largerMember)) {
            maximumCrewMember = maximumCrewMember.getPredecessor();
            if (maximumCrewMember == null) {
                return null;
            }
        }

        if (0 > maximumCrewMember.getValue().compareTo(smallerMember)) {
            return null;
        }

        return maximumCrewMember.getValue();
    }

    private static List<Node<FlightCrewMemberUse>> mergeList(List<Node<FlightCrewMemberUse>> arrayA, List<Node<FlightCrewMemberUse>> arrayB) {
        // Merge two lists into a singular list, keeping the current order.
        List<Node<FlightCrewMemberUse>> mergedArray = new ArrayList<>(arrayA.size() + arrayB.size());

        int i=0, j=0;

        while (i < arrayA.size() && j < arrayB.size())
        {
            if (arrayA.get(i).getValue().getWorkExperience() < arrayB.get(j).getValue().getWorkExperience())
            {
                mergedArray.add(arrayA.get(i));
                i++;
            }
            else
            {
                mergedArray.add(arrayB.get(j));
                j++;
            }
        }

        while (i < arrayA.size())
        {
            mergedArray.add(arrayA.get(i));
            i++;
        }

        while (j < arrayB.size())
        {
            mergedArray.add(arrayB.get(j));
            j++;
        }

        return mergedArray;
    }
}