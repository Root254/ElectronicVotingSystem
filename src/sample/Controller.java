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
        styleActiveSidebarTab(actionEvent);
    }

    @FXML private void goToVoterRegistration(ActionEvent actionEvent) {
        loadCenterLayout("voter_registration");
        styleActiveSidebarTab(actionEvent);
    }

    @FXML private void goToViewVoters(ActionEvent actionEvent) {
        loadCenterLayout("view_voter");
        styleActiveSidebarTab(actionEvent);
    }

    @FXML private void goToCandidateRegistration(ActionEvent actionEvent) {
        loadCenterLayout("candidate_registration");
        styleActiveSidebarTab(actionEvent);
    }

    @FXML private void goToViewCandidates(ActionEvent actionEvent) {
        loadCenterLayout("view_candidate");
        styleActiveSidebarTab(actionEvent);
    }

    @FXML private void goToResults(ActionEvent actionEvent) {
        loadCenterLayout("view_results");
        styleActiveSidebarTab(actionEvent);
    }

    @FXML private void goToWinners(ActionEvent actionEvent) {
        loadCenterLayout("winners");
        styleActiveSidebarTab(actionEvent);
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
}
