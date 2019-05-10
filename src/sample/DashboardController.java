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
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Label electionName;
    public Label status;
    public Label voters;
    public PieChart piechart;
    public PieChart piechart1;

    private ObservableList<Voter> voterData = FXCollections.observableArrayList();
    private ObservableList<String> voterCount = FXCollections.observableArrayList();
    private ObservableList<String> gender = FXCollections.observableArrayList("Male", "Female");
    private ObservableList<String> schoolInitials = FXCollections.observableArrayList("SPAS", "SHSS", "SASA", "SBE", "SEES", "SoE");




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Connection connection = DbConnector.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM voter_db.voter_register");

            while (resultSet.next()) {
                voterData.add(new Voter(resultSet.getString("VoterID"), resultSet.getString("Name"), resultSet.getString("School"), resultSet.getString("Gender"), new ImageView(new Image(resultSet.getString("Avatar"), 100, 100, true, true)), resultSet.getString("Email"), resultSet.getInt("Mobile")));
            }
        } catch (SQLException e) {
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


        ObservableList<PieChart.Data> piechartData = FXCollections.observableArrayList();
        for (String school : schoolInitials) {
            voterCount.clear();
            try {
                Connection connection = DbConnector.getConnection();
                ResultSet rs = connection.createStatement().executeQuery("SELECT School FROM voter_db.voter_register WHERE School = '"+school+"'");

                while (rs.next()) {
                    voterCount.add(rs.getString("School"));
                }
                for (String count : voterCount) {
                    piechartData.add(new PieChart.Data(count, voterCount.size()));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        piechart.setData(piechartData);
        piechart.setStartAngle(90.0);


    }
}
