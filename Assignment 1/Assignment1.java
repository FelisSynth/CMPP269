
/**
Import Exemptions by: Trung Hieu, Zain, and Buola.

*This program calculates the Import Exemption based on the number of days absent and total amount of goods.

*User first inputs full name (and initials), days absent, and total amount of imported goods.

*The program will then assign variables to later calculate the taxes, exemption, total goods, and whether or not to include tobacco.

*If absence is less than one day, there will be no personal exemption.

*If more than or equal to one but less than two days, up to 200 may be claimed as personal exemption. No alcohol or tobacco can be claimed as personal exemption.
if it exceeds 200, no exemption will be claimed and regular duty taxes applies to total amount of imported goods.
    
*If the absence is more than or equal to two days up to 800 can be claimed as personal exemption. Alcohol and tobacco can also be included in exemption.
if goods exceeds 800, import duty and taxes have a special duty rate applied to the next 300 while regular applies to all amounts exceeding 1100.

*After this, it will display the amount calculated in the proper formating spaced between two lines. 

*Inputs:
*     Traveller Name
*     Length of Absence
*     Total amount of imported Goods

*Processing: Calculations:
*     Special Duty & Taxes
*     Regular Duty & Taxes
*     Exemption
*     Usable Personal Exemption


*Output:
*     Absence Period
*     Total Amount of Imported Goods
*     Maximum Personal Exemption
*     Inclusion of alcohol or Tobacco
*     Usable Personal Exemption
*     Special and Regular Duty Taxes


*Oct 14, 2021

*/
import java.util.*;

public class Assignment1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        //Prompts user to enter name, days of absence, and amount of imported goods ($ CAD).
        
        System.out.print("Traveller's name (last, first, initials): ");
            String last = in.next();
            String first = in.next();
            String initial = in.next();

        System.out.print("Length of absence (# of days): ");
            double absence = in.nextDouble();

        System.out.print("Total amount of imported goods ($ CAD): ");
            double goods = in.nextDouble();

        //Assigns variables for later use.
        
            double exemption = 0;
            double special = 0;
            double regular = 0;
            double maxGoods = 0;
                String alcoholTobacco = "No";

        //Uses if, else, and else if statements to decide proper input based on days absent.
        
        if (absence < 1) {
            regular = goods;
        } else if (absence >= 1 && absence < 2) {
            maxGoods = 200;
            if (goods <= 200) {
                exemption = goods;
            } else {
                regular = goods;
            }
        } else {
            alcoholTobacco = "Yes";
            maxGoods = 800;
            if (goods > 1100) {
                regular = goods - 1100;
                special = 300;
                exemption = 800;
            } else if (goods > 800) {
                special = goods - 800;
                exemption = 800;
            } else {
                exemption = goods;
            }
        }

        //Formating of the dollar sign next to each input (determined by days absent).
        
            String stringMaxGoods = String.format("$%.2f", maxGoods);
            String stringExemption = String.format("$%.2f", exemption);
            String stringSpecial = String.format("$%.2f", special);
            String stringRegular = String.format("$%.2f", regular);
            String stringGoods = String.format("$%.2f", goods);

        //Displays the initial input for users name and arranges it by first, initial, and last.
        
        System.out.println("Import Exemption Report for " + first.substring(0, first.length()-1) + " " + initial + " " + last.substring(0, last.length()-1));
        
        //Prints a dotted line across the page below previous message.
        
        for (int i = 0; i < 60; i++) {
            System.out.print("-");
            }
        
        //Formating for information determined by absent days. Includes a blank space and the space between each output and message.
        
        System.out.println();
        System.out.printf("%-40s%10.1f%n", "Absence Period (days)", absence);
        System.out.printf("%-40s%10s%n", "...Maximum Personal Exemption", stringMaxGoods);
        System.out.printf("%-40s%10s%n", "...Include Limited Alcohol & Tobacco?", alcoholTobacco);
        System.out.printf("%-40s%20s%n", "Usable Personal Exemption", stringExemption);
        System.out.printf("%-40s%20s%n", "Amount Subject to Special Duty & Taxes ", stringSpecial);
        System.out.printf("%-40s%20s%n", "Amount Subject to Regular Duty & Taxes ", stringRegular);
        System.out.printf("%60s%n", "----------");
        System.out.printf("%-40s%20s%n", "Total Amount of Imported Goods", stringGoods);
            
        //Prints a dotted line across the bottom of the page.    
        for (int i = 0; i < 60; i++) {
                System.out.print("-");
                }
                
    }
}
