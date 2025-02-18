package app;

import java.util.Scanner;

public class Taxation {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("            CUSTOM TAXATION             ");
        System.out.println("========================================");

        Scanner scanner  = new Scanner(System.in);

        System.out.println("1. Register Tax Payer");
        String payerName = scanner.next();
        System.out.println("2. Update Tax Payer");
        System.out.println("3. Search Tax Payer");
        System.out.println("4. Delete Tax Payer");
        System.out.println("5. Display All Tax Payers");
        System.out.println("0. Exit");
    }
}
