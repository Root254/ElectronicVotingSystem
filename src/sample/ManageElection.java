package sample;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ManageElection implements Initializable {
    @FXML private Label electionNameLbl;
    @FXML private Label statusLbl;
    @FXML private JFXListView<String> postsListview;
    @FXML private StackPane stackpane;
    @FXML private Label date;
    @FXML private JFXButton reset;

    @FXML private void resetElection(ActionEvent actionEvent) {
        try {
            Connection con = DbConnector.getConnection();
            Statement statement = con.createStatement();
            int delCands = statement.executeUpdate("DELETE FROM voter_db.candidate_register");
            int delVoters = statement.executeUpdate("DELETE FROM voter_db.voter_register");
            int delResults = statement.executeUpdate("DELETE FROM voter_db.results");
            int delElection = statement.executeUpdate("DELETE FROM voter_db.election_details");
            int delPosts = statement.executeUpdate("DELETE FROM voter_db.contested_posts");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML private void update(ActionEvent actionEvent) {
        //Heading
        Label text = new Label("Update");
        text.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: grey;");

        //Body
        VBox vBox = new VBox(15);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        JFXTextField name = new JFXTextField();
        JFXDatePicker startDate = new JFXDatePicker();
        JFXTextField Id = new JFXTextField();
        Id.setPromptText("Post ID");
        Id.setLabelFloat(true);
        JFXTextField postName = new JFXTextField();
        postName.setPromptText("Post Name");
        postName.setLabelFloat(true);
        startDate.setStyle("-fx-pref-height: 30px; -fx-pref-width: 200px;");
        Label lbl1 = new Label("Election name");
        lbl1.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
        Label lbl2 = new Label("Start date");
        lbl2.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
        Label lbl3 = new Label("Add Contested post");
        lbl3.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: grey;");
        Label lbl4 = new Label("Contested posts");
        lbl4.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");

        GridPane.setConstraints(lbl1, 0, 0);
        GridPane.setConstraints(lbl2, 0, 1);
        GridPane.setConstraints(lbl3, 0, 2, 2, 1);
        GridPane.setConstraints(Id,0,3);
        GridPane.setConstraints(name, 1, 0);
        GridPane.setConstraints(startDate, 1, 1);
        GridPane.setConstraints(postName,1,3);
        gridPane.getChildren().addAll(lbl1, name, lbl2, startDate, lbl3, Id, postName);

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setPrefWidth(500);
        dialogLayout.setPrefHeight(500);
        dialogLayout.setHeading(text);
        dialogLayout.setBody(gridPane);

        JFXDialog dialog = new JFXDialog(stackpane, dialogLayout, JFXDialog.DialogTransition.CENTER);
        JFXButton btn = new JFXButton("Save");
        JFXButton btn1 = new JFXButton("Cancel");
        btn.setStyle("-fx-background-color: #039be5;\n" +
                "    -fx-background-radius: 20px;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-font-family: \"Times New Roman\";\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-font-size: 18px;\n" +
                "    -fx-alignment: center;");
        btn.setOnAction(event -> {
            try {
                Connection con = DbConnector.getConnection();
                ResultSet rs = con.createStatement().executeQuery("SELECT Name, Start_Date FROM voter_db.election_details");
                rs.next();

                if (rs.getRow() < 1) {
                    try {
                        PreparedStatement stmt = con.prepareStatement("INSERT INTO voter_db.election_details VALUES (?,?,?)");

                        stmt.setString(1, name.getText());
                        stmt.setString(2, startDate.getValue().toString());
                        stmt.setInt(3, 1);
                        stmt.executeUpdate();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    dialog.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (name.getText().isEmpty() && startDate.getValue() == null && Id.getText().isEmpty() && postName.getText().isEmpty()) {
                dialog.close();
            }
            else {
                if (!name.getText().isEmpty()) {
                    try {
                        Connection con = DbConnector.getConnection();
                        PreparedStatement stmt = con.prepareStatement("UPDATE voter_db.election_details SET Name = ? WHERE ID = 1");

                        stmt.setString(1, name.getText());
                        stmt.executeUpdate();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (startDate.getValue() != null) {
                    try {
                        Connection con = DbConnector.getConnection();
                        PreparedStatement stmt = con.prepareStatement("UPDATE voter_db.election_details SET Start_Date = ? WHERE ID = 1");

                        stmt.setString(1, startDate.getValue().toString());
                        stmt.executeUpdate();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (!Id.getText().isEmpty() && !postName.getText().isEmpty()) {
                    try {
                        Connection con = DbConnector.getConnection();
                        PreparedStatement stmt = con.prepareStatement("INSERT INTO voter_db.contested_posts VALUES (?,?)");

                        stmt.setString(1, Id.getText());
                        stmt.setString(2, postName.getText());
                        stmt.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                dialog.close();
            }
        });

        btn1.setStyle("-fx-background-color: transparent;\n" +
                "    -fx-background" +
                "-radius: 20px;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-font-family: \"Times New Roman\";\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-font-size: 18px;\n" +
                "    -fx-alignment: center;");
        btn1.setOnAction(event -> {
            dialog.close();
        });
        dialogLayout.setActions(btn, btn1);
        dialog.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CandidateRegistrationController cntl = new CandidateRegistrationController();
        cntl.setPstnCombo();
        postsListview.setItems(cntl.pstnInitials);

        try {
            Connection con = DbConnector.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT Name, Start_Date FROM voter_db.election_details");
            rs.next();

            electionNameLbl.setText(rs.getString("Name"));
            date.setText(rs.getString("Start_Date"));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if (new Date().before(format.parse(rs.getString("Start_Date")))) {
                reset.setVisible(false);
                statusLbl.setText("Pending...");
            }
            else
            if (format.format(new Date()).equals(rs.getString("Start_Date"))){
                reset.setVisible(false);
                statusLbl.setText("Active");
            }
            else {
                reset.setVisible(true);
                reset.setTooltip(new Tooltip("Delete ALL election data"));
                statusLbl.setText("Closed");
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }
}
