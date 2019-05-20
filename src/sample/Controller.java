package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public JFXButton manageElection;
    /**
     *
     * This Controller class manages navigation through the sidebar of the dashboard.
     *
     */
    @FXML private BorderPane borderpane;
    @FXML private JFXButton dashboard;
    @FXML private JFXButton voterRegistration;
    @FXML private JFXButton viewVoters;
    @FXML private JFXButton candidateRegistration;
    @FXML private JFXButton viewCandidates;
    @FXML private JFXButton results;
    @FXML private JFXButton winners;
    private Stage window;

    @FXML private void goToDashboard(ActionEvent actionEvent) {
        loadCenterLayout("dashboard");
        styleActiveSidebarTab(actionEvent);
        voterRegistration.setStyle("-fx-background-color: transparent");
        manageElection.setStyle("-fx-background-color: transparent");
        viewVoters.setStyle("-fx-background-color: transparent");
        candidateRegistration.setStyle("-fx-background-color: transparent");
        viewCandidates.setStyle("-fx-background-color: transparent");
        results.setStyle("-fx-background-color: transparent");
        winners.setStyle("-fx-background-color: transparent");
    }

    @FXML private void goToVoterRegistration(ActionEvent actionEvent) {
        loadCenterLayout("voter_registration");
        styleActiveSidebarTab(actionEvent);
        dashboard.setStyle("-fx-background-color: transparent");
        manageElection.setStyle("-fx-background-color: transparent");
        viewVoters.setStyle("-fx-background-color: transparent");
        candidateRegistration.setStyle("-fx-background-color: transparent");
        viewCandidates.setStyle("-fx-background-color: transparent");
        results.setStyle("-fx-background-color: transparent");
        winners.setStyle("-fx-background-color: transparent");
    }

    @FXML private void goToViewVoters(ActionEvent actionEvent) {
        loadCenterLayout("view_voter");
        styleActiveSidebarTab(actionEvent);
        dashboard.setStyle("-fx-background-color: transparent");
        manageElection.setStyle("-fx-background-color: transparent");
        voterRegistration.setStyle("-fx-background-color: transparent");
        candidateRegistration.setStyle("-fx-background-color: transparent");
        viewCandidates.setStyle("-fx-background-color: transparent");
        results.setStyle("-fx-background-color: transparent");
        winners.setStyle("-fx-background-color: transparent");
    }

    @FXML private void goToManageElection(ActionEvent actionEvent) {
        loadCenterLayout("manage_election");
        styleActiveSidebarTab(actionEvent);
        dashboard.setStyle("-fx-background-color: transparent");
        voterRegistration.setStyle("-fx-background-color: transparent");
        candidateRegistration.setStyle("-fx-background-color: transparent");
        viewVoters.setStyle("-fx-background-color: transparent");
        viewCandidates.setStyle("-fx-background-color: transparent");
        results.setStyle("-fx-background-color: transparent");
        winners.setStyle("-fx-background-color: transparent");
    }

    @FXML private void goToCandidateRegistration(ActionEvent actionEvent) {
        loadCenterLayout("candidate_registration");
        styleActiveSidebarTab(actionEvent);
        dashboard.setStyle("-fx-background-color: transparent");
        manageElection.setStyle("-fx-background-color: transparent");
        voterRegistration.setStyle("-fx-background-color: transparent");
        viewVoters.setStyle("-fx-background-color: transparent");
        viewCandidates.setStyle("-fx-background-color: transparent");
        results.setStyle("-fx-background-color: transparent");
        winners.setStyle("-fx-background-color: transparent");
    }

    @FXML private void goToViewCandidates(ActionEvent actionEvent) {
        loadCenterLayout("view_candidate");
        styleActiveSidebarTab(actionEvent);
        dashboard.setStyle("-fx-background-color: transparent");
        manageElection.setStyle("-fx-background-color: transparent");
        voterRegistration.setStyle("-fx-background-color: transparent");
        candidateRegistration.setStyle("-fx-background-color: transparent");
        viewVoters.setStyle("-fx-background-color: transparent");
        results.setStyle("-fx-background-color: transparent");
        winners.setStyle("-fx-background-color: transparent");
    }

    @FXML private void goToResults(ActionEvent actionEvent) {
        loadCenterLayout("view_results");
        styleActiveSidebarTab(actionEvent);
        dashboard.setStyle("-fx-background-color: transparent");
        manageElection.setStyle("-fx-background-color: transparent");
        voterRegistration.setStyle("-fx-background-color: transparent");
        candidateRegistration.setStyle("-fx-background-color: transparent");
        viewVoters.setStyle("-fx-background-color: transparent");
        viewCandidates.setStyle("-fx-background-color: transparent");
        winners.setStyle("-fx-background-color: transparent");
    }

    @FXML private void goToWinners(ActionEvent actionEvent) {
        loadCenterLayout("winners");
        styleActiveSidebarTab(actionEvent);
        dashboard.setStyle("-fx-background-color: transparent");
        manageElection.setStyle("-fx-background-color: transparent");
        voterRegistration.setStyle("-fx-background-color: transparent");
        candidateRegistration.setStyle("-fx-background-color: transparent");
        viewCandidates.setStyle("-fx-background-color: transparent");
        results.setStyle("-fx-background-color: transparent");
        viewVoters.setStyle("-fx-background-color: transparent");
    }

    /**
     *
     * Sets the BorderPane's center
     *
     * @param fileName
     */
    private void loadCenterLayout(String fileName) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(fileName + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        borderpane.setCenter(root);
    }

    /**
     *
     * Styles the active jfx-button in the sidebar
     *
     * @param actionEvent
     */
    private void styleActiveSidebarTab(ActionEvent actionEvent) {
        JFXButton activeBtn = (JFXButton) actionEvent.getSource();
        activeBtn.setStyle("-fx-background-color: #03000a; -fx-background-radius: 20px;");
    }

    public void logout(ActionEvent actionEvent) {
        try {
            Parent shift = FXMLLoader.load(getClass().getResource("welcome_screen.fxml"));
            window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            window.setScene(new Scene(shift));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Connection con = DbConnector.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT Start_Date FROM voter_db.election_details");
            rs.next();


            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if (format.format(new Date()).equals(rs.getString("Start_Date")) || new Date().after(format.parse(rs.getString("Start_Date")))) {
                voterRegistration.setDisable(true);
                candidateRegistration.setDisable(true);
            }
            else {
                voterRegistration.setDisable(false);
                candidateRegistration.setDisable(false);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }
}
