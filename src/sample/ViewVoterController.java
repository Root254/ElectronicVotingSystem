package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewVoterController implements Initializable {
    @FXML private TableView<Voter> voterTable;
    @FXML private TableColumn<Voter, ImageView> imgCol;
    @FXML private TableColumn<Voter, String> idCol;
    @FXML private TableColumn<Voter, String> nameCol;
    @FXML private TableColumn<Voter, String> schoolCol;
    @FXML private TableColumn<Voter, Integer> telCol;

    private ObservableList<Voter> voterData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Connection connection = DbConnector.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM voter_db.voter_register");

            while (resultSet.next()) {
                voterData.add(new Voter(resultSet.getString("VoterID"), resultSet.getString("Name"), resultSet.getString("School"), resultSet.getString("Gender"), new ImageView(new Image(resultSet.getString("Avatar"), 100, 100, true, true)), resultSet.getString("Email"), resultSet.getInt("Mobile")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        imgCol.setCellValueFactory(new PropertyValueFactory<>("avatar"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("admissionNumber"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        schoolCol.setCellValueFactory(new PropertyValueFactory<>("school"));
        telCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        voterTable.setItems(voterData);
    }
}
