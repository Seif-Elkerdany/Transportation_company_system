package GUI;

import Backend.Passenger;
import Backend.Trip;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PassengerExploreController extends PassengerController {

    @FXML
    private Label PassengerExploreTripDetails;
    @FXML
    public Button PassengerSelectTrip;
    protected Trip mainTrip;

    public void setMainTrip(Trip mainTrip) {
        this.mainTrip = mainTrip;
    }

    protected Trip roundTrip;
    protected Trip oneWayTrip;

    protected static Trip selectedTrip;

    public static void setSelectedTrip(Trip tempTrip) {
        selectedTrip = tempTrip;
    }

    @FXML
    protected Spinner<Integer> spinner;
    protected static int Quantity;
    protected static Passenger passenger;
    @FXML
    protected TextField TripQuantity;

    public static void setPassenger(Passenger p) {
        passenger = p;
    }

    //round trips SetData

    public void setRoundData(String PassengerTripDetails) {
        this.PassengerExploreTripDetails.setText(PassengerTripDetails);
    }

    public void setRoundTrip(Trip trip) {
        this.roundTrip = trip;
    }

    //round trips set data end



    //one way trips SetData

    public void setOneWayData(String PassengerTripDetails) {
        this.PassengerExploreTripDetails.setText(PassengerTripDetails);
    }

    public void setOneWayTrip(Trip trip) {
        this.oneWayTrip = trip;
    }

    //one way trips set data end



    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public static Trip getSelectedTrip() {
        return selectedTrip;
    }

    public static int getQuantity() {
        return Quantity;
    }

    public void preBook(ActionEvent press) throws IOException {
        if (!TripQuantity.getText().isEmpty()) {
            try {
                System.out.println(mainTrip);
                setSelectedTrip(mainTrip);
                setQuantity(Integer.parseInt(TripQuantity.getText()));
            }catch (NumberFormatException e) {
                setQuantity(0);
                System.out.println("Please enter a valid number not a text and try again");
            }
        } else {

        }
    }

//    public void updateLabel(String newString) {
//        EventLabelHandler event = new EventLabelHandler(newString);
//        fireEvent
//    }
}

//    public void Book(ActionEvent press) throws IOException{
//        PassengerController.passenger.bookTicket(trip, Integer.parseInt(TripQuantity.getText()));
//
//        //---------------------
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("PassengerMenu.fxml"));
//        root = loader.load();
//        stage = (Stage) ((Node) press.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setResizable(false);
//        stage.setScene(scene);
//        stage.show();
//        //============


