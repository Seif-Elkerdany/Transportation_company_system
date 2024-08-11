package GUI;


import Backend.*;
import Backend.Trip;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class DriverController implements Initializable {

    protected static Driver driver;
    @FXML
    public Label UserLabel;


    @FXML
    protected Button driverProfileBTN;
    @FXML
    private Label DriverProfileCard;

    DriverTripsController driverTripsController;

    @FXML
    protected Label DriverProfileLabel;

    @FXML
    protected AnchorPane DriverProfileMenu;
    @FXML
    protected Label DriverProfileTest;
    @FXML
    protected Label DriverTripsTest;

    @FXML
    protected Button driverTripsBTN;

    @FXML
    protected AnchorPane DriverTripsMenu;
    @FXML
    protected GridPane TripsGrid;
    @FXML
    protected Parent root;
    @FXML
    protected Stage stage;
    @FXML
    protected Scene scene;
    @FXML
    protected AnchorPane MainDriverMenu;




    public static void setDriver(Driver tempDriver) {
        driver = tempDriver;
    }
    public DriverController() {

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int column = 0;
        int row = 1;

//        MainMenu mainMenu = new MainMenu();
        try {
            DriverTripsMenu.setVisible(true);
            DriverProfileMenu.setVisible(false);
            //Driver Trips Menu
            for (Trip trip : driver.assignedTrips) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("DriverTrips.fxml"));
                VBox TripBox = fxmlLoader.load();
                driverTripsController = fxmlLoader.getController();
                driverTripsController.setData(trip.toString());

                TripsGrid.add(TripBox, ++column, row);
                GridPane.setMargin(TripBox, new Insets(10));
            }
            //=================================
            DriverProfileCard.setText(driver.displayDriverInfo());
            UserLabel.setText(driver.getName());

        } catch (Exception e) {
        }
    }

    public static boolean usernameSpecifics(String username) {
        if (username.contains(",") || username.contains(" ") ) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean passwordSpecifics(String password) {
        if (password.contains(",") || password.contains(" ") || password.length() < 8) {
            return false;
        } else {
            return true;
        }
    }







    public void SwitchButtons(ActionEvent press) {

        if (press.getSource() == driverTripsBTN) {
            DriverTripsMenu.setVisible(true);
            DriverProfileMenu.setVisible(false);
        }else if (press.getSource() == driverProfileBTN) {
            DriverTripsMenu.setVisible(false);
            DriverProfileMenu.setVisible(true);
        }

    }
}
