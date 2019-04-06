package sample;

public class Voter {
    protected String name;
    protected String school;
    protected String gender;
    protected String email;
    protected String telephone;
    protected String avatar;
    protected String admissionNumber;

    public Voter() {}

    public Voter(String admissionNumber, String name, String avatar, String school, String gender, String email, String telephone) {
        this.admissionNumber = admissionNumber;
        this.name = name;
        this.avatar = avatar;
        this.school = school;
        this.gender = gender;
        this.email = email;
        this.telephone = telephone;
    }
}
