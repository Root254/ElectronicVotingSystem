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

public class WinnerListviewCell extends ListCell<Winners> {

    @FXML
    private ImageView photo;
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private Label name;
    @FXML
    private RadioButton choice;
    @FXML
    private Label votes;

    private FXMLLoader fxmlLoader;
    private ToggleGroup group = new ToggleGroup();
    private ObservableList<String> pstnNames = FXCollections.observableArrayList();
    private ObservableList<Winners> ballotObservableList = FXCollections.observableArrayList();

    @Override
    public void updateItem(Winners winnerItem, boolean empty) {
        super.updateItem(winnerItem, empty);

        if (empty || winnerItem == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("cellview.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                Connection con = DbConnector.getConnection();
                ResultSet rs = con.createStatement().executeQuery("SELECT Post_Name FROM voter_db.contested_posts");

                while (rs.next()) {
                    //pstnNames.add(rs.getString("Post_Name"));
                    Connection connection = DbConnector.getConnection();
                    ResultSet resultSet = connection.createStatement().executeQuery("SELECT results.Vote_count, candidate_register.Name, candidate_register.Avatar FROM voter_db.results INNER JOIN voter_db.candidate_register ON results.Candidate_ID = candidate_register.CandidateID WHERE Post_Name = '"+rs.getString("Post_Name")+"'");

                    while (resultSet.next()) {
                        ballotObservableList.add(new Winners(resultSet.getInt("Vote_count"), resultSet.getString("Name"), resultSet.getString("Avatar")));
                    }
                }
                rs.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            for (Winners win : ballotObservableList) {
                winnerItem = win;
            }

            votes.setText(String.valueOf(winnerItem.getVotes()));
            name.setText(winnerItem.getName());
            photo.setImage(new Image(winnerItem.getPic(), 100, 100, true, true));

            setText(null);
            setGraphic(anchorpane);
        }
    }
}