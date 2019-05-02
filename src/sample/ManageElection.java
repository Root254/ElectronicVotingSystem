package sample;

import com.jfoenix.controls.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageElection implements Initializable {
    public Label electionNameLbl;
    public Label statusLbl;
    public JFXListView postsListview;
    public StackPane stackpane;

    public void update(ActionEvent actionEvent) {
        //Heading
        Label text = new Label("Update");
        text.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: grey;");

        //Body
        VBox vBox = new VBox(15);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(20);
        JFXTextField name = new JFXTextField();
        JFXDatePicker startDate = new JFXDatePicker();
        JFXDatePicker regDeadline = new JFXDatePicker();
        regDeadline.setStyle("-fx-pref-height: 30px; -fx-pref-width: 200px;");
        startDate.setStyle("-fx-pref-height: 30px; -fx-pref-width: 200px;");
        Label lbl1 = new Label("Election name");
        lbl1.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
        Label lbl2 = new Label("Start date");
        lbl2.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
        Label lbl3 = new Label("Registration deadline");
        lbl3.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
        Label lbl4 = new Label("Contested posts");
        lbl4.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");

        GridPane.setConstraints(lbl1, 0, 0);
        GridPane.setConstraints(lbl2, 0, 1);
        GridPane.setConstraints(lbl3, 0, 2);
        GridPane.setConstraints(name, 1, 0);
        GridPane.setConstraints(startDate, 1, 1);
        GridPane.setConstraints(regDeadline, 1, 2);
        gridPane.getChildren().addAll(lbl1, name, lbl2, startDate, lbl3, regDeadline);

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
                "    -fx-font: bold;\n" +
                "    -fx-font-size: 18px;\n" +
                "    -fx-alignment: center;");
        btn.setOnAction(event -> {
            dialog.close();
        });

        btn1.setStyle("-fx-border-color: #039be5;\n" +
                "    -fx-border-radius: 20px;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-font-family: \"Times New Roman\";\n" +
                "    -fx-font: bold;\n" +
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
    }



}
