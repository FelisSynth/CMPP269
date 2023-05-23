/**
 * Assignment #2 Employee Salary Program
 * 
 * Program requires user to input filename, salary filename, employee first and last name. When done, it invokes the first method 
 * which reads in employee data. It then later reads the employee file until it finds the id, date, birth, and verifies if the input
 * name matches the one in the file. After that, the program reads for salary data based on id input. If not found, the program exits. 
 * Then formats all of the reads information into a table to be viewed by user.
 * 
 * Inputs:
 * Employee filename
 * Salary filename
 * Employee firstname
 * Employee lastname
 * 
 * By: Trung Hieu, Zain Arshad, and Buola Achor.
 * 
 * 09/11/21
 */
import java.io.*;
import java.util.*;

public class Assignment2 {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);

        //Prompts user to add file names and employee names
        System.out.print("Enter employee filename (full path): ");
        String fileName = in.nextLine();

        System.out.print("Enter employee salary filename (full path): ");
        String fileSalary = in.nextLine();

        System.out.print("Enter employee last name: ");
        String lastName = in.nextLine();
        lastName = lastName.toLowerCase();

        System.out.print("Enter employee first name: ");
        String firstName = in.nextLine();
        firstName = firstName.toLowerCase();

        //Invokes lookupEmployeeID method
        String empID = lookupEmployeeID(fileName, lastName, firstName);

        //Invokes printEmployeeSalary method
        printEmployeeSalary(empID, fileSalary,lastName,firstName);

    }
    
    //Reads in employee data and returns ID for later use 
    public static String lookupEmployeeID(String fileName, String lastName, String firstName) throws IOException {
        String ID = "";
        
        //Creates file object and scanner to read file
        File empName = new File(fileName);
        Scanner input = new Scanner(empName);
        input.useDelimiter("[,\n]");
        
        //Initializes variables outside of loop
        String tempFirstName;
        String tempLastName;
        String tempBirth;
        String tempID;
        String tempDate;
        
        //Reads names, id, birth, and date. Checks if entered first and last name is present in file and assigns recorded ID to ID variable.
        while (input.hasNext()) {
            tempFirstName = input.next();
            tempFirstName = tempFirstName.toLowerCase();

            tempLastName = input.next();
            tempLastName = tempLastName.toLowerCase();

            tempBirth = input.next();
            tempBirth = tempBirth.toLowerCase();

            tempID = input.next();
            tempID = tempID.toLowerCase();

            tempDate = input.next();
            tempDate = tempDate.toLowerCase();

            if (tempFirstName.equals(firstName)) {
                if (tempLastName.equals(lastName)) {
                    ID = tempID.toUpperCase();
                    break;
                }
            }
        }
        //Stops further input
        input.close();
        
        //Exits method and returns ID
        return ID;
    }
    
    //Reads in salary data to later be formatted in next method
    public static void printEmployeeSalary(String ID, String fileSalary, String lastName, String firstName) throws IOException {
        //Creates scanner object to read data and initializes variables
        String empID = "";
        Scanner input = new Scanner(new File(fileSalary));
        input.useDelimiter(",|\r\n");
        
        //Loop used to read salary data and display information not found for users not shown in salary data
        while (input.hasNext()) {
            while (!empID.equals(ID)) {
                empID = input.next();
            }
            
            if (empID == "") {
                System.out.print("No Employee information found for: " + firstName.substring(0, 1).toUpperCase() + firstName.substring(1) 
                    + " " + lastName.substring(0, 1).toUpperCase() + lastName.substring(1));

                System.exit(0);
            }

            double monthlySalary = input.nextDouble();
            double fedTax = input.nextDouble();
            double abTax = input.nextDouble();
            double cpp = input.nextDouble();
            double ei = input.nextDouble();
            
            System.out.println();
            System.out.println("Salary Schedule for " + firstName.toUpperCase() + " " + lastName.toUpperCase() + " " + "(" + empID + ")");
            //Invokes printPaySchedule to use values read in current method
            printPaySchedule(monthlySalary, fedTax, abTax, cpp, ei);
            break;
        }

    }

    //Formats data to display income,tax,cpp, and ei over a year
    public static void printPaySchedule(double Salary, double FedTax, double ABTax, double CPP, double EI) {
        //Formats the top part of the data showing every monthly salary, tax, etc from that year. Also includes two seperate headers showing gross and net salary.
        System.out.printf("%17s%48s\n", "Gross", "Net");
        System.out.printf("%5s%12s%12s%12s%12s%12s\n", "Month", "Salary", "Tax", "CPP", "EI", "Salary");
        for (int i = 1; i <= 65; i++) {
            System.out.print("-");
        }
        System.out.println();

        //Creates constants for maximum cpp and ei while also calculating tax and intializing variables
        final double MAX_CPP = 3166.45;
        final double MAX_EI = 889.54;
        double availableCPP = MAX_CPP;
        double availableEI = MAX_EI;
        double totalTax = FedTax + ABTax;
        double totalNetSalary = 0;

        //Based on the available cpp and ei, program decrements each month until they are equal in which the maximum amount of cpp and ei is added.
        for (int i = 1; i <= 12; i++) {
            if (CPP < availableCPP) {
                availableCPP -= CPP;
            } else {
                CPP = availableCPP;
                availableCPP = 0;
            }

            if (EI < availableEI) {
                availableEI -= EI;
            } else {
                EI = availableEI;
                availableEI = 0;
            }
            
            //The program calculates net salary by subtracting all taxes, cpp and ei from salary.
            double netSalary = Salary - ABTax - FedTax - CPP - EI;
            System.out.printf("%5d%,12.2f%,12.2f%,12.2f%,12.2f%,12.2f\n", i, Salary, totalTax, CPP, EI, netSalary);
            totalNetSalary += netSalary;
        }
        
        //Creates a line using for loop and formats the final value at the bottom after a blank line
        for (int i = 1; i <= 65; i++) {
            System.out.print("-");
        }
        System.out.println();

        System.out.printf("%5s%,12.2f%,12.2f%,12.2f%,12.2f%,12.2f\n", "Total", Salary * 12, totalTax * 12, MAX_CPP, MAX_EI, totalNetSalary);
    }
}