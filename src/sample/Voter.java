package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;

public class Voter {
    private SimpleStringProperty name;
    private SimpleStringProperty school;
    private SimpleStringProperty gender;
    private SimpleStringProperty email;
    private SimpleIntegerProperty telephone;
    private ImageView avatar;
    private SimpleStringProperty admissionNumber;

    Voter(String admissionNumber, String name, String school, String gender, ImageView avatar, String email, Integer telephone) {
        this.admissionNumber = new SimpleStringProperty(admissionNumber);
        this.name = new SimpleStringProperty(name);
        this.school = new SimpleStringProperty(school);
        this.gender = new SimpleStringProperty(gender);
        this.avatar = avatar;
        this.email = new SimpleStringProperty(email);
        this.telephone = new SimpleIntegerProperty(telephone);
    }

    public String getName() {
        return name.get();
    }

//    public SimpleStringProperty nameProperty() {
//        return name;
//    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSchool() {
        return school.get();
    }

//    public SimpleStringProperty schoolProperty() {
//        return school;
//    }

    public void setSchool(String school) {
        this.school.set(school);
    }

    public String getGender() {
        return gender.get();
    }

//    public SimpleStringProperty genderProperty() {
//        return gender;
//    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getEmail() {
        return email.get();
    }

//    public SimpleStringProperty emailProperty() {
//        return email;
//    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public int getTelephone() {
        return telephone.get();
    }

//    public SimpleIntegerProperty telephoneProperty() {
//        return telephone;
//    }

    public void setTelephone(int telephone) {
        this.telephone.set(telephone);
    }

    public ImageView getAvatar() {
        return avatar;
    }

//    public SimpleStringProperty avatarProperty() {
//        return avatar;
//    }

    public void setAvatar(ImageView avatar) {
        this.avatar = avatar;
    }

    public String getAdmissionNumber() {
        return admissionNumber.get();
    }

//    public SimpleStringProperty admissionNumberProperty() {
//        return admissionNumber;
//    }

    public void setAdmissionNumber(String admissionNumber) {
        this.admissionNumber.set(admissionNumber);
    }
}
