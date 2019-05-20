package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SelectListViewCell extends ListCell<Ballot> {
    @FXML
    private ImageView photo;
    @FXML private AnchorPane anchorpane1;
    @FXML private Label name;

    private FXMLLoader fxmlLoader;

    @Override
    public void updateItem(Ballot ballotItem, boolean empty) {
        super.updateItem(ballotItem, empty);

        if (empty || ballotItem == null) {
            setText(null);
            setGraphic(null);
        }
        else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("cellview.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            for (Ballot ballot :new VoteCasting().selected1) {
                ballotItem = ballot;
            }

            name.setText(ballotItem.getName());
            photo.setImage(new Image(ballotItem.getAvatar(), 100, 100, true, true));

            setText(null);
            setGraphic(anchorpane1);
        }
    }
}
