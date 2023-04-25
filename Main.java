package chucknorris;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean isRunning = true;
        while (isRunning) {
            displayMenu();
            String input = scanner.nextLine();
            switch (input) {
                case "encode" -> encrypt(scanner);
                case "decode" -> decrypt(scanner);
                case "exit" -> isRunning = false;
                default -> System.out.printf("There is no '%s' operation\n", input);
            }
        }
        System.out.println("Bye!");
    }

    public static void displayMenu() {
        System.out.println("\nPlease input operation (encode/decode/exit):");
    }

    public static void encrypt(Scanner scanner) {
        System.out.println("Input string:");
        String message = scanner.nextLine();
        StringBuilder binaryMessage = new StringBuilder();
        for (char c : message.toCharArray()) {
            binaryMessage.append(String.format("%07d", Integer.parseInt(Integer.toBinaryString(c))));
        }
        Pattern p = Pattern.compile("(0+|1+)");
        Matcher m = p.matcher(binaryMessage);
        StringBuilder result = new StringBuilder();
        while (m.find()) {
            result.append(m.group().contains("1") ? "0" : "00").append(" ").append("0".repeat(m.group().length())).append(" ");
        }
        System.out.printf("\nEncoded string:\n%s\n", result);
    }

    public static void decrypt(Scanner scanner) {
        System.out.println("Input encoded string:");
        String message = scanner.nextLine();
        String[] messageSplit = message.split("\\s+");
        StringBuilder binaryMessage = new StringBuilder();
        for (int i = 0; i < messageSplit.length; i += 2) {
            String symbol;
            switch (messageSplit[i]) {
                case "00" -> symbol = "0";
                case "0" -> symbol = "1";
                default -> symbol = null;
            }
            if (symbol == null) {
                binaryMessage.delete(0, binaryMessage.length());
                break;
            }
            int amount = messageSplit[i+1].length();
            binaryMessage.append(symbol.repeat(amount));
        }
        Pattern p = Pattern.compile("(\\d){7}");
        Matcher m = p.matcher(binaryMessage);
        if (binaryMessage.length() % 7 == 0 && binaryMessage.length() != 0) {
            StringBuilder result = new StringBuilder();
            while (m.find()) {
                int code = Integer.parseInt(m.group(), 2);
                result.append((char) code);
            }
            System.out.printf("\nDecoded string:\n%s\n", result);
        } else {
            System.out.println("Encoded string is not valid.");
        }
    }
}