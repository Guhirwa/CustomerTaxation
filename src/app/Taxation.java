package app;

import java.util.Scanner;

public class Taxation {
    public static void main(String[] args) {

        int choice;

        System.out.println("========================================");
        System.out.println("            CUSTOM TAXATION             ");
        System.out.println("========================================");

        Scanner scanner  = new Scanner(System.in);

        System.out.println("1. Register Tax Payer");
        System.out.println("2. Update Tax Payer");
        System.out.println("3. Search Tax Payer");
        System.out.println("4. Delete Tax Payer");
        System.out.println("5. Display All Tax Payers");
        System.out.println("0. Exit");
        System.out.println("----------------------------------------");

        System.out.print("Choose: ");
        choice = scanner.nextInt();

        switch(choice) {
            case 1:
                System.out.println("Option 1 selected");
                break;
            case 2:
                System.out.println("Option 2 selected");
                break;
            case 3:
                System.out.println("Option 3 selected");
                break;
            case 4:
                System.out.println("Option 4 selected");
                break;
            case 5:
                System.out.println("Option 5 selected");
                break;
            case 0:
                System.out.println("Option 0 selected");
                break;
            default:
                System.out.println("Wrong Choice !!!");
                break;
        }
    }
}
