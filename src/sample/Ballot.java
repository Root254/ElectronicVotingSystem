package sample;

import javafx.beans.property.SimpleStringProperty;

public class Ballot {
    private SimpleStringProperty name, id;
    private SimpleStringProperty avatar;

    public Ballot(String id, String name, String avatar) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.avatar = new SimpleStringProperty(avatar);
    }

    public String getId() {
        return id.get();
    }

//    public SimpleStringProperty idProperty() {
//        return id;
//    }

    public void setId(String id) {
        this.id.set(id);
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
