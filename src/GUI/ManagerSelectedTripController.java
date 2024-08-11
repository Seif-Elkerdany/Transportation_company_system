package GUI;

import Backend.Driver;
import Backend.Manager;
import Backend.Trip;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class ManagerSelectedTripController {
    protected static Manager manager;

    public static void setManager(Manager manager) {
        ManagerReviewTripController.manager = manager;
    }

    @FXML
    private Label SelectedTripLabel;

    protected Trip trip;
    public void setTrip(Trip trip) {
        this.trip = trip;
    }
    protected static Trip SelectedTrip;

    public static void setSelectedTrip(Trip selectedTrip) {
        SelectedTrip = selectedTrip;
    }


    public Trip getTrip() {
        return trip;
    }

    public static Trip getSelectedTrip() {
        return SelectedTrip;
    }

    public void setData(String selectedTrip) {
        SelectedTripLabel.setText(selectedTrip);
    }

    public void Select(ActionEvent press) throws IOException {
        System.out.println(trip);
        setSelectedTrip(trip);
    }
}
