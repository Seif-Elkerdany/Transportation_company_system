package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DriverTripsController {


    @FXML
    private Label DriverTripCard;



    public void setData(String DriverTripDetails) {
        DriverTripCard.setText(DriverTripDetails);
    }
}
