package GUI;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Backend.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

import static Backend.Employee.allDrivers;


public class ManagerController implements Initializable {

    //FXML Attributes -----------------------------

    @FXML
    private Button AddTripMENUBTN;

    @FXML
    private AnchorPane AddTripMenu;

    @FXML
    private Button AddVehicleMENUBTN;

    @FXML
    private AnchorPane AddVehicleMenu;

    @FXML
    private Label AddVehicleText;

    @FXML
    private AnchorPane AssignDriverMenu;

    @FXML
    private Button AssignDriverMENUBTN;

    @FXML
    private Label AssignDriverLabel;

    @FXML
    private TextField DayText;

    @FXML
    private TextField DestinationText;

    @FXML
    private GridPane ReviewTrips;

    @FXML
    private VBox FreeDriversBox;

    @FXML
    private TextField HourText;

    @FXML
    private TextField JourneyTypeText;

    @FXML
    private AnchorPane MainAnchor;

    @FXML
    private Label ManagerMainLabel;

    @FXML
    private Button ManagerProfileMENUBTN;

    @FXML
    protected Label ManagerProfileCard;

    @FXML
    private AnchorPane ManagerProfileMenu;

    @FXML
    private TextField MinuteText;

    @FXML
    private TextField MonthText;

    @FXML
    private TextField NumberofStopsText;

    @FXML
    private TextField PlateText;

    @FXML
    private GridPane RemoveTripGrid;

    @FXML
    private AnchorPane RemoveTripMenu;

    @FXML
    private Button ReportMENUBTN;

    @FXML
    private VBox ReportDriversBox;

    @FXML
    private AnchorPane ReportMenu;

    @FXML
    private VBox ReportTripsBox;

    @FXML
    private VBox ReportVehicleBox;

    @FXML
    private Button ReviewAllTripsMENUBTN;

    @FXML
    private AnchorPane ReviewTripsMenu;

    @FXML
    private TextField SourceText;

    @FXML
    private TextField TripTypeText;

    @FXML
    private VBox TripsBox;

    @FXML
    private Label UserLabel;

    @FXML
    private TextField VehicleCapacityText;

    @FXML
    private VBox VehicleLayout;

    @FXML
    private TextField VehiclePlateText;

    @FXML
    private TextField VehicleTypeText;

    @FXML
    private TextField YearText;

    @FXML
    private AnchorPane testAnchor;
    @FXML
    protected Label AddTripText;
    @FXML
    protected Button AssignDriverBTN;

    //===============================

    //review controller
    ManagerReviewTripController reviewTripController;

    //Assigning Driver to a Trip
    ManagerFreeDriversController freeDriversController;
    ManagerSelectedTripController selectedTripController;

    //Report
    ManagerReportTripsController reportTripsController;
    ManagerReportDriversController reportDriversController;
    ManagerReportVehicleController reportVehicleController;

    //==========================================


    protected static Manager manager;

    public static void setManager(Manager tempManager) {
        manager = tempManager;
    }

    VehicleCardController vehicleCardController;


    public ManagerController() {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ManagerProfileCard.setText(manager.displayManagerInfo());
        UserLabel.setText(manager.getName());
        ReviewTrips.getChildren().clear();


        try {

            int column = 0;
            int row = 1;
            //Manager Review Trips
            for (int trip = 0; trip < Trip.allTrips.size(); trip++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ManagerReviewTrip.fxml"));
                VBox TripBox = fxmlLoader.load();
                reviewTripController = fxmlLoader.getController();
                reviewTripController.setData(Trip.allTrips.get(trip).toString());
                reviewTripController.setTrip(Trip.allTrips.get(trip));
                ManagerReviewTripController.setManager(manager);
                if (column == 2) {
                    column = 0;
                    ++row;
                }

                ReviewTrips.add(TripBox, ++column, row);
                GridPane.setMargin(TripBox, new Insets(10));
            }
            ReviewTripsMenu.setVisible(true);
            AddVehicleMenu.setVisible(false);
            AddTripMenu.setVisible(false);
            AssignDriverMenu.setVisible(false);
            ReportMenu.setVisible(false);
            ManagerProfileMenu.setVisible(false);


        } catch (Exception e) {

        }
    }

    //Add vehicle Menu
    public void addVehicle(ActionEvent press) throws IOException {
        if (VehicleTypeText.getText().isEmpty() || VehicleCapacityText.getText().isEmpty() || PlateText.getText().isEmpty()) {
            try {
                AddVehicleText.setText(manager.addVehicle(VehicleTypeText.getText(), Integer.parseInt(VehicleCapacityText.getText()), PlateText.getText()));
            } catch (NumberFormatException e) {
                ManagerMainLabel.setText("please enter a number not text");
            }
        } else {
            AddVehicleText.setText("please fill all the fields before adding a trip");
        }
    }
    //end

    //Add Trip
    public void addTrip(ActionEvent press) throws IOException {
        if (!JourneyTypeText.getText().isEmpty() || !SourceText.getText().isEmpty() || !DestinationText.getText().isEmpty() || !YearText.getText().isEmpty() || !MonthText.getText().isEmpty() || !DayText.getText().isEmpty() || !HourText.getText().isEmpty() || !MinuteText.getText().isEmpty() || !NumberofStopsText.getText().isEmpty() || !VehiclePlateText.getText().isEmpty() || !TripTypeText.getText().isEmpty()) {
            try {
                AddTripText.setText(manager.managerAddTrip(JourneyTypeText.getText(), SourceText.getText(), DestinationText.getText(), Integer.parseInt(YearText.getText()), Integer.parseInt(MonthText.getText()), Integer.parseInt(DayText.getText()), Integer.parseInt(HourText.getText()), Integer.parseInt(MinuteText.getText()), Integer.parseInt(NumberofStopsText.getText()), VehiclePlateText.getText(), TripTypeText.getText()));
            } catch (NumberFormatException e) {
                ManagerMainLabel.setText("please enter a number not text");
            }
        } else {
            AddTripText.setText("please fill all the fields before adding a trip");
        }
    }
    //end

    //Assign Driver
    public void assignDriver(ActionEvent press) throws IOException {
        try {
            ManagerFreeDriversController.getSelectedDriver();
            ManagerSelectedTripController.getSelectedTrip();
            ManagerMainLabel.setText(manager.assignDriverToTrip(ManagerFreeDriversController.getSelectedDriver(), ManagerSelectedTripController.getSelectedTrip()));
        }catch (NullPointerException e) {
            ManagerMainLabel.setText("please select a driver/trip");
        }
    }


















    //Switching Scenes
    public void SwitchButtons(ActionEvent press) throws IOException{
        if (press.getSource() == ReviewAllTripsMENUBTN) {
            ReviewTrips.getChildren().clear();

            int column = 0;
            int row = 1;
            //Manager Review Trips
            for (int trip = 0; trip < Trip.allTrips.size(); trip++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ManagerReviewTrip.fxml"));
                VBox TripBox = fxmlLoader.load();
                reviewTripController = fxmlLoader.getController();
                reviewTripController.setData(Trip.allTrips.get(trip).toString());
                reviewTripController.setTrip(Trip.allTrips.get(trip));
                ManagerReviewTripController.setManager(manager);
                if (column == 2) {
                    column = 0;
                    ++row;
                }

                ReviewTrips.add(TripBox, ++column, row);
                GridPane.setMargin(TripBox, new Insets(10));
            }
            //Manager Review Trips end

            ReviewTripsMenu.setVisible(true);
            AddVehicleMenu.setVisible(false);
            AddTripMenu.setVisible(false);
            AssignDriverMenu.setVisible(false);
            ReportMenu.setVisible(false);
            ManagerProfileMenu.setVisible(false);
        }
        if (press.getSource() == AddVehicleMENUBTN) {
            ReviewTripsMenu.setVisible(false);
            AddVehicleMenu.setVisible(true);
            AddTripMenu.setVisible(false);
            AssignDriverMenu.setVisible(false);
            ReportMenu.setVisible(false);
            ManagerProfileMenu.setVisible(false);
        }
        if (press.getSource() == AddTripMENUBTN) {
            VehicleLayout.getChildren().clear();


            int size = Vehicle.allVehicles.size();

                //Vehicles ini
                for (int vehicle = 0; vehicle < size; vehicle++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("VehiclesCard.fxml"));
                    VBox VehicleBox = fxmlLoader.load();
                    vehicleCardController = fxmlLoader.getController();
                    vehicleCardController.setData(Vehicle.allVehicles.get(vehicle).displayInfo());
                    VehicleLayout.getChildren().add(VehicleBox);
                }
                // Vehicle end

            ReviewTripsMenu.setVisible(false);
            AddVehicleMenu.setVisible(false);
            AddTripMenu.setVisible(true);
            AssignDriverMenu.setVisible(false);
            ReportMenu.setVisible(false);
            ManagerProfileMenu.setVisible(false);
        }
        if (press.getSource() == AssignDriverMENUBTN) {

            FreeDriversBox.getChildren().clear();
            TripsBox.getChildren().clear();

            //Free Drivers Card Loading
            for(Driver driver : allDrivers){
                if(driver.assignedTrips.isEmpty() || driver.assignedTrips.size() != driver.MAXIMUM_TRIPS) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("ManagerFreeDrivers.fxml"));
                    VBox freeDriver = fxmlLoader.load();

                    freeDriversController = fxmlLoader.getController();
                    freeDriversController.setDriver(driver);
                    freeDriversController.setData(driver.displayDriverInfo());
                    FreeDriversBox.getChildren().add(freeDriver);
                }
            }
            //Free Driver End

            //Trips for free Driver Loading
            for (int trip = 0; trip < Trip.allTrips.size(); trip++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ManagerSelectedTrip.fxml"));
                VBox TripBox = fxmlLoader.load();

                selectedTripController = fxmlLoader.getController();
                selectedTripController.setTrip(Trip.allTrips.get(trip));
                selectedTripController.setData(Trip.allTrips.get(trip).toString());
                TripsBox.getChildren().add(TripBox);
            }



            ReviewTripsMenu.setVisible(false);
            AddVehicleMenu.setVisible(false);
            AddTripMenu.setVisible(false);
            AssignDriverMenu.setVisible(true);
            ReportMenu.setVisible(false);
            ManagerProfileMenu.setVisible(false);
        }
        if (press.getSource() == ReportMENUBTN) {
            ReportTripsBox.getChildren().clear();
            ReportDriversBox.getChildren().clear();
            ReportVehicleBox.getChildren().clear();

            //All Report FXML loading
            //Trips Report
            for (int trip = 0; trip < Trip.allTrips.size(); trip++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ManagerReportTrips.fxml"));
                VBox TripBox = fxmlLoader.load();
                reportTripsController = fxmlLoader.getController();
                reportTripsController.setData(Trip.allTrips.get(trip).toString());
                ReportTripsBox.getChildren().add(TripBox);
            }

            //Drivers Report
            for (int driver = 0; driver < allDrivers.size(); driver++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ManagerReportDrivers.fxml"));
                VBox DriverBox = fxmlLoader.load();
                reportDriversController = fxmlLoader.getController();
                reportDriversController.setData(allDrivers.get(driver).displayDriverInfo());
                ReportDriversBox.getChildren().add(DriverBox);
            }

            //Vehicles Report
            for (int vehicle = 0; vehicle < Vehicle.allVehicles.size(); vehicle++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ManagerReportVehicle.fxml"));
                VBox VehicleBox = fxmlLoader.load();
                reportVehicleController = fxmlLoader.getController();
                reportVehicleController.setData(Vehicle.allVehicles.get(vehicle).displayInfo());
                ReportVehicleBox.getChildren().add(VehicleBox);
            }


            ReviewTripsMenu.setVisible(false);
            AddVehicleMenu.setVisible(false);
            AddTripMenu.setVisible(false);
            AssignDriverMenu.setVisible(false);
            ReportMenu.setVisible(true);
            ManagerProfileMenu.setVisible(false);
        }
        if (press.getSource() == ManagerProfileMENUBTN) {
            ReviewTripsMenu.setVisible(false);
            AddVehicleMenu.setVisible(false);
            AddTripMenu.setVisible(false);
            AssignDriverMenu.setVisible(false);
            ReportMenu.setVisible(false);
            ManagerProfileMenu.setVisible(true);
        }
    }











    }



