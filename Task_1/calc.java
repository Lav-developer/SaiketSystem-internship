import java.util.InputMismatchException;
import java.util.Scanner;

public class calc {

    public static double add(double a, double b) {
        return a + b;
    }

    public static double subtract(double a, double b) {
        return a - b;
    }

    public static double multiply(double a, double b) {
        return a * b;
    }

    public static double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return a / b;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        double num1, num2, result;

        System.out.println("=== Basic Calculator ===");

        do {
            
            System.out.println("\nSelect an operation:");
            System.out.println("1. Addition (+)");
            System.out.println("2. Subtraction (-)");
            System.out.println("3. Multiplication (*)");
            System.out.println("4. Division (/)");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number between 1 and 5.");
                scanner.next(); 
                continue;       
            }

            if (choice == 5) {
                System.out.println("Exiting calculator. Goodbye!");
                break;
            }

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice! Please select 1, 2, 3, 4, or 5.");
                continue;
            }

            try {
                System.out.print("Enter first number: ");
                num1 = scanner.nextDouble();
                System.out.print("Enter second number: ");
                num2 = scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid number! Please enter numeric values.");
                scanner.next(); 
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        result = add(num1, num2);
                        System.out.printf("Result: %.2f + %.2f = %.2f%n", num1, num2, result);
                        break;
                    case 2:
                        result = subtract(num1, num2);
                        System.out.printf("Result: %.2f - %.2f = %.2f%n", num1, num2, result);
                        break;
                    case 3:
                        result = multiply(num1, num2);
                        System.out.printf("Result: %.2f * %.2f = %.2f%n", num1, num2, result);
                        break;
                    case 4:
                        result = divide(num1, num2);
                        System.out.printf("Result: %.2f / %.2f = %.2f%n", num1, num2, result);
                        break;
                    default:
                        System.out.println("Unexpected error.");
                }
            } catch (ArithmeticException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (true);

        scanner.close();
    }
}