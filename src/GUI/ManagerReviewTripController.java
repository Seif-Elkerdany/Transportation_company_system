package GUI;

import Backend.Manager;
import Backend.Trip;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagerReviewTripController {
    protected static Manager manager;

    public static void setManager(Manager manager) {
        ManagerReviewTripController.manager = manager;
    }

    @FXML
    private Button ManagerRemoveBTN;

    @FXML
    private Label ReviewTripDetailsLabel;
    protected Trip trip;

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public void setData(String managerReviewTrip) {
        ReviewTripDetailsLabel.setText(managerReviewTrip);
    }

    protected Parent root;
    protected Stage stage;
    protected Scene scene;

    public void RemoveTrip(ActionEvent press) throws IOException {
        manager.cancelTrip(this.trip);

        //-------------------------
        root = FXMLLoader.load(getClass().getResource("ManagerMenu.fxml"));
        stage = (Stage) ((Node) press.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        //============

    }
}
