package sample;

import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SelectController implements Initializable {

    @FXML private JFXListView<Ballot> listView1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //System.out.println(new VoteCasting().selected1.size());
        listView1.setItems(new VoteCasting().selected1);
        listView1.setCellFactory(ballotItemListView -> new SelectListViewCell());
    }
}
