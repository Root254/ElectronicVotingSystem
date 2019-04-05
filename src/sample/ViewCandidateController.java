package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewCandidateController {
    public BorderPane borderpane;
    @FXML private JFXButton dashboard;
    @FXML private JFXButton voterRegistration;
    @FXML private JFXButton viewVoters;
    @FXML private JFXButton candidateRegistration;
    @FXML private JFXButton viewCandidates;
    @FXML private JFXButton results;
    @FXML private JFXButton winners;

    Stage window;

    public void goToDashboard(ActionEvent actionEvent) throws IOException {
        Parent dash = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(new Scene(dash));
    }

    public void goToVoterRegistration(ActionEvent actionEvent) throws IOException {
        Parent voterReg = FXMLLoader.load(getClass().getResource("voter_registration.fxml"));
        window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(new Scene(voterReg));
    }

    public void goToViewVoters(ActionEvent actionEvent) throws IOException {
        Parent viewVoter = FXMLLoader.load(getClass().getResource("view_voter.fxml"));
        window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(new Scene(viewVoter));
    }

    public void goToCandidateRegistration(ActionEvent actionEvent) throws IOException {
        Parent candReg = FXMLLoader.load(getClass().getResource("candidate_registration.fxml"));
        window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(new Scene(candReg));
    }

    public void goToViewCandidates(ActionEvent actionEvent) throws IOException {
        Parent viewCand = FXMLLoader.load(getClass().getResource("view_candidate.fxml"));
        window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(new Scene(viewCand));
    }

    public void goToResults(ActionEvent actionEvent) throws IOException {
        Parent rslt = FXMLLoader.load(getClass().getResource("view_results.fxml"));
        window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(new Scene(rslt));
    }

    public void goToWinners(ActionEvent actionEvent) throws IOException {
        Parent win = FXMLLoader.load(getClass().getResource("winners.fxml"));
        window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(new Scene(win));
    }
}
