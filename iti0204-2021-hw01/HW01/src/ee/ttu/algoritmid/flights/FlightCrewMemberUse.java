package ee.ttu.algoritmid.flights;

public class FlightCrewMemberUse implements Comparable<FlightCrewMemberUse>, FlightCrewMember {

    private final String name;
    private final Role role;
    private final double experience;
    private final FlightCrewMember member;

    public FlightCrewMemberUse(FlightCrewMember member) {
        this.name = member.getName();
        this.role = member.getRole();
        this.experience = member.getWorkExperience();
        this.member = member;
    }

    public FlightCrewMemberUse(double experience) {
        this.name = null;
        this.role = null;
        this.experience = experience;
        this.member = null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Role getRole() {
        return role;
    }

    @Override
    public double getWorkExperience() {
        return experience;
    }

    public FlightCrewMember getMember() {
        return member;
    }

    @Override
    public int compareTo(FlightCrewMemberUse o) {
        return Double.compare(this.experience, o.experience);
    }
}