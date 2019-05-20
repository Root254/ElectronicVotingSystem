package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StaffLoginController implements Initializable {
    @FXML private Label errMsg2;
    @FXML private Label errMsg;
    @FXML private JFXButton staff_login;
    @FXML private JFXTextField staffField;
    @FXML private JFXPasswordField pwdField;
    Stage window;

    public void handleStaffLogin(ActionEvent actionEvent) throws IOException {

        try {
            Connection con = DbConnector.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT Pwd FROM staff_db.staff_details WHERE StaffID = '"+staffField.getText()+"'");
            rs.next();

            if (rs.getRow() < 1) {
                staffField.requestFocus();
                staffField.setStyle("-jfx-focus-color: red;");
            }
            else {
                if (!rs.getString("Pwd").equals(pwdField.getText())) {
                    pwdField.requestFocus();
                    pwdField.setStyle("-jfx-focus-color: red;");
                }
                else {
                    Parent staffLog = FXMLLoader.load(getClass().getResource("main.fxml"));
                    window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

                    window.setScene(new Scene(staffLog, 1800, 1000));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void validateField(MouseEvent mouseEvent) {
        if (staffField.getText().isEmpty() || pwdField.getText().isEmpty()) {
            staff_login.setDisable(true);
        }
        else {
            pwdField.setStyle("-jfx-focus-color: #4059a9;");
            errMsg.setText(null);
            errMsg2.setText(null);
            staff_login.setDisable(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
