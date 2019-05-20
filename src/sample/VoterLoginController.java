package sample;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class VoterLoginController implements Initializable {
    @FXML private JFXButton mvBtn;
    @FXML private Label errMsg;
    @FXML private Label main_label;
    @FXML private StackPane stackpane;
    @FXML private JFXTextField voterIDField;
    @FXML private JFXPasswordField pwdField;
    @FXML private JFXButton voter_login;

    private Stage window;

    public void validateField(MouseEvent mouseEvent) {
        if (voterIDField.getText().isEmpty()) {
            mvBtn.setVisible(false);
            voterIDField.requestFocus();
            voterIDField.setStyle("-jfx-focus-color: red;");
            errMsg.setText("Please enter your VoterID");
            errMsg.setStyle("-fx-font-size: 12; -fx-text-fill: red;");
        }
        else {
            try {
                Connection con = DbConnector.getConnection();
                ResultSet rs = con.createStatement().executeQuery("SELECT * FROM voter_db.voter_register WHERE VoterID = '"+voterIDField.getText()+"'");
                rs.next();

                if (rs.getRow() < 1) {
                    mvBtn.setVisible(false);
                    errMsg.setText("Not registered as a voter");
                    errMsg.setStyle("-fx-font-size: 12; -fx-text-fill: red;");
                    voterIDField.requestFocus();
                    voterIDField.setStyle("-jfx-focus-color: red;");
                }
                else {
                    if (rs.getInt("Status") == 1) {
                        mvBtn.setVisible(false);
                        errMsg.setText("Already voted");
                        errMsg.setStyle("-fx-text-fill: red");
                    }
                    else {
                        voterIDField.setStyle("-jfx-focus-color: #4059a9;");
                        errMsg.setText(null);
                        mvBtn.setVisible(true);
                    }


                    try {
                        ResultSet resultSet = con.createStatement().executeQuery("SELECT Status FROM voter_db.voter_register WHERE VoterID = '"+voterIDField.getText()+"'");
                        resultSet.next();


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML private void manualVerification(ActionEvent actionEvent) {
        Label name = new Label("StaffID");
        Label pwd = new Label("Password");
        JFXTextField sName = new JFXTextField();
        JFXPasswordField sPwd = new JFXPasswordField();

        GridPane gridPane = new GridPane();
        gridPane.setVgap(20.0);
        gridPane.setHgap(20.0);
        GridPane.setConstraints(name, 0, 0);
        GridPane.setConstraints(pwd, 0, 1);
        GridPane.setConstraints(sName, 1, 0);
        GridPane.setConstraints(sPwd, 1, 1);
        gridPane.getChildren().addAll(name, pwd, sName, sPwd);

        JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
        jfxDialogLayout.setPrefHeight(300.0);
        jfxDialogLayout.setPrefWidth(400.0);
        jfxDialogLayout.setBody(gridPane);

        JFXDialog dialog = new JFXDialog(stackpane, jfxDialogLayout, JFXDialog.DialogTransition.CENTER);
        JFXButton button1 = new JFXButton("Cancel");
        button1.setStyle("-fx-background-color: white;\n" +
                "    -fx-background-radius: 20px;\n" +
                "    -fx-text-fill: #4d4d4d;\n" +
                "    -fx-font-family: \"Times New Roman\";\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-font-size: 18px;\n" +
                "    -fx-alignment: center;");
        JFXButton button = new JFXButton("Continue");
        button1.setOnAction(event -> dialog.close());
        button.setOnAction(e -> {
            try {
                Connection connection = DbConnector.getConnection();
                ResultSet resultSet = connection.createStatement().executeQuery("SELECT Pwd FROM staff_db.staff_details WHERE StaffID = '"+sName.getText()+"'");
                resultSet.next();

                if (resultSet.getString("Pwd") == null) {
                    sPwd.requestFocus();
                    sPwd.setStyle("-jfx-focus-color: red;");
                }
                else {

                    if (resultSet.getString("Pwd").equals(sPwd.getText())) {
                        dialog.close();
                        Connection con = DbConnector.getConnection();
                        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM voter_db.voter_register WHERE VoterID = '"+voterIDField.getText()+"'");
                        rs.next();

//                        setVoted(rs.getString("VoterID"));
                        ImageView imageView = new ImageView(new Image(rs.getString("Avatar"), 200, 200, true, true));
                        Label lbl1 = new Label("Voter's Name");
                        Label lbl2 = new Label("Voter's ID");
                        Label lbl3 = new Label("School");
                        Label lbl4 = new Label(rs.getString("Name"));
                        Label lbl5 = new Label(rs.getString("VoterID"));
                        Label lbl6 = new Label(rs.getString("School"));

                        GridPane gridPane1 = new GridPane();
                        gridPane1.setHgap(20.0);
                        gridPane1.setVgap(20.0);
                        GridPane.setConstraints(lbl1, 0, 0);
                        GridPane.setConstraints(lbl2, 0, 1);
                        GridPane.setConstraints(lbl3, 0, 2);
                        GridPane.setConstraints(lbl4, 1, 0);
                        GridPane.setConstraints(lbl5, 1, 1);
                        GridPane.setConstraints(lbl6, 1, 2);
                        gridPane1.getChildren().addAll(lbl1, lbl2, lbl3, lbl4, lbl5, lbl6);

                        VBox vBox = new VBox(45);
                        vBox.getChildren().addAll(imageView, gridPane1);

                        HBox hBox = new HBox();
                        hBox.setAlignment(Pos.CENTER);
                        hBox.getChildren().add(vBox);

                        AnchorPane anchorPane = new AnchorPane();
                        anchorPane.setPrefSize(550, 450);
                        AnchorPane.setTopAnchor(hBox, 0.0);
                        AnchorPane.setRightAnchor(hBox, 0.0);
                        AnchorPane.setLeftAnchor(hBox, 0.0);
                        anchorPane.getChildren().add(hBox);

                        JFXDialogLayout content = new JFXDialogLayout();
                        content.setPrefHeight(550.0);
                        content.setPrefWidth(450.0);
                        content.setBody(anchorPane);

                        JFXDialog dialog2 = new JFXDialog(stackpane, content, JFXDialog.DialogTransition.CENTER);
                        JFXButton cancelBtn = new JFXButton("Cancel");
                        JFXButton verificationBtn = new JFXButton("Verify");
                        cancelBtn.setStyle("-fx-background-color: white;\n" +
                                "    -fx-background-radius: 20px;\n" +
                                "    -fx-text-fill: #4d4d4d;\n" +
                                "    -fx-font-family: \"Times New Roman\";\n" +
                                "    -fx-font-weight: bold;\n" +
                                "    -fx-font-size: 18px;\n" +
                                "    -fx-alignment: center;");
                        cancelBtn.setOnAction(event -> dialog2.close());
                        verificationBtn.setOnAction(event -> {
                            try {
                                setVoted(rs.getString("VoterID"));
                                Parent parent = FXMLLoader.load(getClass().getResource("vote_casting.fxml"));
                                window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                                window.setScene(new Scene(parent));
                            } catch (IOException | SQLException se) {
                                se.printStackTrace();
                            }
                        });
                        content.setActions(verificationBtn, cancelBtn);
                        dialog2.show();
                    }
                }
                resultSet.close();
                connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        });

        jfxDialogLayout.setActions(button, button1);
        dialog.show();
    }

    @FXML private void autoVerification(ActionEvent actionEvent) {
    }

    @FXML private void switchWindow(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("welcome_screen.fxml"));
            window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(new Scene(parent));
        } catch (IOException se) {
            se.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void setVoted(String adm) {
        try {
            Connection con = DbConnector.getConnection();
            PreparedStatement stmt = con.prepareStatement("UPDATE voter_db.voter_register SET Status = ? WHERE VoterID = ?");
            stmt.setInt(1, 1);
            stmt.setString(2, adm);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
