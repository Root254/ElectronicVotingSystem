package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
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

public class VoteCasting implements Initializable {
    public BorderPane boarderlayout;
    @FXML
    JFXButton nextBtn;
    @FXML
    private VBox vbox;
    @FXML
    private HBox hbox;
    @FXML
    private Label ballotTitle;
    @FXML
    JFXListView<Ballot> listView;

    Stage window;
    FXMLLoader fxmlLoader;
    private static int voteCount = 0;
    private int numClicks = 1;
    private int extra = 0;
    private ObservableList<String> pstnNames = FXCollections.observableArrayList();
    ObservableList<Ballot> ballotObservableList = FXCollections.observableArrayList();
    private ObservableList<Ballot> selected = FXCollections.observableArrayList();

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
                System.out.println("-------------------------------------");
                //System.out.println("oldValue "+oldValue.getName());
                System.out.println("-------------------------------------");

                System.out.println("-------------------------------------");
                System.out.println("newValue "+newValue.getName());
                System.out.println("-------------------------------------");


                if (listView.getItems().contains(newValue)) {
                    selected.add(new Ballot(newValue.getId(), newValue.getName(), newValue.getAvatar()));
                    System.out.println("Added " +newValue.getName());
                    System.out.println("-------------------------------------");
                    System.out.println("candidate in selected "+selected.get(0).getName());
                    System.out.println("-------------------------------------");
                }
                if (oldValue != null) {
                    selected.remove(new Ballot(oldValue.getId(), oldValue.getName(), oldValue.getAvatar()));
                    System.out.println("Removed " +oldValue.getName());
                    System.out.println("-------------------------------------");
                    System.out.println("candidate in selected "+selected.get(0).getName());
                    System.out.println("-------------------------------------");

                }
            }

            //if (newValue != null) {
            // System.out.println(newValue.getName());

//                        try {
//                            Connection con = DbConnector.getConnection();
//                            ResultSet rs = con.createStatement().executeQuery("SELECT Vote_count FROM voter_db.results WHERE Candidate_ID = '"+newValue.getId()+"'");
//                            rs.next();
//                            System.out.println(newValue.getId());
//                            System.out.println("-----------------------------");
//
//                            int vote = rs.getInt("Vote_count");
//                            System.out.println(rs.getInt("Vote_count"));
//                            vote += 1;
//                            System.out.println(vote);
//                            System.out.println("-----------------------------");
//
//                            PreparedStatement stmt = con.prepareStatement("UPDATE voter_db.results SET Vote_count = ? WHERE Candidate_ID = ?");
//                            stmt.setInt(1, vote);
//                            stmt.setString(2, newValue.getId());
//                            stmt.executeUpdate();
//                            System.out.println("Record is updated to DBUSER table!");
//                        }
            //MouseButton.PRIMARY
//                        catch (SQLException | NullPointerException e) {
//                            e.printStackTrace();
//                        }
            //}
        }));


//        try {
//            Connection connection = DbConnector.getConnection();
//            ResultSet resultSet = connection.createStatement().executeQuery("SELECT Name, Avatar FROM voter_db.candidate_register");
//
//            while (resultSet.next()) {
//                ballotObservableList.add(new Ballot(resultSet.getString("Name"), resultSet.getString("Avatar")));
//            }
//            resultSet.close();
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        listView.setItems(ballotObservableList);
//        listView.setCellFactory(ballotItemListView -> new BallotListviewCell());
    }

    @FXML
    private void next(ActionEvent actionEvent) {
        numClicks++;
        System.out.println(numClicks);
        int i = numClicks - 1;
        System.out.println("vc->Index position " +i);
        System.out.println("vc----------------------------------------------");
//        setBallotObservableList();
        //setPstnNames();

        ballotObservableList.clear();
        if (i < pstnNames.size()) {
            System.out.println("Clicks " +numClicks);
            System.out.println("vc-------------------------------------");
            System.out.println("array size " +pstnNames.size());
            System.out.println("vc-------------------------------------");
            ballotTitle.setText(pstnNames.get(i));
            System.out.println("vc->current position "+pstnNames.get(i));

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

            /*listView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    System.out.println("-------------------------------------");
                    //System.out.println("oldValue "+oldValue.getName());
                    System.out.println("-------------------------------------");

                    System.out.println("-------------------------------------");
                    System.out.println("newValue "+newValue.getName());
                    System.out.println("-------------------------------------");


                    if (listView.getItems().contains(newValue)) {
                        selected.add(new Ballot(newValue.getId(), newValue.getName(), newValue.getAvatar()));

                        int vote;
                        try {
                            Connection con = DbConnector.getConnection();
                            ResultSet rs = con.createStatement().executeQuery("SELECT Vote_count FROM voter_db.results WHERE Candidate_ID = '"+newValue.getId()+"'");
                            rs.next();

                            vote = rs.getInt("Vote_count") + 1;

                            PreparedStatement stmt = con.prepareStatement("UPDATE voter_db.results SET Vote_count = ? WHERE Candidate_ID = ?");
                            stmt.setInt(1, vote);
                            stmt.setString(2, newValue.getId());
                            stmt.executeUpdate();
                            System.out.println("Record is updated to DBUSER table!");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Added " +newValue.getName());
                        System.out.println("-------------------------------------");
                        System.out.println("candidates in selected "+selected.get(i).getName());
                        System.out.println("-------------------------------------");
                    }
                    else  {
                        selected.remove(new Ballot(oldValue.getId(), oldValue.getName(), oldValue.getAvatar()));
                        System.out.println("Removed " +oldValue.getName());
                        System.out.println("-------------------------------------");
                        System.out.println("candidates in selected "+selected.get(i).getName());
                        System.out.println("-------------------------------------");
                    }

                    System.out.println("-------------------------------------");
                    System.out.println("-------------------------------------");
                    System.out.println("selected candidates " +newValue.getName());
                    System.out.println("-------------------------------------");
                    System.out.println("-------------------------------------");
                }

                //if (newValue != null) {
                   // System.out.println(newValue.getName());

//                        try {
//                            Connection con = DbConnector.getConnection();
//                            ResultSet rs = con.createStatement().executeQuery("SELECT Vote_count FROM voter_db.results WHERE Candidate_ID = '"+newValue.getId()+"'");
//                            rs.next();
//                            System.out.println(newValue.getId());
//                            System.out.println("-----------------------------");
//
//                            int vote = rs.getInt("Vote_count");
//                            System.out.println(rs.getInt("Vote_count"));
//                            vote += 1;
//                            System.out.println(vote);
//                            System.out.println("-----------------------------");
//
//                            PreparedStatement stmt = con.prepareStatement("UPDATE voter_db.results SET Vote_count = ? WHERE Candidate_ID = ?");
//                            stmt.setInt(1, vote);
//                            stmt.setString(2, newValue.getId());
//                            stmt.executeUpdate();
//                            System.out.println("Record is updated to DBUSER table!");
//                        }
                    //MouseButton.PRIMARY
//                        catch (SQLException | NullPointerException e) {
//                            e.printStackTrace();
//                        }
                //}
            }));*/


//            if (listView.getSelectionModel().getSelectedItem() !=null) {
//                try {
//                    Connection con = DbConnector.getConnection();
//                    ResultSet rs = con.createStatement().executeQuery("SELECT Vote_count FROM voter_db.results WHERE Candidate_ID = '"+listView.getSelectionModel().getSelectedItem().getId()+"'");
//                    rs.next();
//
//                    int vote = rs.getInt("Vote_count");
//                    vote += 1;
//
//                    PreparedStatement stmt = con.prepareStatement("UPDATE voter_db.results SET Vote_count = ? WHERE Candidate_ID = ?");
//                    stmt.setInt(1, vote);
//                    stmt.setString(2, listView.getSelectionModel().getSelectedItem().getId());
//                    stmt.executeUpdate();
//                    System.out.println("Record is updated to DBUSER table!");
//                }
//                catch (SQLException | NullPointerException e) {
//                    e.printStackTrace();
//                }
//            }

        } else {

            ballotTitle.setText("Your selection");
            nextBtn.setText("Finish");
            try {
                Parent shift = FXMLLoader.load(getClass().getResource("voter_login.fxml"));
                window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                window.setScene(new Scene(shift));
            } catch (IOException e) {
                e.printStackTrace();
            }





            //nextBtn.setId("finish");

            //System.out.println(nextBtn.getId());
            /*switch (extra) {
                case 1:
                    ballotTitle.setText("Your selection");
                    nextBtn.setText("Finish");
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("cast2.fxml"));
                        boarderlayout.setCenter(root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        Parent shift = FXMLLoader.load(getClass().getResource("voter_login.fxml"));
                        window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                        window.setScene(new Scene(shift));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                        if (fxmlLoader == null) {
//                            fxmlLoader = new FXMLLoader(getClass().getResource("voter_login.fxml"));
//                            try {
//                                fxmlLoader.load();
//                                System.out.println("Toka");
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            break;
            }*/

//            if (nextBtn.getId().equals("finish")) {
//
//            }
        }
    }







    @FXML private void back(ActionEvent actionEvent) {
    }

//    private void setBallotObservableList() {
//        try {
//            Connection connection = DbConnector.getConnection();
//            ResultSet resultSet = connection.createStatement().executeQuery("SELECT Name, Avatar, Position FROM voter_db.candidate_register");
//
//            while (resultSet.next()) {
//                ballotObservableList.add(new Ballot(resultSet.getString("Name"), resultSet.getString("Avatar")));
//            }
//            resultSet.close();
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    private void setPstnNames() {
//        try {
//            Connection con = DbConnector.getConnection();
//            ResultSet rs = con.createStatement().executeQuery("SELECT Post_Name FROM voter_db.contested_posts");
//
//            while (rs.next()) {
//                pstnNames.add(rs.getString("Post_Name"));
//            }
//            rs.close();
//            con.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
