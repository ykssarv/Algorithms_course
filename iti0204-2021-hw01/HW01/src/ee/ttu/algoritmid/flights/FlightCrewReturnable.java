package ee.ttu.algoritmid.flights;

public class FlightCrewReturnable implements FlightCrew {

    private final FlightCrewMember pilot;
    private final FlightCrewMember copilot;
    private final FlightCrewMember steward;

    public FlightCrewReturnable(FlightCrewMember pilot, FlightCrewMember copilot, FlightCrewMember steward) {
        this.pilot = pilot;
        this.copilot = copilot;
        this.steward = steward;
    }

    @Override
    public FlightCrewMember getPilot() {
        return pilot;
    }

    @Override
    public FlightCrewMember getCopilot() {
        return copilot;
    }

    @Override
    public FlightCrewMember getFlightAttendant() {
        return steward;
    }

}