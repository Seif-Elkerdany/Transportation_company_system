package Backend;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Vehicle {
    // Attributes --> vehicleType, capacity, licensePlate, allVehicles

    // vehicle type (bus\limousine\mini-bus)
    private String vehicleType;

    // number of vehicle seats
    private int capacity;

    // vehicle license plate
    private String licensePlate;

    // Contains all vehicles to store it in the Database.
    public static ArrayList<Vehicle> allVehicles = new ArrayList<Vehicle>();

    //__________________________________________________________________________________________________________________
    // Methods --> addVehicle, displayInfo

    public Vehicle(String vehicleType, int capacity, String licensePlate) {
        this.vehicleType = vehicleType;
        this.capacity = capacity;
        this.licensePlate = licensePlate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    // display vehicle info
    public String displayInfo() {
        return "Type of the vehicle: " + getVehicleType() + "\nLicense Plate: " + getLicensePlate() + "\nIt's Capacity:" + getCapacity();
    }

    public String preprocessingData() {
        return this.vehicleType + "," + this.capacity + "," + this.licensePlate + ",";
    }

    // DataBase
    //__________________________________________________________________________________________________________________
    // Methods --> overWriteVehicleDataBase, loadVehicleData

    // Method to write new Vehicle in the database
    public void overWriteVehicleDataBase(){
        try {
            FileWriter myWriter = new FileWriter("Vehicle_Database.txt");
            String data = "";
            for (Vehicle v : allVehicles) {
                data += v.preprocessingData();
                data += System.lineSeparator();
            }
            myWriter.write(data);
            myWriter.close();
        }catch (IOException e) {
            System.out.println("Error accessing database");
            e.printStackTrace();
        }
    }

    public static ArrayList<Vehicle> loadVehicleData(){
        try {
            File file = new File("Vehicle_Database.txt");

            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String[] words = data.split(",");
                Vehicle vehicle = new Vehicle(words[0], Integer.parseInt(words[1]), words[2]);
                allVehicles.add(vehicle);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Couldn't load Vehicles Data!");
            e.printStackTrace();
            System.out.println(e);
        }
        return allVehicles;
    }
}
