package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BallotListviewCell extends ListCell<Ballot> {
    @FXML private ImageView photo;
    @FXML private AnchorPane anchorpane;
    @FXML private Label name;
    @FXML private RadioButton choice;
    @FXML private CheckBox mark;

    private FXMLLoader fxmlLoader;
    private ToggleGroup group = new ToggleGroup();
    private ObservableList<Ballot> selected = FXCollections.observableArrayList();
    private ObservableList<String> pstnNames = FXCollections.observableArrayList();
    private ObservableList<Ballot> ballotObservableList = FXCollections.observableArrayList();

    @Override
    public void updateItem(Ballot ballotItem, boolean empty) {
        super.updateItem(ballotItem, empty);

        if (empty || ballotItem == null) {
            setText(null);
            setGraphic(null);
        }
        else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("listview_cell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            for (Ballot ballot :new VoteCasting().ballotObservableList) {
                ballotItem = ballot;
            }

            name.setText(ballotItem.getName());
            photo.setImage(new Image(ballotItem.getAvatar(), 100, 100, true, true));

            group.selectedToggleProperty().addListener(((observable, oldValue, newValue) -> {
                if (group.getSelectedToggle() != null) {
                    choice = (RadioButton) group.getSelectedToggle();
                    choice.setSelected(isSelected());
                }
            }));

            selectedProperty().addListener(((observable1, oldValue1, newValue1) -> {

                if (newValue1) {
                    choice.setSelected(true);
                }
                if (oldValue1) {
                    choice.setSelected(false);
                }
            }));
            choice.setToggleGroup(group);

            setText(null);
            setGraphic(anchorpane);
        }
    }
}
