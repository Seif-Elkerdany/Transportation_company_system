package Backend;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

public class Trip {

    // Attributes --> journeyType, tripType, price, source, destination, noOfStops, allTrips, vehicle, availableSeats, date, MAX_STOPS, uniqueID, ID

    // chooses between internal or external
    private String journeyType;

    // choose between (one-way) or (round-trip)
    private String tripType;

    // Price of the ticket
    private double price = 0;

    // Start point
    private String source;

    // End point
    private String destination;

    // Number of stops of the trip
    private int noOfStops;

    // Arraylist has got all the trips available in it.
    public static ArrayList<Trip> allTrips = new ArrayList<Trip>();

    // Type of the vehicle that will carry out the trip.
    protected Vehicle vehicle;

    // Number of available seats in the trip
    private int availableSeats;

    // Date of the trip
    private Date date;

    // Maximum number of stops in a single trip.
    private final int MAX_STOPS = 3;

    // Create unique ID for each new trip.
    protected UUID uniqueID;

    // Save the ID
    protected String ID;

    // Number of tickets
    protected int NoOfTickets;

    // total price of passenger tickets
    protected double totalPrice;

    //__________________________________________________________________________________________________________________
    // Methods --> setNoOfStops, bookSeat, cancelSeat, addTrip, removeTrip

    // Constructor for creating trip
    public Trip(String journeyType, String source, String destination, int year, int month, int day, int hour, int min, int noOfStops, String licensePlate, String tripType) {
        this.journeyType = journeyType;
        this.source = source;
        this.destination = destination;
        setNoOfStops(noOfStops);
        this.date = new Date(year, month, day, hour, min);
        this.vehicle = getVehicle(licensePlate);
        this.tripType = tripType;
        setAvailableSeats();
    }

    public Trip(String ID, String journeyType, String source, String destination, int year, int month, int day, int hour, int min, int noOfStops, String licensePlate, String tripType, double price, int availableSeats) {
        this.journeyType = journeyType;
        this.source = source;
        this.destination = destination;
        this.noOfStops = noOfStops;
        this.date = new Date(year, month, day, hour, min);
        this.vehicle = getVehicle(licensePlate);
        this.tripType = tripType;
        this.price = price;
        this.ID = ID;
        this.availableSeats = availableSeats;
        totalPrice = NoOfTickets * price;
    }

    public void setPrice(){
        // Method to set the price automatically

        if(this.journeyType.equals("external")){
            this.price += 200;
        }
        else {
            this.price += 40;
        }

        if(this.tripType.equals("round-trip")){
            this.price *= 2;
            double discount = this.price * 0.15;
            this.price -= discount;
        }

        this.price += (this.noOfStops * -10);
    }


    public String getTripType() {
        return tripType;
    }

    public String getJourneyType() {
        return journeyType;
    }

    public double getPrice() {
        return price;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public int getNoOfStops() {
        return noOfStops;
    }

    public int getNoOfTickets() {
        return NoOfTickets;
    }

    public double getTotalPrice() {
        totalPrice = NoOfTickets * price;
        return totalPrice;
    }

    public void setNoOfTickets(int noOfTickets) {
        NoOfTickets = noOfTickets;
    }

    public Date getDate() {
        return date;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats() {
        // Set the available seat to be equal the capacity of the vehicle.
        this.availableSeats = this.vehicle.getCapacity();
    }

    public String setNoOfStops(int noOfStops) {
        // Set a limit for the number of stops.
        if (noOfStops <= MAX_STOPS) {
            this.noOfStops = noOfStops;
        } else {
            return "You exceed the maximum number of stops which is 3.";
        }
        return "";
    }

    public Vehicle getVehicle(String licensePlate){
        Vehicle v = null;
        // Search for the Vehicle using licensePlate and when it found add it to allVehicles and return this Vehicles
        for(int i = 0; i < Vehicle.allVehicles.size();i++){
            if(licensePlate.equals(Vehicle.allVehicles.get(i).getLicensePlate())){
                v = Vehicle.allVehicles.get(i);
            }
        }
        return v;
    }

    public String bookSeat(int No_of_tickets) {
        // Book a seat in the trip
        System.out.println("Available Seats: " + availableSeats);

        if ((this.availableSeats != 0) && (this.availableSeats >= No_of_tickets)) {
            this.availableSeats -= No_of_tickets;
            System.out.println("New Available Seats: " + availableSeats);
            return "";
        } else {
            return "there is no available seats in this trip.";
        }
    }

    public void cancelSeat(int No_of_tickets) {
        // Cancel a seat in the trip.
        if (this.availableSeats != this.vehicle.getCapacity()) {
            // return empty seat in the vehicle
            this.availableSeats += No_of_tickets;
        }
    }

    @Override
    public String toString(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return "From: " + this.source + "\nTo: " + this.destination + "\nTrip Type: " + this.tripType +"\nVehicle Type: " + this.vehicle.getVehicleType() + "\nAvailable seats on this trip: " + this.availableSeats + "\nNumber of stops: " + this.noOfStops + "\nDate: " + formatter.format(this.date) + "\nPrice: " + this.price;
    }

    public String preprocessingData() {
        // Method to change the data shape and order it into comma separated values to save it in the database for the first time.
        uniqueID = UUID.randomUUID();

        return "" + this.uniqueID + "," + this.journeyType + "," + this.source + "," + this.destination + "," + this.date.getYear() + "," + this.date.getMonth() +
                "," + this.date.getDay() + "," + this.date.getHours() + "," + this.date.getMinutes() + "," + this.noOfStops + "," + this.vehicle.getLicensePlate() + "," + this.tripType + "," + this.price + "," + this.availableSeats + ",";
    }

    public String preprocessingData_overWrite() {
        // Method to change the data shape and order it into comma separated values to save it in the database.
        return "" + this.ID + "," + this.journeyType + "," + this.source + "," + this.destination + "," + this.date.getYear() + "," + this.date.getMonth() +
                "," + this.date.getDay() + "," + this.date.getHours() + "," + this.date.getMinutes() + "," + this.noOfStops + "," + this.vehicle.getLicensePlate() + "," + this.tripType + "," + this.price + "," + this.availableSeats + ",";
    }

    //DataBase
    //__________________________________________________________________________________________________________________
    // Methods --> addToDataBase, loadTripsData, overWriteTripsDataBase

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

    // Write in the database every new trip the manager creates
    public void addToDataBase(){
        try {
            FileWriter myWriter = new FileWriter("Trip_Database.txt");
            String data = "";
            for (Trip t : Trip.allTrips) {
                data += t.preprocessingData();
                data += System.lineSeparator();
            }
            myWriter.write(data);
            myWriter.close();
        }catch (IOException e) {
            System.out.println("Error accessing database");
            e.printStackTrace();
        }
    }

    public static ArrayList<Trip> loadTripsData(){
        try {
            File file = new File("Trip_Database.txt");

            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String[] words = data.split(",");
                Trip trip = new Trip(words[0], words[1], words[2], words[3], Integer.parseInt(words[4]), Integer.parseInt(words[5])
                        , Integer.parseInt(words[6]),Integer.parseInt(words[7]),Integer.parseInt(words[8]),Integer.parseInt(words[9]),
                        words[10], words[11], Double.parseDouble(words[12]), Integer.parseInt(words[13]));
                allTrips.add(trip);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Couldn't load Trips Data!");
            e.printStackTrace();
            System.out.println(e);
        }
        return allTrips;
    }
}