package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Winners {
    private SimpleIntegerProperty votes;
    private SimpleStringProperty name, pic;

    public Winners(Integer votes, String name, String pic) {
        this.votes = new SimpleIntegerProperty(votes);
        this.name = new SimpleStringProperty(name);
        this.pic = new SimpleStringProperty(pic);
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

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPic() {
        return pic.get();
    }

    public SimpleStringProperty picProperty() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic.set(pic);
    }
}
