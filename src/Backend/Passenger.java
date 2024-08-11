package Backend;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Passenger extends User{
    // Attributes --> passengerTrips, allPassengers

    // ArrayList of Trips that will save all trips for the Passenger
    public ArrayList<Trip> passengerTrips = new ArrayList<>();

    // Arraylist that has all passengers
    protected static ArrayList<Passenger> allPassengers = new ArrayList<>();

    // boolean that change if there is trip that has been deleted in trips database
    protected boolean deletedTrips = false;

    //__________________________________________________________________________________________________________________
    // Methods --> bookTicket, cancelTrip, reviewTickets, displayProfile, tripDetails

    // Constructor for registration a new passenger
    public Passenger(String name, String phoneNumber, String userName, String email, String password){
        super(name, phoneNumber, userName, email, password);
    }

    public Passenger(User user){
        super(user.getUserName(), user.getPhoneNumber(), user.getName(), user.getEmail(), user.getPassword());
    }

    public void bookTicket(Trip trip, int No_of_tickets){
        /*
         * Book a seat in the vehicle and add trip to passengerTrips arraylist.
         *
         * parameters:
         *            trip (Trip): trip to add to passengerTrips arraylist.
         */

        // Here we are checking if there is available seats in the Vehicle
        try {
            if(trip.getAvailableSeats() != 0) {
                if(isBooked(trip.ID)){
                    System.out.println("You have booked this ticket");
                }else {
                    if(trip.bookSeat(No_of_tickets).equals("there is no available seats in this trip.")){
                        System.out.println("There is no enough available seats in this trip");
                    }else {
                        passengerTrips.add(trip);
                        trip.setNoOfTickets(No_of_tickets);
                    }
                }
            }

            // overWrite new data in the database.
            overWritePassengerDataBase();
            trip.overWriteTripsDataBase();
        }catch (NullPointerException e){
            System.out.println("Please enter a valid ticket");
        }
    }

    public boolean isBooked(String ID){
        if(this.passengerTrips.isEmpty()){
            return false;
        }else {
            for(Trip t : this.passengerTrips){
                if (t.ID.equals(ID)){
                    return true;
                }else {
                    return false;
                }
            }
            return false;
        }
    }

    public String refund(){
        // refresh the database of passengers if there is a deleted trip
        if(deletedTrips){
            this.overWritePassengerDataBase();
            this.deletedTrips = false;
            return "One or more trips have been deleted you will be refunded on the same credit card you have used to purchase the tickets!";
        }
        return "";
    }

    public String cancelTrip(Trip cancelTrip){
        /*
         * Cancel Trip for the user and return an empty seat in the vehicle.
         *
         * parameters:
         *            cancelTrip (Trip): trip to be removed from the trips arraylist.
         */
        // Checks if passenger has already booked a ticket or not to cancel tickets
        if(this.passengerTrips.isEmpty()){
            return "You need to book ticket first!";
        }else {
            passengerTrips.remove(cancelTrip);
            cancelTrip.cancelSeat(cancelTrip.NoOfTickets);
            cancelTrip.setNoOfTickets(0);
            overWritePassengerDataBase();
            cancelTrip.overWriteTripsDataBase();
        }
        return "You have canceled the trip.";
    }

    public String tripDetails(Trip trip) {
        /*
         * Display trip details (where?when?price...) from the trips array.
         *
         * parameters:
         *            trip (Trip): trip to be displayed.
         */

        return "Trip to " + trip.getDestination() +
        "\nTicket Price: " + trip.getPrice() +
        "\nFrom " + trip.getSource() +
        "\nTrip Type: " + trip.getJourneyType() +
        "\nTicket Type: " + trip.getTripType() +
        "\nDate: " + trip.getDate() +
        "\nNumber of stops: " + trip.getNoOfStops() +
        "\nTrip Vehicle:\n" +
        trip.vehicle +
        "\nAvailable seats: " + trip.getAvailableSeats();
    }

    public String reviewTickets(){
        // Print each booked ticket details.
        int size = passengerTrips.size();
        if(size == 0){
            return "You need to book ticket first!";
        }else {
            String line = "Your Tickets are: \n";

            for (int i = 0; i < size; i++) {
                // Display all trips with their proper number.
                line += "Trip #" + (i + 1) + "\n" + this.tripDetails(passengerTrips.get(i)) + "\n";

            }
            return line;
        }
    }

    public String displayProfile(){
        // Display users profile.
        int size = passengerTrips.size();

        return "Name: " + this.getName() +
        "\nUserName: " + this.getUserName() +
        "\ne-mail: " + this.getEmail() +
        "\nPhone number: " + this.getPhoneNumber() +
        "\nNumber of your Trips = " + size +
        "\nYour ID is " + super.UserID;
    }

    // Database
    //__________________________________________________________________________________________________________________
    // Methods --> overWritePassengerDataBase, loadPassengersData, LoginPassengerData

    // Method to add or remove trips in the database
    public void overWritePassengerDataBase(){
        try {
            // open the file
            FileWriter myWriter = new FileWriter("Passenger_Database.txt");
            // the line we will write later.
            String data = "";
            // Enhanced for loop to loop on all passengers
            for (Passenger p : allPassengers) {

                // add the passenger data in the line
                data += p.saveData();

                if(p.getUserName().equals(this.getUserName())){
                    for (Trip t : p.passengerTrips) {
                        // Add trips ID in the data line
                        data += t.ID + "," + t.NoOfTickets + ",";
                    }
                }else {
                    // for loop to loop on all passengers trips
                    for(int i = 0; i < p.passengerTrips.size(); i++){
                        data += p.passengerTrips.get(i).ID + "," + p.quantity.get(i) + ",";
                    }
                }
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

    protected ArrayList<Integer> quantity = new ArrayList<>();
    public static void loadPassengersData(){
        // Method to load all passengers in the memory in their arraylist
        try {
            File file = new File("Passenger_Database.txt");
            // reader obj that will read all the lines in the file
            Scanner reader = new Scanner(file);
            // Iterate through all the lines in the file
            while (reader.hasNextLine()) {

                String data = reader.nextLine();
                // split the data in the line each time seeing comma
                String[] words = data.split(",");

                // create passenger obj and add it to the allPassengers arraylist
                Passenger passenger = new Passenger(words[0], words[1], words[2], words[3], words[4]);
                passenger.setUserID(words[5]);
                allPassengers.add(passenger);

                // Here we are checking weather the passenger has trips or not and if he does then take all of them add them to his passengerTrips arraylist
                if(words.length > 6){
                    int index_id = 6;
                    int index_no_of_tickets = 7;

                    while (index_no_of_tickets != words.length){
                        // Take trip id and trip quantity
                        String id = words[index_id];
                        String TripQuantity = words[index_no_of_tickets];

                        // check if the trip is still in trip database
                        if(passenger.isTripInDatabase(id)){
                            //if the trip is still in the database then make the flag = false and do not refresh the database
                            Trip t = searchTrip(id);
                            passenger.quantity.add(Integer.parseInt(TripQuantity));
                            passenger.passengerTrips.add(t);
                        }else {
                            passenger.deletedTrips = true;
                        }

                        index_id++;
                        index_no_of_tickets++;
                    }

                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Couldn't load Passenger Data!");
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public boolean isTripInDatabase(String tripID){
        for(Trip t : Trip.allTrips){
            if(t.ID.equals(tripID)){
                return true;
            }
        }
        return false;
    }

    public static Trip searchTrip(String tripID){
        for(Trip t : Trip.allTrips){
            if(t.ID.equals(tripID)){
                return t;
            }
        }
        return null;
    }

    public static Passenger LoginPassengerData(String username){
        /*
         * readPassengerDataBase a method that read the database and return obj of Passenger.
         *
         * parameters:
         *            username (String): username that the user logged_in with we will use it to search for his data.
         *
         * returns: Passenger obj with user data to use the passenger menu.
         */
        Passenger passenger = null;
        // search for the passenger using his username
        for(int i = 0; i < Passenger.allPassengers.size(); i++){
            if(username.equals(Passenger.allPassengers.get(i).getUserName())){
                passenger = Passenger.allPassengers.get(i);

                for(int j = 0; j < passenger.quantity.size(); j++){
                    passenger.passengerTrips.get(j).setNoOfTickets(passenger.quantity.get(j));
                }

            }
        }
        return passenger;
    }
}