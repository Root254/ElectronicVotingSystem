package sample;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewResultsController implements Initializable {
    @FXML private TableView<Result> resultsTable;
    @FXML private TableColumn<Result, ImageView> imgCol;
    @FXML private TableColumn<Result, String> nameCol;
    @FXML private TableColumn<Result, Integer>  votesCol;
    @FXML private BarChart<String, Integer> bargraph;
    @FXML private CategoryAxis candAxis;
    @FXML private NumberAxis votesAxis;
    @FXML private JFXComboBox<String> pstnCombo;

    private ObservableList<Result> resultsData = FXCollections.observableArrayList();
    private ObservableList<String> pstnInitials = FXCollections.observableArrayList();
    private ObservableList<String> votes = FXCollections.observableArrayList();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        setPstnCombo();
        pstnCombo.setItems(pstnInitials);
        pstnCombo.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            resultsData.clear();
            series.getData().clear();
            try {
                Connection con = DbConnector.getConnection();
                ResultSet rs = con.createStatement().executeQuery("SELECT CandidateID, Name, Avatar FROM voter_db.candidate_register WHERE Position = '"+newValue+"'");


                while (rs.next()) {
                    ResultSet resultSet = con.createStatement().executeQuery("SELECT Vote_count FROM voter_db.results WHERE results.Candidate_ID = '"+rs.getString("CandidateID")+"'");

                    while (resultSet.next()) {
                        resultsData.add(new Result(rs.getString("Name"), rs.getString("Avatar"), resultSet.getInt("Vote_count")));

                        series.getData().add(new XYChart.Data<>(rs.getString("CandidateID"), resultSet.getInt("Vote_count")));
                    }
                }
                bargraph.getData().add(series);

                rs.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            imgCol.setCellValueFactory(new PropertyValueFactory<>("avatar"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            votesCol.setCellValueFactory(new PropertyValueFactory<>("votes"));

            resultsTable.setItems(resultsData);
        }));
    }

    private void setPstnCombo() {
        try {
            Connection con = DbConnector.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM voter_db.contested_posts");

            while (rs.next()) {
                pstnInitials.add(rs.getString("Post_Name"));
            }
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
