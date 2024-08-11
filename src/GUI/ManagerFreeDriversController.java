package GUI;

import Backend.Driver;
import Backend.Manager;
import Backend.Trip;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class ManagerFreeDriversController {
    protected static Manager manager;

    public static void setManager(Manager manager) {
        ManagerReviewTripController.manager = manager;
    }

    @FXML
    private Label FreeDriverDetails;



    protected Driver driver;
    public void setDriver(Driver driver) {
        this.driver = driver;
    }
    protected static Driver SelectedDriver;

    public void setSelectedDriver(Driver selectedDriver) {
        SelectedDriver = selectedDriver;
    }

    public static Driver getSelectedDriver() {
        return SelectedDriver;
    }

    public void setData(String freeDriverdata) {
        FreeDriverDetails.setText(freeDriverdata);
    }

    public void Select(ActionEvent press) throws IOException {
        System.out.println(driver.displayDriverInfo());
        setSelectedDriver(driver);
    }
}
