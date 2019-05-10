package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Result {
    private SimpleStringProperty name;
    private ImageView avatar;
    private SimpleIntegerProperty votes;

    Result(String name, String avatar, Integer votes) {
        this.name = new SimpleStringProperty(name);
        this.avatar = new ImageView(new Image(avatar, 100, 100, true, true));
        this.votes = new SimpleIntegerProperty(votes);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ImageView getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageView avatar) {
        this.avatar = avatar;
    }

    public int getVotes() {
        return votes.get();
    }

    public SimpleIntegerProperty votesProperty() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes.set(votes);
    }
}
