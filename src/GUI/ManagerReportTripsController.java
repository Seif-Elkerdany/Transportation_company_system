package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ManagerReportTripsController {
    @FXML
    private Label MainLabel;

    public void setData(String Data) {
        MainLabel.setText(Data);
    }
}
