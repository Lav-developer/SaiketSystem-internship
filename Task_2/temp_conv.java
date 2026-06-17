package Task_2;
import java.util.InputMismatchException;
import java.util.Scanner;

public class temp_conv {
    // Convert Celsius to Fahrenheit
    public static double celsiusToFahrenheit(double celsius) {
        return (celsius * 9.0 / 5.0) + 32;
    }

    // Convert Fahrenheit to Celsius
    public static double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5.0 / 9.0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        double inputTemp, outputTemp;

        System.out.println("=== Temperature Converter ===");

        do {
            // Display menu
            System.out.println("\nSelect conversion direction:");
            System.out.println("1. Celsius to Fahrenheit");
            System.out.println("2. Fahrenheit to Celsius");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");

            // Handle invalid menu input
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number (1, 2, or 3).");
                scanner.next(); // clear invalid token
                continue;
            }

            if (choice == 3) {
                System.out.println("Exiting converter. Goodbye!");
                break;
            }

            if (choice < 1 || choice > 3) {
                System.out.println("Invalid choice! Please select 1, 2, or 3.");
                continue;
            }

            // Read temperature value
            System.out.print("Enter the temperature to convert: ");
            try {
                inputTemp = scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid temperature! Please enter a numeric value.");
                scanner.next(); // clear invalid input
                continue;
            }

            // Perform conversion and display result
            if (choice == 1) {
                outputTemp = celsiusToFahrenheit(inputTemp);
                System.out.printf("%.2f°C = %.2f°F%n", inputTemp, outputTemp);
            } else { // choice == 2
                outputTemp = fahrenheitToCelsius(inputTemp);
                System.out.printf("%.2f°F = %.2f°C%n", inputTemp, outputTemp);
            }

        } while (true);

        scanner.close();
    }
}
