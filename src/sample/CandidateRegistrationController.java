package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CandidateRegistrationController implements Initializable {
    @FXML private ImageView pic;
    @FXML private JFXButton btnSave;
    @FXML private FontAwesomeIconView tick;
    @FXML private Label msg;
    @FXML private Label errMsg;
    @FXML private JFXTextField admField;
    @FXML private JFXTextField nameField;
    @FXML private JFXComboBox schoolCombo;
    @FXML private JFXComboBox genderCombo;
    @FXML private JFXTextField emailField;
    @FXML private JFXTextField telephoneField;
    @FXML private JFXComboBox pstnCombo;

    private ObservableList<String> schoolInitials = FXCollections.observableArrayList("SPAS", "SHSS", "SASA", "SBE", "SEES", "SoE");
    private ObservableList<String> genderInitials = FXCollections.observableArrayList("Male", "Female");
    private ObservableList<ContestedPost> positionsData = FXCollections.observableArrayList();
    public ObservableList<String> pstnInitials = FXCollections.observableArrayList();
    private Image image;

    @FXML private void populateFields(MouseEvent mouseEvent) {
        tick.setVisible(false);
        msg.setVisible(false);
        if (admField.getText().isEmpty()) {
            admField.requestFocus();
            admField.setStyle("-jfx-focus-color: red;");
            errMsg.setText("Please enter admission number.");
            errMsg.setStyle("-fx-font-size: 12; -fx-text-fill: red;");
            errMsg.setVisible(true);
        }
        else {
            try {
                Connection con = DbConnector.getConnection();
                ResultSet rs = con.createStatement().executeQuery("SELECT * FROM voter_db.voter_register WHERE VoterID = '"+admField.getText()+"'");
                ResultSet resultSet = con.createStatement().executeQuery("SELECT * FROM voter_db.candidate_register WHERE CandidateID = '"+admField.getText()+"'");

                if (!rs.next()) {
                    nameField.clear();
                    schoolCombo.setValue(null);
                    genderCombo.setValue(null);
                    emailField.clear();
                    telephoneField.clear();

                    errMsg.setText("Invalid ID");
                    errMsg.setStyle("-fx-font-size: 12; -fx-text-fill: red;");
                    errMsg.setVisible(true);
                }
                else {
                    if (resultSet.next()) {
                        errMsg.setText("Already registered as a candidate.");
                        errMsg.setStyle("-fx-font-size: 12; -fx-text-fill: red;");
                        errMsg.setVisible(true);
                        admField.requestFocus();
                        admField.setStyle("-jfx-focus-color: red;");
                    }
                    else {
                        nameField.setText(rs.getString("Name"));
                        schoolCombo.setValue(rs.getString("School"));
                        genderCombo.setValue(rs.getString("Gender"));
                        image = new Image(rs.getString("Avatar"), 100, 100, true, true);
                        pic.setImage(image);
                        emailField.setText(rs.getString("Email"));
                        telephoneField.setText(Integer.toString(rs.getInt("Mobile")));
                        btnSave.setDisable(false);
                        errMsg.setVisible(false);
                    }
                }
                rs.close();
                con.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML private void saveFields(ActionEvent actionEvent) {
        try {
            Connection con = DbConnector.getConnection();
            PreparedStatement stmt = con.prepareStatement("INSERT INTO voter_db.candidate_register VALUES (?,?,?,?,?,?,?,?)");

            stmt.setString(1, admField.getText());
            stmt.setString(2, nameField.getText());
            stmt.setString(3, schoolCombo.getValue().toString());
            stmt.setString(4, genderCombo.getValue().toString());
            stmt.setString(5, pic.getImage().impl_getUrl());
            stmt.setString(6, emailField.getText());
            stmt.setInt(7, Integer.valueOf(telephoneField.getText()));
            stmt.setString(8, pstnCombo.getValue().toString());
            int i = stmt.executeUpdate();

            if (i > 0) {
                msg.setVisible(true);
                tick.setVisible(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clear();
        admField.setStyle("-jfx-focus-color: #4059a9;");
    }

    @FXML private void clearFields(ActionEvent actionEvent) {
        clear();
        errMsg.setVisible(false);
        tick.setVisible(false);
        msg.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        schoolCombo.setItems(schoolInitials);
        genderCombo.setItems(genderInitials);
        setPstnCombo();
        pstnCombo.setItems(pstnInitials);
    }

    public void setPstnCombo() {
        try {
            Connection con = DbConnector.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM voter_db.contested_posts");

            while (rs.next()) {
                //positionsData.add(new ContestedPost(rs.getInt("Post_ID"), rs.getString("Post_Name")));
                pstnInitials.add(rs.getString("Post_Name"));
            }
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clear() {
        admField.clear();
        nameField.clear();
        schoolCombo.setValue(null);
        genderCombo.setValue(null);
        pstnCombo.setValue(null);
        emailField.clear();
        telephoneField.clear();
        pic.setImage(null);
        btnSave.setDisable(true);
    }
}
