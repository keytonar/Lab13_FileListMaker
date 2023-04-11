import java.util.Scanner;
/**
 *
 * @param pipe a Scanner opened to read from System.in
 * @param prompt prompt for the user
 * @return a String response that is not zero length
 */


public class SafeInput
{
    public static String getNonZeroLenString(Scanner pipe, String prompt)
    {
        String retString = "";  // Set this to zero length. Loop runs until it isn’t
        do
        {
            System.out.print("\n" + prompt + ":"); // show prompt add space
            retString = pipe.nextLine();
        } while (retString.length() == 0);

        return retString;
    }
    /**
     * Prompts the user to input an integer value and validates the input.
     *
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return an integer value entered by the user
     */
    public static int getInt(Scanner pipe, String prompt) {
        int value = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print(prompt);
            if (pipe.hasNextInt()) {
                value = pipe.nextInt();
                validInput = true;
            } else {
                String trash = pipe.next();
                System.out.println("Invalid input. Please enter an integer value.");
            }
            pipe.nextLine(); // clear the newline character from the input stream
        }
        return value;
    }
    public static double getDouble(Scanner pipe, String prompt) {
        double value = 0.0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print(prompt);
            if (pipe.hasNextDouble()) {
                value = pipe.nextDouble();
                validInput = true;
            } else {
                String trash = pipe.next();
                System.out.println("Invalid input. Please enter a double value.");
            }
            pipe.nextLine(); // clear the newline character from the input stream
        }
        return value;
    }
    public static int getRangedInt(Scanner pipe, String prompt, int low, int high) {
        int value = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print(prompt + " [" + low + " - " + high + "]: ");
            if (pipe.hasNextInt()) {
                value = pipe.nextInt();
                if (value >= low && value <= high) {
                    validInput = true;
                } else {
                    System.out.println("Invalid input. Please enter an integer within the range [" + low + " - " + high + "].");
                }
            } else {
                String trash = pipe.next();
                System.out.println("Invalid input. Please enter an integer within the range [" + low + " - " + high + "].");
            }
            pipe.nextLine(); // clear the newline character from the input stream
        }
        return value;
    }
    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high) {
        double input = 0;
        boolean validInput = false;
        do {
            System.out.print(prompt + " [" + low + " - " + high + "]: ");
            if (pipe.hasNextDouble()) {
                input = pipe.nextDouble();
                pipe.nextLine(); // clear the newline character
                if (input >= low && input <= high) {
                    validInput = true;
                } else {
                    System.out.println("Input must be within range [" + low + " - " + high + "]");
                }
            } else {
                pipe.nextLine(); // clear the input
                System.out.println("Invalid input, please enter a valid decimal number.");
            }
        } while (!validInput);
        return input;
    }
    public static boolean getYNConfirm(Scanner pipe, String prompt) {
        String input;
        do {
            System.out.print(prompt + " [Y/N]: ");
            input = pipe.nextLine().toUpperCase();
        } while (!input.equals("Y") && !input.equals("N"));
        return input.equals("True");
    }
    public static String getRegExString(Scanner pipe, String prompt, String regEx) {
        String input = "";
        do {
            System.out.print(prompt + ": ");
            while (!pipe.hasNextLine()) {
                // Do nothing
            }
            input = pipe.nextLine().trim();
            pipe.reset(); // Clear any delimiter from the pipe
        } while (!input.matches(regEx));
        return input;
    }
    public static void prettyHeader(String msg) {
        int msgLength = msg.length();
        int padding = (60 - msgLength - 6) / 2; // 6 = 3 stars on each side
        String paddingStr = "";
        for (int i = 0; i < padding; i++) {
            paddingStr += " ";
        }
        String header = "";
        for (int i = 0; i < 60; i++) {
            header += "*";
        }
        System.out.println(header);
        System.out.println("***" + paddingStr + msg + paddingStr + "***");
        System.out.println(header);
    }


}