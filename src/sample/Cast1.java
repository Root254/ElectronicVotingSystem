package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Cast1 implements Initializable {
    public BorderPane boarderlayout;
    @FXML
    JFXButton nextBtn;
    @FXML
    private VBox vbox;
    @FXML
    private HBox hbox;
    @FXML
    private Label ballotTitle;
    @FXML
    JFXListView<Ballot> listView;

    Stage window;
    FXMLLoader fxmlLoader;
    private static int voteCount = 0;
    private int numClicks = 1;
    private int extra = 0;
    private ObservableList<String> pstnNames = FXCollections.observableArrayList();
    private ObservableList<Ballot> ballotObservableList = FXCollections.observableArrayList();
    private ObservableList<Ballot> selected = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Connection con = DbConnector.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT Post_Name FROM voter_db.contested_posts");

            while (rs.next()) {
                pstnNames.add(rs.getString("Post_Name"));
            }
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (String pstn : pstnNames) {
            try {
                Connection connection = DbConnector.getConnection();
                ResultSet resultSet = connection.createStatement().executeQuery("SELECT CandidateID, Name, Avatar FROM voter_db.candidate_register WHERE Position = '" + pstn + "'");

                while (resultSet.next()) {
                    ballotObservableList.add(new Ballot(resultSet.getString("CandidateID"), resultSet.getString("Name"), resultSet.getString("Avatar")));
                }
                resultSet.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        listView.setItems(ballotObservableList);
        listView.setCellFactory(ballotItemListView -> new BallotListviewCell());
    }
}