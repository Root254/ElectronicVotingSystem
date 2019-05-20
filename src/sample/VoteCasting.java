package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class VoteCasting implements Initializable {
    public BorderPane boarderlayout;
    @FXML JFXButton nextBtn;
    @FXML private VBox vbox;
    @FXML private HBox hbox;
    @FXML private Label ballotTitle;
    @FXML JFXListView<Ballot> listView;

    private int numClicks = 1;
    private FXMLLoader fxmlLoader;
    @FXML private JFXListView<Ballot> listView1;
    private ObservableList<String> pstnNames = FXCollections.observableArrayList();
    ObservableList<Ballot> ballotObservableList = FXCollections.observableArrayList();
    ObservableList<Ballot> selected1 = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Connection con = DbConnector.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT Post_Name FROM voter_db.contested_posts");

            while (rs.next()) {
                pstnNames.add(rs.getString("Post_Name"));
            }
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ballotTitle.setText(pstnNames.get(0));
        try {
            Connection connection = DbConnector.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT CandidateID, Name, Avatar FROM voter_db.candidate_register WHERE Position = '" + pstnNames.get(0) + "'");

            while (resultSet.next()) {
                ballotObservableList.add(new Ballot(resultSet.getString("CandidateID"), resultSet.getString("Name"), resultSet.getString("Avatar")));
            }
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listView.setItems(ballotObservableList);
        listView.setCellFactory(ballotItemListView -> new BallotListviewCell());
        listView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                nextBtn.setDisable(false);
            }
        }));
    }

    @FXML
    private void next(ActionEvent actionEvent) {
        numClicks++;
        int i = numClicks - 1;
        System.out.println(i);
        System.out.println( pstnNames.size());

        if (i < pstnNames.size()) {
            setSelected1();
            ballotTitle.setText(pstnNames.get(i));
            nextBtn.setDisable(true);

            ballotObservableList.clear();
            try {
                Connection connection = DbConnector.getConnection();
                ResultSet resultSet = connection.createStatement().executeQuery("SELECT CandidateID, Name, Avatar FROM voter_db.candidate_register WHERE Position = '" + pstnNames.get(i) + "'");

                while (resultSet.next()) {
                    ballotObservableList.add(new Ballot(resultSet.getString("CandidateID"), resultSet.getString("Name"), resultSet.getString("Avatar")));
                }
                resultSet.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            listView.setItems(ballotObservableList);
            listView.setCellFactory(ballotItemListView -> new BallotListviewCell());
            listView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    nextBtn.setDisable(false);
                }
            }));

        } else
        if (i == pstnNames.size()) {
            setSelected1();
            for (Ballot sel : selected1) {
                recordVote(sel);
            }
            nextBtn.setText("Confirm");
            nextBtn.setDisable(false);
            /*Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("select.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            boarderlayout.setCenter(root);*/
            //System.out.println(selected1.size());


            if (fxmlLoader == null) {
                pstnNames.clear();
                fxmlLoader = new FXMLLoader(getClass().getResource("select.fxml"));
                fxmlLoader.setController(this);
                Parent root = null;
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                boarderlayout.setCenter(root);
                listView1.setItems(this.selected1);
                listView1.setCellFactory(ballotItemListView -> new SelectListViewCell());
            }
            ballotTitle.setText("Your selection");
        }
        else {
            try {
                Parent shift = FXMLLoader.load(getClass().getResource("voter_login.fxml"));
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                window.setScene(new Scene(shift));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setSelected1() {
        Ballot candSelected = listView.getSelectionModel().getSelectedItem();
        selected1.add(candSelected);
    }

    private void recordVote(Ballot candChosen) {
        int vote;
        try {
            Connection con = DbConnector.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT Vote_count FROM voter_db.results WHERE Candidate_ID = '"+candChosen.getId()+"'");
            rs.next();

            vote = rs.getInt("Vote_count") + 1;

            PreparedStatement stmt = con.prepareStatement("UPDATE voter_db.results SET Vote_count = ? WHERE Candidate_ID = ?");
            stmt.setInt(1, vote);
            stmt.setString(2, candChosen.getId());
            stmt.executeUpdate();
            System.out.println("Record is added to DBUSER table!");
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
