package GUI;

import GUI.DriverTripsController;
import GUI.PassengerController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import Backend.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class MainMenu extends Application implements Initializable {
    static Stack<String> stack = new Stack<>();


    @FXML
    protected Parent root;
    @FXML
    protected Stage stage;
    @FXML
    protected Scene scene;

    protected AnchorPane MainPane;


    @FXML
    private Button MainLogin;

    @FXML
    private Button MainRegister;

    @FXML
    public TextField LoginUsername;
    @FXML
    public PasswordField LoginPassword;
    public TextField fullNameText;
    public TextField emailText;
    public TextField phoneText;
    public TextField RegisterUsername;
    public PasswordField RegisterPassword;
    public PasswordField confirmPassword;
    @FXML
    private Button IntroMenuEmployeeBTN;

    @FXML
    private Button IntroMenuPassengerBTN;
    @FXML
    private Button IntroMenuDriverBTN;

    @FXML
    private Button IntroMenuManagerBTN;

    @FXML
    public Label LoginLabel;
    @FXML
    private TextField DriverVehicleText;
    @FXML
    public Label Error = new Label();
    @FXML
    private PasswordField RegisterDriverPassword;

    @FXML
    private TextField RegisterDriverUsername;

    @FXML
    private PasswordField confirmDriverPassword;

    @FXML
    private TextField emailDriverText;

    @FXML
    private TextField fullNameDriverText;

    @FXML
    private TextField phoneDriverText;



    //===================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void start(Stage introMenu) throws IOException {


        // load database
        Vehicle.loadVehicleData();
        Trip.loadTripsData();
        Manager.loadManagersData();
        Driver.loadDriversData();
        Passenger.loadPassengersData();

        this.stage = introMenu;
        this.stage.setTitle("Transportation Company");
        introMenu.setResizable(false);

        IntroMenu();

    }

    public void IntroMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("IntroMenuOld.fxml"));
        AnchorPane introMenu = (AnchorPane) loader.load();

        scene = new Scene(introMenu);
        stage.setScene(scene);
        stage.show();

    }
//
//    public static TextField getLoginUsername() {
//        return LoginUsername;
//    }
//
//    public PasswordField getLoginPassword() {
//        return LoginPassword;
//    }
//
//    public static void setLoginUsername(TextField loginUsername) {
//        LoginUsername = loginUsername;
//    }
//
//    public static void setLoginPassword(PasswordField loginPassword) {
//        LoginPassword = loginPassword;
//    }


    public void SelectionMenu(ActionEvent press) throws IOException {
        if (press.getSource() == MainLogin) {
            stack.push("login");
            System.out.println(stack.peek());
        }
        if (press.getSource() == MainRegister) {
            stack.push("register");
            System.out.println(stack.peek());
        }
        root = FXMLLoader.load(getClass().getResource("SelectionMenu.fxml"));
        stage = (Stage) ((Node) press.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void EmployeeMenu(ActionEvent press) throws IOException {
        if (press.getSource() == IntroMenuEmployeeBTN) {
            stack.push("employee");
            System.out.println(stack.peek());
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("EmployeeMenu.fxml"));
        MainPane = (AnchorPane) loader.load();
        stage = (Stage) ((Node) press.getSource()).getScene().getWindow();
        scene = new Scene(MainPane);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();


    }


    public void MainInputScene(ActionEvent press) throws IOException {
        if (press.getSource() == IntroMenuPassengerBTN) {
            stack.push("passenger");
            System.out.println(stack.peek());
        }
        if (press.getSource() == IntroMenuDriverBTN) {
            stack.push("driver");
            System.out.println(stack.peek());
        }
        if (press.getSource() == IntroMenuManagerBTN) {
            stack.push("manager");
            System.out.println(stack.peek());
        }


        if (stack.firstElement() == "register") {
            if (stack.peek() == "passenger" || stack.peek() == "manager") {
                root = FXMLLoader.load(getClass().getResource("RegisterMenu.fxml"));
                stage = (Stage) ((Node) press.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            } else if (stack.peek() == "driver") {
                root = FXMLLoader.load(getClass().getResource("RegisterMenuDriver.fxml"));
                stage = (Stage) ((Node) press.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            }
        }
        if (stack.firstElement() == "login") {
            root = FXMLLoader.load(getClass().getResource("LoginMenu.fxml"));
            stage = (Stage) ((Node) press.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        }


    }

    public void Return(ActionEvent press) throws IOException {
        if (stack.peek() == "passenger" || stack.peek() == "employee") {
            root = FXMLLoader.load(getClass().getResource("SelectionMenu.fxml"));
            stage = (Stage) ((Node) press.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }
        if (stack.peek() == "driver" || stack.peek() == "manager") {
            root = FXMLLoader.load(getClass().getResource("EmployeeMenu.fxml"));
            stage = (Stage) ((Node) press.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void GoesNextRegister(ActionEvent press) throws IOException {
        User user;
        if (stack.peek() == "passenger") {
            if (DriverController.usernameSpecifics(RegisterUsername.getText())) {
                if (DriverController.passwordSpecifics(RegisterPassword.getText())) {
                    if (!RegisterUsername.getText().isEmpty() || !phoneText.getText().isEmpty() || !fullNameText.getText().isEmpty() || !emailText.getText().isEmpty() || !RegisterPassword.getText().isEmpty()) {
                        if (confirmPassword.getText().equals(RegisterPassword.getText())) {
                            try {
                                Integer.parseInt(phoneText.getText());
                                if(emailText.getText().contains("@gmail.com") || emailText.getText().contains("@iCloud.com") || emailText.getText().contains("@hotmail.com")){
                                    user = new User(RegisterUsername.getText(), phoneText.getText(), fullNameText.getText(), emailText.getText(), RegisterPassword.getText());
                                    user.Register("Passenger", user);
                                    stack.removeAll(stack);
                                    root = FXMLLoader.load(getClass().getResource("IntroMenuOld.fxml"));
                                    stage = (Stage) ((Node) press.getSource()).getScene().getWindow();
                                    scene = new Scene(root);
                                    stage.setResizable(false);
                                    stage.setScene(scene);
                                    stage.show();
                                }else {
                                    Error.setText("Write a valid email address");
                                }
                            }catch (NumberFormatException e){
                                Error.setText("Please write numbers in the phone number field");
                            }
                        } else {
                            Error.setText("Password Doesn't Match");
                        }
                    } else {
                        Error.setText("Please Fill all the Fields");
                    }
                } else {
                    Error.setText("Password cannot contain Spaces or Comma \",\"");
                }
            } else {
                Error.setText("Username cannot contain Spaces or Comma \",\"");

            }
        }else if (stack.peek() == "manager") {
            if (DriverController.usernameSpecifics(RegisterUsername.getText())) {
                if (DriverController.passwordSpecifics(RegisterPassword.getText())) {
                    if (!RegisterUsername.getText().isEmpty() || !phoneText.getText().isEmpty() || !fullNameText.getText().isEmpty() || !emailText.getText().isEmpty() || !RegisterPassword.getText().isEmpty()) {
                        if (confirmPassword.getText().equals(RegisterPassword.getText())) {
                            try{
                                Integer.parseInt(phoneText.getText());
                                if(emailText.getText().contains("@gmail.com") || emailText.getText().contains("@iCloud.com") || emailText.getText().contains("@hotmail.com")){
                                    user = new User(RegisterUsername.getText(), phoneText.getText(), fullNameText.getText(), emailText.getText(), RegisterPassword.getText());
                                    user.Register("Manager", user);
                                    stack.removeAll(stack);
                                    root = FXMLLoader.load(getClass().getResource("IntroMenuOld.fxml"));
                                    stage = (Stage) ((Node) press.getSource()).getScene().getWindow();
                                    scene = new Scene(root);
                                    stage.setResizable(false);
                                    stage.setScene(scene);
                                    stage.show();
                                }else{
                                    Error.setText("Write a valid email address");
                                }
                            }catch (NumberFormatException e){
                                Error.setText("Please write numbers in the phone number field");
                            }
                        } else {
                            Error.setText("Password Doesn't Match");
                        }
                    } else {
                        Error.setText("Please Fill all the Fields");
                    }
                } else {
                    Error.setText("Password cannot contain Spaces or Comma \",\"");
                }
            } else {
                Error.setText("Username cannot contain Spaces or Comma \",\"");

            }
        } else if (stack.peek() == "driver") {
            if (DriverController.usernameSpecifics(RegisterDriverUsername.getText())) {
                if (DriverController.passwordSpecifics(RegisterDriverPassword.getText())) {
                    if (!RegisterDriverUsername.getText().isEmpty() || !phoneDriverText.getText().isEmpty() || !fullNameDriverText.getText().isEmpty() || !emailDriverText.getText().isEmpty() || !RegisterDriverPassword.getText().isEmpty()) {
                        if (confirmDriverPassword.getText().equals(RegisterDriverPassword.getText())) {
                            try {
                                System.out.println(stack.peek());
                                Integer.parseInt(phoneDriverText.getText());
                                if(emailDriverText.getText().contains("@gmail.com") || emailDriverText.getText().contains("@iCloud.com") || emailDriverText.getText().contains("@hotmail.com")){
                                    Driver driver = new Driver(RegisterDriverUsername.getText(), phoneDriverText.getText(), fullNameDriverText.getText(), emailDriverText.getText(), RegisterDriverPassword.getText());
                                    driver.Register("Driver", DriverVehicleText.getText(), driver);
                                    stack.removeAll(stack);
                                    root = FXMLLoader.load(getClass().getResource("IntroMenuOld.fxml"));
                                    stage = (Stage) ((Node) press.getSource()).getScene().getWindow();
                                    scene = new Scene(root);
                                    stage.setResizable(false);
                                    stage.setScene(scene);
                                    stage.show();
                                }else {
                                    Error.setText("Write a valid email address");
                                }
                            }catch (NumberFormatException e) {
                                Error.setText("Please write numbers in the phone number field");
                            }
                        } else {
                            Error.setText("Password Doesn't Match");
                        }
                    } else {
                        Error.setText("Please Fill all the Fields");
                    }
                } else {
                    Error.setText("Password cannot contain Spaces or Comma \",\"");
                }
            } else {
                Error.setText("Username cannot contain Spaces or Comma \",\"");

            }
        }
    }


    public void GoesNextLogin(ActionEvent press) throws IOException {
        if (stack.peek() == "passenger") {
            boolean CheckInput = Passenger.Login(LoginUsername.getText(), LoginPassword.getText().toString(), "Passenger");
            if (CheckInput) { // if true it loads next passenger menu
                Passenger passenger = Passenger.LoginPassengerData(LoginUsername.getText());
                PassengerController.setPassenger(passenger);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("PassengerMenu.fxml"));
                root = loader.load();
                stage = (Stage) ((Node) press.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            } else {
                LoginLabel.setText("Incorrect Username/Password");
            }
        }
        if (stack.peek() == "manager") {
            boolean CheckInput = Manager.Login(LoginUsername.getText().toString(), LoginPassword.getText().toString(), "Manager");

            if (CheckInput) { // if true it loads next manager menu
                Manager manager = Manager.LoginManagerData(LoginUsername.getText());
                ManagerController.setManager(manager);

                root = FXMLLoader.load(getClass().getResource("ManagerMenu.fxml"));
                stage = (Stage) ((Node) press.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            } else {
                LoginLabel.setText("Incorrect Username/Password");
            }
        }
        if (stack.peek() == "driver") {
            boolean CheckInput = Driver.Login(LoginUsername.getText(), LoginPassword.getText().toString(), "Driver");
            if (CheckInput) { // if true it loads next driver menu

                Driver driver = Driver.LoginDriversData(LoginUsername.getText());
                DriverController.setDriver(driver);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DriverMenu.fxml"));
                root = loader.load();
                stage = (Stage) ((Node) press.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            } else {
                LoginLabel.setText("Incorrect Username/Password");
            }
        }
    }


















    public void Exit(ActionEvent press) throws IOException {

        root = FXMLLoader.load(getClass().getResource("IntroMenuOld.fxml"));
        stage = (Stage) ((Node) press.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        stack.removeAll(stack);

    }

    public static void main(String[] args) {
        launch(args);
    }


}






