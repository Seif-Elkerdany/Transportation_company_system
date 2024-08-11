package GUI;

import Backend.Passenger;
import Backend.Trip;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.imageio.stream.ImageOutputStreamImpl;
import java.io.IOException;

public class PassengerOwnTripsController extends PassengerController{
    protected static Passenger passenger;
    protected Trip trip;

    @FXML
    private Label PassengerTripDetails;
    @FXML
    private Button PassengerCancelBTN;
    public static void setPassenger(Passenger p) {
        passenger = p;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
    public void Cancel(ActionEvent press) throws IOException {

        System.out.println(trip);
        System.out.println(passenger.cancelTrip(trip));
        //---------------------
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PassengerMenu.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) press.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        //============
    }

    public void setData(String PassengerTripDetails) {
        this.PassengerTripDetails.setText(PassengerTripDetails);
    }
}
