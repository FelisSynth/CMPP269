/*
 * Assignment #3 Car Rental Program
 *
 * Program requires user to input a filename with all cars stored which then displays them to the user.
 * After this, the user is prompted with multiple options such as rent a car, return a car,etc. Once an option is selected
 * and the user inputs information (if prompted) the list of Cars is refreshed and the options are once again displayed.
 * If the user requests a car that is unavailable the program will display a message and the user will be prompted to press the enter key.
 * When the user is finished pressing the last option E will close the system displaying the number of rentals and the sum of each rental. The program
 * will also save each input and change into the file.
 *
 * Inputs:
 * Car filename
 * Renter Name
 * Renter Phone
 *
 * By: Trung Hieu, Zain Arshad, and Buola Achor.
 *
 * 05/12/21
 */

import java.io.*;
import java.util.*;

public class Assignment3 {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("*** Welcome to Mo's Classic Car Rentals ***");
        System.out.println();
        System.out.print("Enter car data filename: ");
        String fileName = in.nextLine();
        ArrayList<Car> carList = new ArrayList<>();
        loadCar(fileName, carList);
        displayCars(carList);
        String option = getOption(in).toLowerCase();

        //If user enters does not enter E, program will process each option of the car, then display each car and option after refreshing.
        while (!option.equals("e")) {
            processCar(option, carList);
            displayCars(carList);
            option = getOption(in).toLowerCase();
        }

        //Save the changes of the session, then displays rentals and sum when user enters E
        saveCars(fileName, carList);
        System.out.println();
        System.out.println("Number of rentals initiated in this session: " + Car.rentalCount);
        System.out.println("Total rental rate revenue from this session: $" + Car.rateSum);
        System.out.println();
        System.out.println("Good bye!");

    }

    public static void loadCar(String fileName, ArrayList<Car> carList) throws IOException {
        //Reads in filename an creates scanner
        File file = new File(fileName);
        Scanner input = new Scanner(file).useDelimiter(",|\r\n");
        
        int year;
        int rate;
        String make;
        String model;
        boolean needsService;
        boolean isRented;
        String renterName;
        String renterPhone;

        if (!file.exists()) {
            System.exit(1);
        }

        //Reads in each line of the file
        while (input.hasNext()) {
            year = input.nextInt();
            make = input.next();
            model = input.next();
            rate = input.nextInt();
            needsService = input.nextBoolean();
            isRented = input.nextBoolean();
            renterName = input.next();
            renterPhone = input.next();

            //make new car object and add to current ArrayList
            Car x = new Car(make, model, year, rate, needsService, isRented, renterName, renterPhone);

            carList.add(x);
        }

        input.close();
    }

    public static void saveCars(String fileName, ArrayList<Car> carList) throws IOException {
        //Reads in filename
        File file = new File(fileName);

        if (!file.exists()) {
            System.exit(0);
        }

        //Creates File Writer object to allow the file to be overwritten
        FileWriter fw = new FileWriter(fileName, false);
        PrintWriter pw = new PrintWriter(fw);

        int i;

        int year;
        int rate;
        String make;
        String model;
        boolean needsService;
        boolean isRented;
        String renterName;
        String renterPhone;

        //Writes each each line of Car values to the file
        for (i = 0; i < carList.size(); i++) {

            year = carList.get(i).getYear();
            make = carList.get(i).getMake();
            model = carList.get(i).getModel();
            rate = carList.get(i).getRate();
            needsService = carList.get(i).getNeedsService();
            isRented = carList.get(i).getIsRented();
            renterName = carList.get(i).getRenterName();
            renterPhone = carList.get(i).getRenterPhone();

            pw.write(year + ",");
            pw.write(make + ",");
            pw.write(model + ",");
            pw.write(rate + ",");
            pw.write(needsService + ",");
            pw.write(isRented + ",");
            pw.write(renterName + ",");
            pw.write(renterPhone);
            pw.write("\r\n");
        }

        pw.close();
    }

    public static void displayCars(ArrayList<Car> carList) {
        //Formats the three stars and displays each car
        //Clear screen with "System.out.println("\u000C");", temporarily commented out to generate Test Plan sample
        //System.out.println("\u000C");
        System.out.println();
        System.out.printf("%32s%3s%3s\n", "*", "*", "*");
        System.out.println("Cars");
        for (int i = 1; i < 6; i++) {
            System.out.println("  " + i + ". " + carList.get(i - 1));
        }
        System.out.println();
    }

    public static String getOption(Scanner in) {
        //Displays each option to prompt user
        System.out.println("Options");
        System.out.println(" A. Rent a car\n" +
                " B. Return a car\n" +
                " C. Flag car for servicing\n" +
                " D. Clear car from servicing\n" +
                " E. To exit program\n");
        System.out.print("Enter your option (by letter): ");

        return in.next();
    }

    public static void processCar(String option, ArrayList<Car> carList) {

        // Create a Scanner object attached to the keyboard
        Scanner in = new Scanner(System.in);
        
        //Name + Number parameter for client
        String newName;
        String newPhone;
        
        //Get car number with line-based input as token-based input will skip newName input in case "a"
        String tempNum;
        int carNumber;
        
        System.out.print("Rent a car. Enter car selection (by number): ");

        tempNum = in.nextLine();
        carNumber = Integer.parseInt(tempNum) - 1;

        //switch statement for options A - D
        switch (option) {
            //rent a car
            case "a":
                if (!carList.get(carNumber).getIsRented() && !carList.get(carNumber).getNeedsService()) {
                    System.out.print("Enter renter's name: ");
                    newName = in.nextLine();

                    System.out.print("Enter renter's phone #: ");
                    newPhone = in.nextLine();

                    carList.get(carNumber).setRented(newName, newPhone);

                } else {
                    System.out.println("The " + carList.get(carNumber).getMake() + " " +
                            carList.get(carNumber).getModel() + " is not available to rent");

                    System.out.print("Press [Enter] to continue...");
                    //"in.nextLine" will take in user input but it will not be stored
                    in.nextLine();

                }
                break;

            //return a car
            case "b":
                if (carList.get(carNumber).getIsRented()) {

                    carList.get(carNumber).setReturned();

                } else {
                    System.out.println("The " + carList.get(carNumber).getMake() + " " +
                            carList.get(carNumber).getModel() + " is not rented and cannot be returned");

                    System.out.print("Press [Enter] to continue...");
                    in.nextLine();

                }
                break;

            //flag car for service
            case "c":
                if (!carList.get(carNumber).getNeedsService()) {

                    carList.get(carNumber).setNeedsService(true);

                } else {
                    System.out.println("The " + carList.get(carNumber).getMake() +  " " +
                            carList.get(carNumber).getModel() + " is already flagged for servicing");

                    System.out.print("Press [Enter] to continue...");
                    in.nextLine();

                }
                break;

            //clear car from service
            case "d":
                if (carList.get(carNumber).getNeedsService()) {

                    carList.get(carNumber).setNeedsService(false);

                } else {
                    System.out.println("The " + carList.get(carNumber).getMake() + " " +
                            carList.get(carNumber).getModel() + " is not flagged as needing service");

                    System.out.print("Press [Enter] to continue...");
                    in.nextLine();

                }
                break;
        }
    }
}
