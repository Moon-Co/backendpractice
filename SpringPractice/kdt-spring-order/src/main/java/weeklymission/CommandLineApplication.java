package weeklymission;

import java.util.Scanner;

public class CommandLineApplication {
    public static void main(String[] args) {
        System.out.println("=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher.\n" +
                "Type list to list all vouchers.");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();


    }
}
