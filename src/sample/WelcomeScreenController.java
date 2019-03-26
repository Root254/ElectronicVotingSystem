package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeScreenController {
    @FXML private JFXButton staff_log;
    @FXML private JFXButton voter_log;
    Stage window;

    public void goToLogin(ActionEvent actionEvent) throws IOException {
        if(actionEvent.getSource() == staff_log) {
            Parent staff = FXMLLoader.load(getClass().getResource("staff_login.fxml"));
            window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            window.setScene(new Scene(staff));
        }
        else if(actionEvent.getSource() == voter_log) {
            Parent voter = FXMLLoader.load(getClass().getResource("voter_login.fxml"));
            window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            window.setScene(new Scene(voter));
        }
        else {
            System.out.println("Sorry sucker!!!");
        }
    }
}
