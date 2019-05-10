package sample;

import com.jfoenix.controls.JFXComboBox;
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

public class ViewCandidateController implements Initializable {
    @FXML private JFXComboBox<String> selectCombo;
    @FXML private TableView<Candidate> candidateTable;
    @FXML private TableColumn<Candidate, ImageView> imgCol;
    @FXML private TableColumn<Candidate, String> idCol;
    @FXML private TableColumn<Candidate, String> nameCol;
    @FXML private TableColumn<Candidate, String> schoolCol;
    @FXML private TableColumn<Candidate, Integer> telCol;
    @FXML private TableColumn<Candidate, String> posCol;

    ObservableList<Candidate> candidateData = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CandidateRegistrationController cntl = new CandidateRegistrationController();
        cntl.setPstnCombo();
        selectCombo.setItems(cntl.pstnInitials);  //combine with filter/predicate

        selectCombo.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            candidateData.clear();
            try {
                Connection connection = DbConnector.getConnection();
                ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM voter_db.candidate_register WHERE Position = '"+newValue+"'");

                while (resultSet.next()) {
                    candidateData.add(new Candidate(resultSet.getString("CandidateID"), resultSet.getString("Name"), resultSet.getString("School"), resultSet.getString("Gender"), new ImageView(new Image(resultSet.getString("Avatar"), 100, 100, true, true)), resultSet.getString("Email"), resultSet.getInt("Mobile"), resultSet.getString("Position")));
                }
                resultSet.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));



        imgCol.setCellValueFactory(new PropertyValueFactory<>("avatar"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("admissionNumber"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        schoolCol.setCellValueFactory(new PropertyValueFactory<>("school"));
        telCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        posCol.setCellValueFactory(new PropertyValueFactory<>("position"));

        candidateTable.setItems(candidateData);
    }
}
