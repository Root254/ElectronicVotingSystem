package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class Controller {
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

    @FXML private void goToDashboard(ActionEvent actionEvent) {
        loadCenterLayout("dashboard");
    }

    @FXML private void goToVoterRegistration(ActionEvent actionEvent) {
        loadCenterLayout("voter_registration");
    }

    @FXML private void goToViewVoters(ActionEvent actionEvent) {
        loadCenterLayout("view_voter");
    }

    @FXML private void goToCandidateRegistration(ActionEvent actionEvent) {
        loadCenterLayout("candidate_registration");
    }

    @FXML private void goToViewCandidates(ActionEvent actionEvent) {
        loadCenterLayout("view_candidate");
    }

    @FXML private void goToResults(ActionEvent actionEvent) {
        loadCenterLayout("view_results");
    }

    @FXML private void goToWinners(ActionEvent actionEvent) {
        loadCenterLayout("winners");
    }

    private void loadCenterLayout(String fileName) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(fileName + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        borderpane.setCenter(root);
    }
}
