package sample;

public class Candidate extends Voter {

    private String position;
    private int votesReceived;

    public Candidate(String admissionNumber, String name, String avatar, String school, String gender, String email, String telephone, String position, int votesReceived) {
        super(admissionNumber, name, avatar, school, gender, email, telephone);
        this.position = position;
        this.votesReceived = votesReceived;
    }
}
