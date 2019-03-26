package sample;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StaffLoginController {
    public JFXButton staff_login;
    Stage window;

    public void handleStaffLogin(ActionEvent actionEvent) throws IOException {
        Parent staffLog = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(new Scene(staffLog));
    }
}
