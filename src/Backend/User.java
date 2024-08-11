package Backend;
import GUI.MainMenu;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.UUID;

public class User{
    // Main class for all Users for both employees and Passengers

    // Attributes --> userName, phoneNumber, name, email, password, uniqueID

    // User's name saved as a String
    private String name;

    // User's Email saved as a String
    private String email;

    // User's Password Saved as a String
    private String password;

    // User's Phone Number Saved as String
    private String phoneNumber;

    // User's unique username
    private String userName;

    // Create unique ID.
    private UUID uniqueID;

    // Use User's unique ID
    protected String UserID;

    //__________________________________________________________________________________________________________________
    // Methods --> preprocessingData, Register, Login, checkUserName

    // New user constructor.
    public User(String userName, String phoneNumber, String name, String email, String password) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public void createUserID(){
        uniqueID = UUID.randomUUID();

        UserID = uniqueID.toString();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String saveData() {
        // Method to change the data shape and order it into comma separated values to save it in the database.
        return this.userName + "," + this.phoneNumber + "," + this.name + "," + this.email + "," + this.password + "," + this.UserID + ",";
    }

    public String saveData(Driver d) {
        // Overwriting saveData it takes driver obj to save the Vehicle that he can drive.
        return this.userName + "," + this.phoneNumber + "," + this.name + "," + this.email + "," + this.password + "," + this.UserID + "," + d.getDriverVehicle();
    }

    public String getPassword() {
        return password;
    }

    // DataBase
    //_________________________________________________________________________________________________________________
    // method --> checkUserName, Register, Login

    public boolean checkUserName(String Database) {
        /*
         * Method to check if the username is in the database or not to make a registration.
         * We used StringTokenizer obj in this method.
         *
         * parameters:
         *            Database (String): name of the csv file which contains all the data.
         *
         * returns: boolean true if the username is available and false if not.
         */

        Database += "_Database.txt";
        // Make new obj from buffer reader that reads the database.
        try (BufferedReader reader = new BufferedReader(new FileReader(Database))) {
            // String for read line.
            String line;
            // Keep reading until we reach the end of the file.
            while ((line = reader.readLine()) != null) {
                // StringTokenizer will split each line to values separated by commas (,).
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                // Here we are checking if tokenizer got at least one token more after the current position.
                if (tokenizer.hasMoreTokens()) {
                    // We just read the first token that is the username in the database.
                    String firstColumn = tokenizer.nextToken();
                    // Now we check if the username exists or not.
                    if(firstColumn.equals(this.userName)){
                        System.out.println("Username already exists.");
                        GUI.MainMenu menu = new MainMenu();
                        menu.Error.setText("Username already exists");

                        return false;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return true;
    }

    public void Register(String Type, User user) {
        /*
        Method to register a new user and its data in the database.

        parameters:
                   path (String): name of the csv file which contains all the data.
        */

        if(Type.equals("Passenger")){
            if (checkUserName(Type)){
                Passenger p = new Passenger(user);
                p.createUserID();
                Passenger.allPassengers.add(p);
                p.overWritePassengerDataBase();
            }
        }
        else if (Type.equals("Manager")){
            if (checkUserName(Type)){
                Manager m = new Manager(user);
                m.createUserID();
                Manager.allManagers.add(m);
                m.overWriteManagerDataBase();
            }
        }
    }

    public void Register(String Type, String Type_of_V, Driver d){
        if(checkUserName(Type)){
            d.createUserID();
            d.setDriverVehicle(Type_of_V);
            Driver.allDrivers.add(d);
            d.overWriteDriverDataBase();
        }
    }

    public static boolean Login(String Username, String password, String dataBase) {
        /*
         * Log in method that checks if the username and password matches anything in the database or not.
         * We used Scanner and File reader to read the csv file instead of StringTokenizer.
         *
         * parameters:
         *            Username (String): username of the user.
         *            password (String): password of the user.
         *            database (String): name of the csv file which contains all the data.
         *
         * returns: boolean true if the user enter his username and password right and false if not.
         */

        ArrayList<String> userdata = new ArrayList<>();
        userdata.add(Username);
        dataBase += "_Database.txt";

        try {
            // obj of scanner that read the data and separate each value got commas in it.
            Scanner scanner = new Scanner(new FileReader(dataBase));
            scanner.useDelimiter(","); // Set comma as delimiter

            // Checks each data in each line.
            while (scanner.hasNext()) {
                // Iterate through the dataset.
                String value = scanner.next();

                // If we have found the user then we take all his data and add it to the list.
                if (value.contains(Username)) {
                    try {
                        // add user's data one by one, stops at the password
                        for (int i = 0; i < 4; i++) {
                            String data = scanner.next();
                            // If we have reached the password add it to the userdata arraylist.
                            if(i == 3){
                                userdata.add(data);
                            }
                        }
                        // checks if the username and the password matches the username and the password in the database.
                        if (userdata.get(0).equals(Username) && userdata.get(1).equals(password)) {
                            return true;
                        }
                    } catch (Exception e) {
                        System.out.println("Error could not find the user!");
                        e.printStackTrace();
                        break;
                    }
                }
            }
            scanner.close();
            return false;
        } catch (Exception e) {
            System.out.println("error couldn't access the database.");
            e.printStackTrace();
            return false;
        }
    }
}