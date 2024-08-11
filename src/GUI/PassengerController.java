package GUI;

import Backend.Passenger;
import Backend.Trip;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PassengerController implements Initializable {

    @FXML
    TextField CardNumberText;

    @FXML
    TextField cvvText;

    @FXML
    TextField ExpiryText;

    @FXML
    protected AnchorPane MainAnchor;

    protected static Passenger passenger;

    public static void setPassenger(Passenger tempPassenger) {
        passenger = tempPassenger;
    }

    PassengerOwnTripsController passengerOwnTripsController;
    PassengerExploreController passengerExploreController;

    //All FXML attributes --------------------
    @FXML
    private Button PassengerExploreBTN;

    @FXML
    protected VBox PassengerExploreOneWayBox;
    @FXML
    protected VBox PassengerExploreRoundBox;

    @FXML
    protected AnchorPane PassengerExploreMenu;

    @FXML
    private AnchorPane PassengerProfile;
    @FXML
    protected Label UserLabel;
    @FXML
    protected Label PassengerProfileCard;

    @FXML
    private Button PassengerProfileBTN;

    @FXML
    private Button PassengerTicketsBTN;

    @FXML
    private AnchorPane PassengerTicketsMenu;

    @FXML
    private GridPane PassengerTripsBox;

    @FXML
    private Label PassengerExploreTripDetails;

    @FXML
    protected BorderPane MainPane;
    @FXML
    protected Label PassengerRefundMessage = new Label("");
    @FXML
    protected Parent root;
    @FXML
    protected Stage stage;
    @FXML
    protected Scene scene;

    //========================


    @FXML
    protected AnchorPane testAnchor;


    public PassengerController() {

    }

    protected static AnchorPane wtfAnchor;

    public void setWtfAnchor(AnchorPane wtfAnchor1) {
        wtfAnchor = wtfAnchor1;
    }

    public AnchorPane getTestAnchor() {
        return testAnchor;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            testAnchor.setVisible(false);
            PassengerRefundMessage.setText(passenger.refund());

            PassengerExploreRoundBox.getChildren().clear();
            PassengerExploreOneWayBox.getChildren().clear();

            //Passenger Explore Trips
            for (int trip = 0; trip < Trip.allTrips.size(); trip++) {
                if (Trip.allTrips.get(trip).getTripType().equalsIgnoreCase("one-way") || Trip.allTrips.get(trip).getTripType().equalsIgnoreCase("one way")) {

                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("PassengerExploreTrips.fxml"));
                    VBox TripBox = fxmlLoader.load();
                    passengerExploreController = fxmlLoader.getController();
                    passengerExploreController.setOneWayData(Trip.allTrips.get(trip).toString() + "\nNumber of tickets: " + Trip.allTrips.get(trip).getNoOfTickets());
                    passengerExploreController.setOneWayTrip(Trip.allTrips.get(trip));
                    passengerExploreController.setMainTrip(Trip.allTrips.get(trip));

                    PassengerExploreOneWayBox.getChildren().add(TripBox);
                    PassengerExploreController.setPassenger(passenger);


                } else if (Trip.allTrips.get(trip).getTripType().equalsIgnoreCase("round-trip") || Trip.allTrips.get(trip).getTripType().equalsIgnoreCase("round trip")) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("PassengerExploreTrips.fxml"));
                    VBox TripBox = fxmlLoader.load();
                    passengerExploreController = fxmlLoader.getController();
                    passengerExploreController.setRoundData(Trip.allTrips.get(trip).toString());
                    passengerExploreController.setRoundTrip(Trip.allTrips.get(trip));
                    passengerExploreController.setMainTrip(Trip.allTrips.get(trip));

                    PassengerExploreRoundBox.getChildren().add(TripBox);
                    PassengerExploreController.setPassenger(passenger);

                }
            }

            UserLabel.setText(passenger.getName());
            PassengerProfileCard.setText(passenger.displayProfile());

            PassengerExploreMenu.setVisible(true);
            PassengerTicketsMenu.setVisible(false);
            PassengerProfile.setVisible(false);
            testAnchor.setVisible(false);


        } catch (Exception e) {

        }

    }


    public void SwitchButtons(ActionEvent press) throws IOException {

        if (press.getSource() == PassengerExploreBTN) {
            PassengerExploreRoundBox.getChildren().clear();
            PassengerExploreOneWayBox.getChildren().clear();




            //Passenger Explore Trips
            for (int trip = 0; trip < Trip.allTrips.size(); trip++) {
                if (Trip.allTrips.get(trip).getTripType().equalsIgnoreCase("one-way") || Trip.allTrips.get(trip).getTripType().equalsIgnoreCase("one way")) {

                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("PassengerExploreTrips.fxml"));
                    VBox TripBox = fxmlLoader.load();
                    passengerExploreController = fxmlLoader.getController();
                    passengerExploreController.setOneWayData(Trip.allTrips.get(trip).toString() + "\nNumber of tickets: " + Trip.allTrips.get(trip).getNoOfTickets());
                    passengerExploreController.setOneWayTrip(Trip.allTrips.get(trip));
                    passengerExploreController.setMainTrip(Trip.allTrips.get(trip));

                    PassengerExploreOneWayBox.getChildren().add(TripBox);
                    PassengerExploreController.setPassenger(passenger);


                } else if (Trip.allTrips.get(trip).getTripType().equalsIgnoreCase("round-trip") || Trip.allTrips.get(trip).getTripType().equalsIgnoreCase("round trip")) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("PassengerExploreTrips.fxml"));
                    VBox TripBox = fxmlLoader.load();
                    passengerExploreController = fxmlLoader.getController();
                    passengerExploreController.setRoundData(Trip.allTrips.get(trip).toString());
                    passengerExploreController.setRoundTrip(Trip.allTrips.get(trip));
                    passengerExploreController.setMainTrip(Trip.allTrips.get(trip));

                    PassengerExploreRoundBox.getChildren().add(TripBox);
                    PassengerExploreController.setPassenger(passenger);

                }
            }

            PassengerExploreMenu.setVisible(true);
            PassengerTicketsMenu.setVisible(false);
            PassengerProfile.setVisible(false);
            testAnchor.setVisible(false);



        }
        if (press.getSource() == PassengerTicketsBTN) {
            PassengerTripsBox.getChildren().clear();

            int column = 0;
            int row = 1;
            //Passenger 0wn Trips Menu
            for (int trip = 0; trip < passenger.passengerTrips.size(); trip++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("PassengerOwnTrips.fxml"));
                VBox ownTripBox = fxmlLoader.load();
                passengerOwnTripsController = fxmlLoader.getController();
                PassengerOwnTripsController.setPassenger(passenger);
                passengerOwnTripsController.setData(passenger.passengerTrips.get(trip).toString() + "\nNumber of tickets: " + passenger.passengerTrips.get(trip).getNoOfTickets() + "\nTotal Price: " + passenger.passengerTrips.get(trip).getTotalPrice());
                passengerOwnTripsController.setTrip(passenger.passengerTrips.get(trip));

                if (column == 2) {
                    column = 0;
                    ++row;
                }
                PassengerTripsBox.add(ownTripBox, ++column, row);
                GridPane.setMargin(ownTripBox, new Insets(10));
            }

            PassengerExploreMenu.setVisible(false);
            PassengerTicketsMenu.setVisible(true);
            PassengerProfile.setVisible(false);
            testAnchor.setVisible(false);



        }
        if (press.getSource() == PassengerProfileBTN) {

            PassengerExploreMenu.setVisible(false);
            PassengerTicketsMenu.setVisible(false);
            PassengerProfile.setVisible(true);
            testAnchor.setVisible(false);



        }

        if (press.getSource() == PassengerBuyButton) {
            if (!CardNumberText.getText().isEmpty() || !cvvText.getText().isEmpty() || !ExpiryText.getText().isEmpty()) {
                boolean isNumber = true;
                do  {

                    try {
                        Integer.parseInt(CardNumberText.getText());
                        Integer.parseInt(cvvText.getText());
                        Integer.parseInt(ExpiryText.getText());
                        isNumber = true;
                        break;

                    } catch (NumberFormatException e) {
                        BookLabelError.setText("Please only Enter numbers in the Field");
                        isNumber = false;
                        break;
                    }
                } while (!isNumber);
                if (isNumber) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("PassengerMenu.fxml"));
                    root = loader.load();
                    stage = (Stage) ((Node) press.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.show();
                    testAnchor.setVisible(true);
                } else {
                    BookLabelError.setText("Please only Enter numbers in the Field");
                }
            } else {
                BookLabelError.setText("Please fill all Fields");
            }
        }

    }


//    @FXML
//    protected Button PassengerBuyButton;


    protected Trip BoughtTrip;


    @FXML
    protected Button PassengerBookTripBTN;
    @FXML
    protected Label BookLabelError;
    @FXML
    protected Button PassengerBuyButton;


    public void Book(ActionEvent press) throws IOException {
        if(PassengerExploreController.getSelectedTrip() != null) {
            if(PassengerExploreController.getQuantity() > 0) {
                PassengerExploreMenu.setVisible(true);
                testAnchor.setVisible(true);
                passenger.bookTicket(PassengerExploreController.getSelectedTrip(), PassengerExploreController.getQuantity());
            }else {
                System.out.println("Check the quantity and then try again");
            }
        }else {
            System.out.println("Please select a valid trip first");
        }
    }
}














