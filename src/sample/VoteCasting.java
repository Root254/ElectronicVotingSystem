package sample;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class VoteCasting implements Initializable {
    @FXML private Label ballotTitle;
    @FXML JFXListView<Ballot> listView;

    ObservableList<Ballot> ballotObservableList = FXCollections.observableArrayList();;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Connection connection = DbConnector.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT Name, Avatar FROM voter_db.candidate_register");

            while (resultSet.next()) {
                ballotObservableList.add(new Ballot(resultSet.getString("Name"), resultSet.getString("Avatar")));
            }
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listView.setItems(ballotObservableList);
        listView.setCellFactory(ballotItemListView -> new BallotListviewCell());
        }
}
