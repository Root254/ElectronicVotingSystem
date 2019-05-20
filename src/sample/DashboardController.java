package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Label electionName;
    public Label status;
    public Label voters;
    public PieChart piechart;
    public PieChart piechart1;
    public Label turnOut;
    public Label percent;

    private ObservableList<Voter> voterData = FXCollections.observableArrayList();
    private ObservableList<String> voterCount = FXCollections.observableArrayList();
    private ObservableList<String> count = FXCollections.observableArrayList();
    private ObservableList<String> gender = FXCollections.observableArrayList("Male", "Female");
    private ObservableList<String> schoolInitials = FXCollections.observableArrayList("SPAS", "SHSS", "SASA", "SBE", "SEES", "SoE");




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Connection con = DbConnector.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT Name, Start_Date FROM voter_db.election_details");
            rs.next();

            electionName.setText(rs.getString("Name"));

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if (!format.format(new Date()).equals(rs.getString("Start_Date"))) {
                status.setText("Election status : pending");
            }
            else {
                status.setText("Election status : active");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DbConnector.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM voter_db.voter_register");
            ResultSet rs = connection.createStatement().executeQuery("SELECT VoterID FROM voter_db.voter_register WHERE Status = 1");

            while (resultSet.next()) {
                voterData.add(new Voter(resultSet.getString("VoterID"), resultSet.getString("Name"), resultSet.getString("School"), resultSet.getString("Gender"), new ImageView(new Image(resultSet.getString("Avatar"), 100, 100, true, true)), resultSet.getString("Email"), resultSet.getInt("Mobile")));
            }
            while (rs.next()) {
                count.add(rs.getString("VoterID"));
            }
            turnOut.setText(String.valueOf(count.size()));
        } catch (SQLException | ArithmeticException | NullPointerException e) {
            e.printStackTrace();
        }
        voters.setText(String.valueOf(voterData.size()));

        ObservableList<PieChart.Data> piechartData1 = FXCollections.observableArrayList();
        for (String genderType : gender) {
            int i = 0;
            for (Voter voter : voterData) {
                if (voter.getGender().equals(genderType)) {
                    i++;
                }
            }
            piechartData1.add(new PieChart.Data(genderType, i));
        }
        piechart1.setData(piechartData1);
        piechart1.setStartAngle(90.0);

        try {
            Connection connection = DbConnector.getConnection();
            ResultSet rs = connection.createStatement().executeQuery("SELECT School FROM voter_db.voter_register");

            while (rs.next()) {
                voterCount.add(rs.getString("School"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<PieChart.Data> piechartData = FXCollections.observableArrayList();
        for (String school : schoolInitials) {
            int i = 0;
            for (String sch : voterCount) {
                if (school.equals(sch)) {
                    i++;
                }
            }
            piechartData.add(new PieChart.Data(school, i));
        }
        piechart.setData(piechartData);
        piechart.setStartAngle(90.0);
    }
}
