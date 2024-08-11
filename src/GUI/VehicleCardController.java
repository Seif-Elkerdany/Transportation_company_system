package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class VehicleCardController {
    @FXML
    private Label VehicleCard;



    public void setData(String VehicleData) {
        VehicleCard.setText(VehicleData);
    }
}
