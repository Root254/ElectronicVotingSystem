package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;

public class Ballot {
    private SimpleStringProperty name;
    private SimpleStringProperty avatar;

    public Ballot(String name, String avatar) {
        this.name = new SimpleStringProperty(name);
        this.avatar = new SimpleStringProperty(avatar);
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

    public String getAvatar() {
        return avatar.get();
    }

//    public SimpleStringProperty avatarProperty() {
//        return avatar;
//    }

    public void setAvatar(String avatar) {
        this.avatar.set(avatar);
    }
}
