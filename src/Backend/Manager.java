package Backend;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager extends Employee{

    //__________________________________________________________________________________________________________________
    // Methods --> managerAddTrip, managerAddVehicle, displayVehciles, cancelTrip, report, displayManagerInfo

    public Manager(String userName, String phoneNumber, String name, String email, String password) {
        super(userName, phoneNumber, name, email, password);
    }

    public Manager(User user){
        super(user.getUserName(), user.getPhoneNumber(), user.getName(), user.getEmail(), user.getPassword());
    }

    public String managerAddTrip(String journeyType, String source, String destination, int year, int month, int day, int hour, int min, int noOfStops, String licensePlate, String tripType) {
        //remapping the date values for the date class
        month--;
        year -= 1900;
        // Create Trip and add it to trips arraylist.
        Trip newTrip = new Trip(journeyType, source, destination, year, month, day, hour, min, noOfStops, licensePlate, tripType);
        Trip.allTrips.add(newTrip);
        // Set the trip price automatic
        newTrip.setPrice();
        // add it to the database
        newTrip.addToDataBase();
        return "Trip has been Added";
    }

    public String addVehicle(String vehicleType, int capacity, String licensePlate) {
        // Create new vehicle obj and add it to the Vehicle arraylist.
        Vehicle newV = new Vehicle(vehicleType, capacity, licensePlate);
        if(checkVehicle(newV)){
            return "This Vehicle already exists";
        }
        Vehicle.allVehicles.add(newV);
        // add it to the database
        newV.overWriteVehicleDataBase();
        return "Vehicle has been added";
    }

    public boolean checkVehicle(Vehicle vehicle) {
        //checks if the vehicle is in the database I use the license plate to compare the vehicles because it is unique for each car
        for(Vehicle v : Vehicle.allVehicles){
            if(v.getLicensePlate().equals(vehicle.getLicensePlate())){
                return true;
            }
        }
        return false;
    }

    public String displayVehciles() {
        // Display all Vehicles we have in the company.
        int size = Vehicle.allVehicles.size();
        String line = "";
        for(int i = 0; i < size;i++){
            line += Vehicle.allVehicles.get(i).displayInfo() + "\n";
        }
        return line;
    }

//    public String displayFreeDrivers(){
//
//        for(Driver driver : Driver.allDrivers){
//            if(driver.assignedTrips.isEmpty() || driver.assignedTrips.size() != driver.MAXIMUM_TRIPS){
//                return driver.displayDriverInfo();
//            }
//        }
//        return "No free drivers available";
//    }

    public String assignDriverToTrip(Driver d, Trip t){
        // First we check weather the driver can have more trips or not
        if(d.assignedTrips.size() >= d.MAXIMUM_TRIPS){
            return "This driver has reached the maximum number of trips that can be assigned to him";
        }else {
            // then we check if the trip has been already assigned to any other driver
            if(isTripAssigned(t.ID)){
                return "This trip has already been assigned!";
            }else {
                // We also check if this driver has been assigned to this trip
                if (isDriverAssigned(d, t.ID)){
                    return "This driver has already assigned to this trip";
                }else {
                    // check if the driver can drive the trip vehicle
                    if(t.vehicle.getVehicleType().equals(d.getDriverVehicle())){
                        d.assignedTrips.add(t);
                        d.overWriteDriverDataBase();
                        return "Driver has been assigned";
                    }
                    else {
                        return "This driver can not drive this type of Vehicles!";
                    }
                }
            }
        }
    }


    public boolean isTripAssigned(String tripID){
        // Method to check weather the trip is assigned to a driver or not
        for(Driver d : allDrivers){
            // iterate through the assigned trips for each driver
            for(int i = 0; i < d.assignedTrips.size(); i++){
                if(tripID.equals(d.assignedTrips.get(i).ID)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isDriverAssigned(Driver d, String tripID){
        // Method to check weather the driver is assigned to this trip or not
        for(Trip t : d.assignedTrips){
            if(t.ID.equals(tripID)){
                return true;
            }
        }
        return false;
    }

    public String cancelTrip(Trip trip) {
        // Method to remove trips from the trip arraylist and database.
        Trip.allTrips.remove(trip);
        overWriteTripsDataBase();
        return "Trip has been Removed";
    }

    public String GenerateReport() {
        // Method to display info about all databases

        String line = "";
        line += "All Drivers.\n";

        for(int i = 0; i < allDrivers.size(); i++){
            line += allDrivers.get(i).displayDriverInfo() + "\n";
        }

        line += "All Trips.\n";
        for(int i = 0; i < Trip.allTrips.size(); i++){
            line += Trip.allTrips.get(i) + "\n";
        }

        line += "All vehicles.\n";
        for(int i = 0; i < Vehicle.allVehicles.size(); i++){
            line += Vehicle.allVehicles.get(i).displayInfo() + "\n";
        }

        return line;
    }

    public String displayDrivers() {
        String line = "";
        for(int i = 0; i < allDrivers.size(); i++){
            line += "Driver #" + ( i + 1) + "\n";
            line += allDrivers.get(i).displayDriverInfo() + "\n";
        }
        return line;
    }

    public String displayManagerInfo() {
        // Display manger info.
        return "Manager's name: " + this.getName() + "\nE-mail: " +this.getEmail()+ "\nPhone Number: " +this.getPhoneNumber();
    }

    // DataBase
    //__________________________________________________________________________________________________________________
    // Methods --> overWriteTripsDataBase, loadManagersData, LoginManagerData

    public void overWriteTripsDataBase(){
        try {
            FileWriter myWriter = new FileWriter("Trip_Database.txt");
            String data = "";
            for (Trip t : Trip.allTrips) {
                data += t.preprocessingData_overWrite();
                data += System.lineSeparator();
            }
            myWriter.write(data);
            myWriter.close();
        }catch (IOException e) {
            System.out.println("Error accessing database");
            e.printStackTrace();
        }
    }

    public void overWriteManagerDataBase(){
        try {
            // open the file
            FileWriter myWriter = new FileWriter("Manager_Database.txt");
            // the line we will write later.
            String data = "";
            // Enhanced for loop to loop on all managers
            for (Manager m : allManagers) {
                // add the manager data in the line
                data += m.saveData();
                // make new line
                data += System.lineSeparator();
            }

            // write to file
            myWriter.write(data);
            myWriter.close();
        }catch (IOException e) {
            System.out.println("Error accessing database");
            e.printStackTrace();
        }
    }

    public static ArrayList<Manager> loadManagersData(){
        try {
            File file = new File("Manager_Database.txt");

            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String[] words = data.split(",");
                Manager manager = new Manager(words[0], words[1], words[2], words[3], words[4]);
                manager.setUserID(words[5]);
                allManagers.add(manager);
            }
            reader.close();
        } catch (Exception e) {
                System.out.println("Couldn't load Managers Data!");
            e.printStackTrace();
            System.out.println(e);
        }
        return allManagers;
    }

    // Method to get the Manager data and save it in the memory
    public static Manager LoginManagerData(String username){
        Manager manager = null;
        for(int i = 0; i < Employee.allManagers.size(); i++){
            if(username.equals(Employee.allManagers.get(i).getUserName())){
                manager = Employee.allManagers.get(i);
            }
        }
        return manager;
    }
}
