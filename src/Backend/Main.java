package Backend;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Scanner input = new Scanner(System.in);

        // load database
        Vehicle.loadVehicleData();
        Trip.loadTripsData();
        Manager.loadManagersData();
        Driver.loadDriversData();
        Passenger.loadPassengersData();

        System.out.println("1.Login");
        System.out.println("2.Register");
        System.out.print("Choose: ");
        int MainMenuChoice = input.nextInt();

        if (MainMenuChoice == 1) {
            System.out.println("1.Passenger");
            System.out.println("2.Employee");
            System.out.print("Choose: ");
            MainMenuChoice = input.nextInt();

            if (MainMenuChoice == 1) {
                System.out.print("UserName: ");
                String username = input.next();

                System.out.print("Password: ");
                String password = input.next();

                boolean p= Passenger.Login(username, password, "Passenger");
                if (p) { // passenger
                    Passenger passenger = Passenger.LoginPassengerData(username);
                    // Passenger's code

                    System.out.println(passenger.refund());

                    MainMenuChoice = PassengerChoice(input);
                    while (MainMenuChoice != 5) {
                        if (MainMenuChoice == 1) {
                            int Size = Trip.allTrips.size();
                            System.out.println("Available Trips to book: \n");
                            for (int i = 0; i < Size; i++) {
                                System.out.println("Trip " + (i + 1) + ":\n" + Trip.allTrips.get(i));
                                System.out.println(" ");
                            }
                            System.out.print("choose the trip you want from the trips shown above: ");
                            int userTripChoice = input.nextInt();

                            System.out.print("select the number of tickets you want to book: ");
                            int userTripQuantity = input.nextInt();

                            passenger.bookTicket(Trip.allTrips.get((userTripChoice - 1)), userTripQuantity);

                            MainMenuChoice = PassengerChoice(input);

                        } else if (MainMenuChoice == 2) {
                            System.out.println(passenger.reviewTickets());

                            System.out.print("choose the trip you want to cancel: ");
                            int passengerTripChoice = input.nextInt();
                            System.out.println(passenger.cancelTrip(passenger.passengerTrips.get((passengerTripChoice - 1))));

                            MainMenuChoice = PassengerChoice(input);

                        } else if (MainMenuChoice == 3) {
                            System.out.println(passenger.reviewTickets());

                            MainMenuChoice = PassengerChoice(input);

                        } else if (MainMenuChoice == 4) {
                            passenger.displayProfile();
                            MainMenuChoice = PassengerChoice(input);
                        } else {
                            System.out.println("incorrect menu input");
                            PassengerChoice(input);
                        }
                    }
                } else {
                    System.out.println("login again");
                }
            } else {
                System.out.println("1.Manager");
                System.out.println("2.Driver");
                System.out.print("Choose: ");
                MainMenuChoice = input.nextInt();

                if (MainMenuChoice == 1) {
                    System.out.print("UserName: ");
                    String username = input.next();

                    System.out.print("Password: ");
                    String password = input.next();

                    boolean m = Manager.Login(username, password, "Manager");

                    if (m) { //Manager Menu
                        Manager manager = Manager.LoginManagerData(username);

                        int managerChoice = ManagerChoice(input);

                        while (managerChoice != 8) {

                            if (managerChoice == 1) { // review all trips
                                System.out.println("Trips active at the moment: \n");
                                int tripsSize = Trip.allTrips.size();
                                for (int i = 0; i < tripsSize; i++) {
                                    System.out.println(Trip.allTrips.get(i));
                                    System.out.println(" ");
                                }
                                managerChoice = ManagerChoice(input);
                            }

                            if (managerChoice == 2) { // add new vehicle
                                System.out.println("Type of Vehicle: ");
                                String type = input.next();
                                System.out.println("Capacity of the Vehicle: ");
                                int capacity = input.nextInt();
                                System.out.println("License plate");
                                String licensePlate = input.next();
                                manager.addVehicle(type, capacity, licensePlate);

                                managerChoice = ManagerChoice(input);
                            }

                            if (managerChoice == 3) { // Add trip
                                System.out.println("* Add Trip Menu");
                                System.out.println("Select Journey type \n1- Internal 2- External");
                                int managerChoice2 = input.nextInt();
                                String journeyType = null;
                                String tripType = null;
                                if (managerChoice2 == 1) {
                                    journeyType = "internal";
                                }
                                if (managerChoice2 == 2) {
                                    journeyType = "external";
                                }
                                System.out.println("Write the Source");
                                String source = input.next();
                                System.out.println("Write the Destination");
                                String dest = input.next();
                                System.out.println("Write the date: ");
                                System.out.print("Year: ");
                                int year = input.nextInt();
                                System.out.print("Month: ");
                                int month = input.nextInt();
                                System.out.print("Day: ");
                                int day = input.nextInt();
                                System.out.print("Hour: ");
                                int hour = input.nextInt();
                                System.out.print("Minute: ");
                                int minute = input.nextInt();
                                System.out.println("what are the number of stops in this trip");
                                int numberOfStops = input.nextInt();
                                manager.displayVehciles();
                                System.out.print("Vehcile's licensePlate: ");
                                String licensePlate = input.next();
                                System.out.println("Select Trip type \n1- One-Way 2- Round Trip");
                                int managerChoice3 = input.nextInt();
                                if (managerChoice3 == 1) {
                                    tripType = "one-way";
                                }
                                if (managerChoice3 == 2) {
                                    tripType = "round-trip";
                                }
                                manager.managerAddTrip(journeyType, source, dest, year, month, day, hour, minute, numberOfStops, licensePlate, tripType);
                                managerChoice = ManagerChoice(input);
                            }

                            if (managerChoice == 4) { // Cancel Trip
                                int tripsSize = Trip.allTrips.size();
                                for (int i = 0; i < tripsSize; i++) {
                                    System.out.println("Trip #"+(i+1)+"\n"+Trip.allTrips.get(i));
                                    System.out.println(" ");
                                }
                                System.out.print("choose the trip you want from the trips shown above: ");
                                int userTripChoice = input.nextInt();

                                manager.cancelTrip(Trip.allTrips.get(userTripChoice-1));
                                managerChoice = ManagerChoice(input);
                            }

                            if (managerChoice == 5) { // Assign certain Driver to a Trip
                                manager.displayDrivers();
                                System.out.print("select the driver you want to assign a trip to: ");
                                int chosenDriver = input.nextInt();
                                int tripsSize = Trip.allTrips.size();
                                for (int i = 0; i < tripsSize; i++) {
                                    System.out.println("Trip #"+(i+1)+"\n"+Trip.allTrips.get(i));
                                    System.out.println(" ");
                                }
                                System.out.print("choose the trip you want to assign the driver to: ");
                                int chosenTrip = input.nextInt();
                                manager.assignDriverToTrip(Driver.allDrivers.get(chosenDriver - 1), Trip.allTrips.get(chosenTrip-1));
                                managerChoice = ManagerChoice(input);
                            }
                            if (managerChoice == 6) { // Generate a Report
                                manager.GenerateReport();
                                managerChoice = ManagerChoice(input);
                            }
                            if (managerChoice == 7) { //Display Managaer Profile
                                System.out.println(manager.displayManagerInfo());
                                managerChoice = ManagerChoice(input);
                            } else {
                                System.out.println("incorrect menu input");
                                ManagerChoice(input);
                            }
                        }
                    } else {
                        System.out.println("login again");
                    }
                } else {
                    System.out.print("UserName: ");
                    String username = input.next();

                    System.out.print("Password: ");
                    String password = input.next();

                    boolean c1 = Driver.Login(username, password, "Driver");

                    if (c1) {
                        Driver d1 = Driver.LoginDriversData(username);
                        // driver's code
                        System.out.println("\n\nDriver info: ");
                        System.out.println(d1.displayDriverInfo());
                        System.out.println("Your assigned Trip are: \n");
                        d1.reviewAssignedTrips();
                    } else {
                        System.out.println("login again");
                    }
                }
            }
        } else {
            System.out.println("1.Passenger");
            System.out.println("2.Employee");
            System.out.print("Choose: ");
            MainMenuChoice = input.nextInt();

            if (MainMenuChoice == 1) {
                System.out.print("Name: ");
                String name = input.next();

                System.out.print("Phone Number: ");
                String phonenumber = input.next();

                System.out.print("UserName: ");
                String username = input.next();
                if (usernameSpecifics(username)) {
                    System.out.print("Email: ");
                    String email = input.next();
                    System.out.print("Password must be more than or equal to 8 digits: ");
                    String password = input.next();
                    if (passwordSpecifics(password)) {
                        User p1 = new User(username, phonenumber, name, email, password);
                        p1.Register("Passenger", p1);
                    } else {
                        System.out.println("Password cannot contain Spaces or Comma \",\"");
                    }
                }  else {
                    System.out.println("Username cannot contain Spaces or Comma \",\"");
                }


            } else {
                System.out.println("1.Manager");
                System.out.println("2.Driver");
                System.out.print("Choose: ");
                MainMenuChoice = input.nextInt();

                if (MainMenuChoice == 1) {
                    System.out.print("Name: ");
                    String name = input.next();

                    System.out.print("Phone Number: ");
                    String phonenumber = input.next();

                    System.out.print("UserName: ");
                    String username = input.next();
                    if (usernameSpecifics(username)) {
                        System.out.print("Email: ");
                        String email = input.next();
                        System.out.print("Password must be more than or equal to 8 digits: ");
                        String password = input.next();
                        if (passwordSpecifics(password)) {
                            User p1 = new User(username, phonenumber, name, email, password);
                            p1.Register("Manager", p1);
                        } else {
                            System.out.println("Password cannot contain Spaces or Comma \",\"");
                        }
                    }  else {
                        System.out.println("Username cannot contain Spaces or Comma \",\"");
                    }

                } else {
                    System.out.print("Name: ");
                    String name = input.next();

                    System.out.print("Phone Number: ");
                    String phonenumber = input.next();

                    System.out.print("UserName: ");
                    String username = input.next();
                    if (usernameSpecifics(username)) {
                        System.out.print("Email: ");
                        String email = input.next();
                        System.out.print("Password must be more than or equal to 8 digits: ");
                        String password = input.next();
                        if (passwordSpecifics(password)) {
                            System.out.print("Type of Vehicle you drive: ");
                            String v = input.next();
                            Driver d3 = new Driver(username, phonenumber, name, email, password);
                            d3.Register("Driver", v, d3);
                        } else {
                            System.out.println("Password cannot contain Spaces or Comma \",\"");
                        }
                    }  else {
                        System.out.println("Username cannot contain Spaces or Comma \",\"");
                    }

                }
            }
        }
    }


    public static int PassengerChoice(Scanner input) {
        System.out.println("1- Book ticket\t\t2- Cancel Ticket\n3- Review Tickets\t4- Display Profile\n5- Exit");
        int choice = input.nextInt();
        return choice;
    }

    public static int ManagerChoice(Scanner input) {
        System.out.println("");
        System.out.println("Manager's Main Menu: ");
        System.out.println("1- Review All Trips \t 2- Add New Vehicle \n3- Add a Trip \t\t\t 4- Remove a trip \n5- Assign Driver to Trip \t\t\t 6- Report \n7- Display Profile \t\t\t 8- Exit");
        int choice = input.nextInt();
        return choice;
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
}