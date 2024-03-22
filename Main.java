import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Activity {
    private String name;
    private String description;
    private double cost;
    private int capacity;
    private Destination destination;

    public Activity(String name, String description, double cost, int capacity, Destination destination) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.capacity = capacity;
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Destination getDestination() {
        return destination;
    }
}

class Destination {
    private String name;
    private List<Activity> activities;

    public Destination(String name) {
        this.name = name;
        this.activities = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public List<Activity> getActivities() {
        return activities;
    }
}

class Passenger {
    private String name;
    private int passengerNumber;
    private double balance;
    private PassengerType type;
    private List<Activity> activities;

    public Passenger(String name, int passengerNumber, PassengerType type) {
        this.name = name;
        this.passengerNumber = passengerNumber;
        this.type = type;
        this.activities = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getPassengerNumber() {
        return passengerNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public PassengerType getType() {
        return type;
    }

    public boolean signUp(Activity activity) {
        if (activity.getCapacity() > 0) {
            if (type == PassengerType.PREMIUM || (type == PassengerType.GOLD && balance >= activity.getCost() * 0.9) || (type == PassengerType.STANDARD && balance >= activity.getCost())) {
                activities.add(activity);
                activity.setCapacity(activity.getCapacity() - 1);
                if (type == PassengerType.GOLD) {
                    balance -= activity.getCost() * 0.9;
                } else if (type == PassengerType.STANDARD) {
                    balance -= activity.getCost();
                }
                return true;
            }
        }
        return false;
    }

    public List<Activity> getActivities() {
        return activities;
    }
}

enum PassengerType {
    STANDARD, GOLD, PREMIUM
}

class TravelPackage {
    private String name;
    private int passengerCapacity;
    private List<Passenger> passengers;
    private List<Destination> itinerary;

    public TravelPackage(String name, int passengerCapacity) {
        this.name = name;
        this.passengerCapacity = passengerCapacity;
        this.passengers = new ArrayList<>();
        this.itinerary = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public boolean addPassenger(Passenger passenger) {
        if (passengers.size() < passengerCapacity) {
            passengers.add(passenger);
            return true;
        }
        return false;
    }

    public void addDestination(Destination destination) {
        itinerary.add(destination);
    }

    public void printItinerary() {
        System.out.println("Travel Package: " + name);
        for (Destination destination : itinerary) {
            System.out.println("Destination: " + destination.getName());
            for (Activity activity : destination.getActivities()) {
                System.out.println("Activity: " + activity.getName() + ", Cost: " + activity.getCost() + ", Capacity: " + activity.getCapacity() + ", Description: " + activity.getDescription());
            }
        }
    }

    public void printPassengerList() {
        System.out.println("Travel Package: " + name);
        System.out.println("Passenger Capacity: " + passengerCapacity);
        System.out.println("Number of Passengers Enrolled: " + passengers.size());
        for (Passenger passenger : passengers) {
            System.out.println("Passenger Name: " + passenger.getName() + ", Passenger Number: " + passenger.getPassengerNumber());
        }
    }

    public void printPassengerDetails(Passenger passenger) {
        System.out.println("Passenger Name: " + passenger.getName());
        System.out.println("Passenger Number: " + passenger.getPassengerNumber());
        if (passenger.getType() != PassengerType.PREMIUM) {
            System.out.println("Balance: " + passenger.getBalance());
        }
        System.out.println("Activities Signed Up For:");
        for (Activity activity : passenger.getActivities()) {
            System.out.println("Destination: " + activity.getDestination().getName() + ", Activity: " + activity.getName() + ", Price Paid: " + activity.getCost());
        }
    }

    public void printAvailableActivities() {
        System.out.println("Activities with Spaces Available:");
        for (Destination destination : itinerary) {
            for (Activity activity : destination.getActivities()) {
                if (activity.getCapacity() > 0) {
                    System.out.println("Destination: " + destination.getName() + ", Activity: " + activity.getName() + ", Spaces Available: " + activity.getCapacity());
                }
            }
        }
    }

    // Getter for itinerary
    public List<Destination> getItinerary() {
        return itinerary;
    }

    // Getter for passengers
    public List<Passenger> getPassengers() {
        return passengers;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get travel package details
        System.out.println("Enter Travel Package Name:");
        String packageName = scanner.nextLine();
        System.out.println("Enter Passenger Capacity:");
        int passengerCapacity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        TravelPackage travelPackage = new TravelPackage(packageName, passengerCapacity);

        // Get destinations and activities
        System.out.println("Enter Number of Destinations:");
        int numDestinations = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 0; i < numDestinations; i++) {
            System.out.println("Enter Destination Name:");
            String destinationName = scanner.nextLine();
            Destination destination = new Destination(destinationName);

            System.out.println("Enter Number of Activities for " + destinationName + ":");
            int numActivities = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            for (int j = 0; j < numActivities; j++) {
                System.out.println("Enter Activity Name:");
                String activityName = scanner.nextLine();

                System.out.println("Enter Activity Description:");
                String activityDescription = scanner.nextLine();

                System.out.println("Enter Activity Cost:");
                double activityCost = scanner.nextDouble();

                System.out.println("Enter Activity Capacity:");
                int activityCapacity = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                Activity activity = new Activity(activityName, activityDescription, activityCost, activityCapacity, destination);
                destination.addActivity(activity);
            }

            travelPackage.addDestination(destination);
        }

        // Get passengers and sign up for activities
        System.out.println("Enter Number of Passengers:");
        int numPassengers = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 0; i < numPassengers; i++) {
            System.out.println("Enter Passenger Name:");
            String passengerName = scanner.nextLine();

            System.out.println("Enter Passenger Number:");
            int passengerNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.println("Enter Passenger Type (1 for STANDARD, 2 for GOLD, 3 for PREMIUM):");
            int passengerTypeChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            PassengerType passengerType;
            switch (passengerTypeChoice) {
                case 1:
                    passengerType = PassengerType.STANDARD;
                    break;
                case 2:
                    passengerType = PassengerType.GOLD;
                    break;
                case 3:
                    passengerType = PassengerType.PREMIUM;
                    break;
                default:
                    System.out.println("Invalid choice. Setting passenger type to STANDARD.");
                    passengerType = PassengerType.STANDARD;
            }

            Passenger passenger = new Passenger(passengerName, passengerNumber, passengerType);

            if (passengerType != PassengerType.PREMIUM) {
                System.out.println("Enter Passenger Balance:");
                double balance = scanner.nextDouble();
                passenger.setBalance(balance);
                scanner.nextLine(); // Consume newline
            }

            travelPackage.addPassenger(passenger);

            // Sign up passenger for activities
            for (Destination destination : travelPackage.getItinerary()) {
                for (Activity activity : destination.getActivities()) {
                    System.out.println("Would " + passengerName + " like to sign up for " + activity.getName() + "? (yes/no):");
                    String signUpChoice = scanner.nextLine().toLowerCase();

                    if (signUpChoice.equals("yes")) {
                        boolean signedUp = passenger.signUp(activity);
                        if (signedUp) {
                            System.out.println("Successfully signed up " + passengerName + " for " + activity.getName());
                        } else {
                            System.out.println("Failed to sign up " + passengerName + " for " + activity.getName() + ". Insufficient balance or activity capacity reached.");
                        }
                    }
                }
            }
        }

        // Print itinerary
        travelPackage.printItinerary();

        // Print passenger list
        travelPackage.printPassengerList();

        // Print details of individual passengers
        for (Passenger passenger : travelPackage.getPassengers()) {
            travelPackage.printPassengerDetails(passenger);
        }

        // Print available activities
        travelPackage.printAvailableActivities();

        scanner.close();
    }
}
