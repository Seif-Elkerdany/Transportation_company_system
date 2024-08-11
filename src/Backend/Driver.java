package Backend;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver extends Employee {
    // Attributes --> assignedTrips, driverVehicle, MAXIMUM_TRIPS

    // ArrayList that will save every trip assigned to each driver.
    public ArrayList<Trip> assignedTrips = new ArrayList<>();

    // Type of the vehicle the driver drive.
    private String driverVehicle;

    // Maximum number of trips the driver can driver
    public final int MAXIMUM_TRIPS = 2;

    //__________________________________________________________________________________________________________________
    // Methods --> reviewAssignedTrips, displayDriverInfo

    public Driver(String userName, String phoneNumber, String name, String email, String password){
        super(userName, phoneNumber, name, email, password);
    }

    public void setDriverVehicle(String driverVehicle) {
        this.driverVehicle = driverVehicle;
    }

    public String getDriverVehicle() {
        return driverVehicle;
    }

    public String reviewAssignedTrips() {
        // Print each assigned trip for the driver.
        int size = assignedTrips.size();
        String line = "";

        for (int i = 0; i < size; i++) {
            line += "\n\t\tTrip number " + (i + 1) + " \n" + this.assignedTrips.get(i) + "\n";
        }
        return line;
    }

    // display info about the driver
    public String displayDriverInfo(){
        return "Name: " + this.getName() + "\nID: " + this.UserID + "\nPhone Number" + this.getPhoneNumber() + "\nE-mail:" + this.getEmail() + "\nUserName:" + this.getUserName()
                + "\nVehicle you can drive:" + this.getDriverVehicle();
    }

    //DataBase
    //__________________________________________________________________________________________________________________
    // methods --> loadDriversData, overWriteDriverDataBase, overWriteDriverDataBase

    public static ArrayList<Driver> loadDriversData(){
        try {
            File file = new File("Driver_Database.txt");

            Scanner reader = new Scanner(file);
            // create drivers obj and put them in the allDrivers arraylist
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String[] words = data.split(",");
                Driver driver = new Driver(words[0], words[1], words[2], words[3], words[4]);
                // Take the driver Vehicle that he can drive and put it in the class
                driver.setDriverVehicle(words[6]);
                driver.setUserID(words[5]);
                allDrivers.add(driver);
                // we have set the limit to trips assigned to the driver equals to two
                if(words.length == 8){
                    String first_id = words[7];
                    boolean flag = true;
                    for(int i = 0; i < Trip.allTrips.size();i++){
                        if(first_id.equals(Trip.allTrips.get(i).ID)){
                            driver.assignedTrips.add(Trip.allTrips.get(i));
                            flag = false;
                        }
                    }
                    if (flag){
                        driver.overWriteDriverDataBase();
                    }
                } else if (words.length == 9) {
                    String first_id = words[7];
                    String second_id = words[8];
                    boolean flag1 = true;
                    boolean flag2 = true;
                    for(int i = 0; i < Trip.allTrips.size();i++){
                        if(first_id.equals(Trip.allTrips.get(i).ID)){
                            driver.assignedTrips.add(Trip.allTrips.get(i));
                            flag1 = false;
                        }

                        if(second_id.equals(Trip.allTrips.get(i).ID)){
                            driver.assignedTrips.add(Trip.allTrips.get(i));
                            flag2 = false;
                        }
                    }
                    if(flag1 || flag2){
                        driver.overWriteDriverDataBase();
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Couldn't load Driver Data!");
            e.printStackTrace();
            System.out.println(e);
        }
        return allDrivers;
    }

    public void overWriteDriverDataBase(){
        // methode to make any change to the database
        try {
            FileWriter myWriter = new FileWriter("Driver_Database.txt");
            String data = "";
            // Save the driver and his assigned trips
            for (Driver d : Employee.allDrivers) {
                data += d.saveData(d) + ",";
                for(Trip t : d.assignedTrips){
                    data += t.ID + ",";
                }
                data += System.lineSeparator();
            }
            myWriter.write(data);
            myWriter.close();
        }catch (IOException e) {
            System.out.println("Error accessing database");
            e.printStackTrace();
        }
    }

    // Method to get the driver data and save it in the memory
    public static Driver LoginDriversData(String username){
        Driver driver = null;
        for(int i = 0; i < Employee.allDrivers.size(); i++){
            if(username.equals(Employee.allDrivers.get(i).getUserName())){
                driver = Employee.allDrivers.get(i);
            }
        }
        return driver;
    }
}
