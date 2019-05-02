package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;

public class Candidate extends Voter {

    private SimpleStringProperty position;

    Candidate(String admissionNumber, String name,  String school, String gender,  ImageView avatar, String email, Integer telephone, String position) {
        super(admissionNumber, name,  school, gender, avatar, email, telephone);
        this.position = new SimpleStringProperty(position);
    }

    public String getPosition() {
        return position.get();
    }

//    public SimpleStringProperty positionProperty() {
//        return position;
//    }

    public void setPosition(String position) {
        this.position.set(position);
    }
}
