package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BallotListviewCell extends ListCell<Ballot> {
    @FXML private ImageView photo;
    @FXML private AnchorPane anchorpane;
    @FXML private Label name;
    @FXML private RadioButton choice;
    @FXML private CheckBox mark;

    private FXMLLoader fxmlLoader;
    private ToggleGroup group = new ToggleGroup();
    private ObservableList<Ballot> selected = FXCollections.observableArrayList();
    private ObservableList<String> pstnNames = FXCollections.observableArrayList();
    private ObservableList<Ballot> ballotObservableList = FXCollections.observableArrayList();


    @Override
    public void updateItem(Ballot ballotItem, boolean empty) {
        super.updateItem(ballotItem, empty);

        if (empty || ballotItem == null) {
            setText(null);
            setGraphic(null);
        }
        else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("listview_cell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                Connection connection = DbConnector.getConnection();
                ResultSet resultSet = connection.createStatement().executeQuery("SELECT CandidateID, Name, Avatar FROM voter_db.candidate_register");

                while (resultSet.next()) {
                    ballotObservableList.add(new Ballot(resultSet.getString("CandidateID"), resultSet.getString("Name"), resultSet.getString("Avatar")));
                }
                resultSet.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            for (Ballot ballot :new VoteCasting().ballotObservableList) {
                ballotItem = ballot;
            }

            name.setText(ballotItem.getName());
            photo.setImage(new Image(ballotItem.getAvatar(), 100, 100, true, true));

            group.selectedToggleProperty().addListener(((observable, oldValue, newValue) -> {
                if (group.getSelectedToggle() != null) {
                    choice = (RadioButton) group.getSelectedToggle();
                    choice.setSelected(isSelected());
                }
            }));
            Ballot finalBallotItem = ballotItem;

            selectedProperty().addListener(((observable1, oldValue1, newValue1) -> {
                if (newValue1) {
                    choice.setSelected(true);
                }
                if (oldValue1) {
                    choice.setSelected(false);
                }

                /*if (newValue1) {
                    selected.add(new Ballot(finalBallotItem.getId(), finalBallotItem.getName(), finalBallotItem.getAvatar()));
                    System.out.println("-------------------------------------");
                    System.out.println("-------------------------------------");
                    System.out.println("Added " +finalBallotItem.getName());
                    System.out.println("-------------------------------------");
                    System.out.println("size of selected "+selected.size());
                    System.out.println("-------------------------------------");
                    System.out.println("-------------------------------------");
                }
                if (oldValue1) {
                    selected.remove(new Ballot(finalBallotItem.getId(), finalBallotItem.getName(), finalBallotItem.getAvatar()));
                    System.out.println("-------------------------------------");
                    System.out.println("-------------------------------------");
                    System.out.println("Removed " +finalBallotItem.getName());
                    System.out.println("-------------------------------------");
                    System.out.println("size of selected "+selected.size());
                    System.out.println("-------------------------------------");
                    System.out.println("-------------------------------------");
                }

                System.out.println("out:size of selected "+selected.size());*/



                if (newValue1) {
                    selected.add(new Ballot(finalBallotItem.getId(), finalBallotItem.getName(), finalBallotItem.getAvatar()));
                    System.out.println("-------------------------------------");
                    System.out.println("-------------------------------------");
                    System.out.println("Added " +finalBallotItem.getName());
                    System.out.println("-------------------------------------");
                    System.out.println("size of selected "+selected.size());
                    System.out.println("-------------------------------------");
                    System.out.println("-------------------------------------");
                }
                if (oldValue1) {
                    selected.remove(new Ballot(finalBallotItem.getId(), finalBallotItem.getName(), finalBallotItem.getAvatar()));
                    selected.clear();
                    System.out.println("-------------------------------------");
                    System.out.println("-------------------------------------");
                    System.out.println("Removed " +finalBallotItem.getName());
                    System.out.println("-------------------------------------");
                    System.out.println("size of selected "+selected.size());
                    System.out.println("-------------------------------------");
                    System.out.println("-------------------------------------");
                }

                for (Ballot sltd : selected) {
                    int vote;
                    try {
                        Connection con = DbConnector.getConnection();
                        ResultSet rs = con.createStatement().executeQuery("SELECT Vote_count FROM voter_db.results WHERE Candidate_ID = '"+sltd.getId()+"'");
                    rs.next();

                    vote = rs.getInt("Vote_count") + 1;

                    PreparedStatement stmt = con.prepareStatement("UPDATE voter_db.results SET Vote_count = ? WHERE Candidate_ID = ?");
                    stmt.setInt(1, vote);
                    stmt.setString(2, sltd.getId());
                    stmt.executeUpdate();
                    System.out.println("Record is updated to DBUSER table!");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    System.out.println("-------------------------------------");
                    System.out.println("-------------------------------------");
                    System.out.println("selected candidates " +sltd.getName());
                    System.out.println("-------------------------------------");
                    System.out.println("-------------------------------------");
                }
            }));
            choice.setToggleGroup(group);

            setText(null);
            setGraphic(anchorpane);
        }
    }

    /*private ObservableList<Ballot> setBallotList() {
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

        for (String pstn : pstnNames) {
            try {
                Connection connection = DbConnector.getConnection();
                ResultSet resultSet = connection.createStatement().executeQuery("SELECT CandidateID, Name, Avatar FROM voter_db.candidate_register WHERE Position = '" + pstn + "'");

                while (resultSet.next()) {
                    ballotObservableList.add(new Ballot(resultSet.getString("CandidateID"), resultSet.getString("Name"), resultSet.getString("Avatar")));
                }
                resultSet.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ballotObservableList;
    }*/
}
