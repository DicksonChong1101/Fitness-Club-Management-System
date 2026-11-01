import java.util.Scanner;

public class InputHelper {

    //use same scanner with MainSystem
    private static Scanner input = MainSystem.input;

    // This method to avoid if you want user enter int but user input wrongly like press some letter (String)
    // This method is used to avoid String input and transfer value to int.
    public static int getInput() {
        while (true) {
            String value = input.nextLine();
            if (value.length() == 0) {
                System.out.print("Invalid input! Enter number: ");
                continue;
            }
            boolean isNumber = true;
            for (int i = 0; i < value.length(); i++) {
                if (!Character.isDigit(value.charAt(i))) {
                    isNumber = false;
                    break;
                }
            }
            if (!isNumber) {
                System.out.print("Invalid input! Enter number: ");
                continue;
            }
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.print("Number too large! Enter a smaller number: ");
            }
        }
    }


    // Helper method: reads a positive double, returns 0 if user cancels
    public static double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt + " (or 0 to cancel): ");
            String inputLine = input.nextLine();
            if (inputLine.equals("0")) {
                return 0;
            }
            try {
                double value = Double.parseDouble(inputLine);
                if (value > 0) {
                    return value;
                } else {
                    System.out.println("Please enter a positive number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("That's not a valid number. Please try again.");
            }
        }
    }

    //Register and Login checking insert part:
    public static boolean isAllDigits(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsInvalidOperator(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '+' || c == '-' || c == '*' || c == '/') {
                return true;
            }
        }
        return false;
    }


}
