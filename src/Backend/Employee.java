package Backend;
import java.util.ArrayList;

public class Employee extends User {
    // Attributes --> allManagers, allDrivers

    // List of Managers.
    protected static ArrayList<Manager> allManagers = new ArrayList<Manager>();
    // List of Drivers.
    public static ArrayList<Driver> allDrivers = new ArrayList<Driver>();

    //__________________________________________________________________________________________________________________

    public Employee(String userName, String phoneNumber, String name, String email, String password){
        super(userName, phoneNumber, name, email, password);
    }
}