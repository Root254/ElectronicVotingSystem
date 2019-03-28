package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StaffLoginController {
    @FXML private JFXButton staff_login;
    @FXML private JFXTextField staffField;
    @FXML private JFXPasswordField pwdField;
    Stage window;

    public void handleStaffLogin(ActionEvent actionEvent) throws IOException {
        Boolean status = Validator.validatePwd(pwdField.getText());
        if (status == true) {
            Parent staffLog = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            window.setScene(new Scene(staffLog));
        }
        else
            pwdField.requestFocus();
            pwdField.setStyle("-jfx-focus-color: red;");
    }
}
