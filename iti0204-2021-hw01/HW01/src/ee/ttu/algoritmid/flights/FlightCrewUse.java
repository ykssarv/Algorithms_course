package ee.ttu.algoritmid.flights;

public class FlightCrewUse implements FlightCrew {

    private final FlightCrewMemberUse pilot;
    private final FlightCrewMemberUse copilot;
    private final FlightCrewMemberUse steward;

    public FlightCrewUse(FlightCrewMemberUse pilot, FlightCrewMemberUse copilot, FlightCrewMemberUse steward) {
        this.pilot = pilot;
        this.copilot = copilot;
        this.steward = steward;
    }

    @Override
    public FlightCrewMemberUse getPilot() {
        return pilot;
    }

    @Override
    public FlightCrewMemberUse getCopilot() {
        return copilot;
    }

    @Override
    public FlightCrewMemberUse getFlightAttendant() {
        return steward;
    }

}