package Task_4;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class number_guess {
    private static final int MIN_RANGE = 1;
    private static final int MAX_RANGE = 100;
    private Random random;
    private Scanner scanner;

    public number_guess() {
        random = new Random();
        scanner = new Scanner(System.in);
    }

    // Generate a random number between MIN_RANGE and MAX_RANGE (inclusive)
    private int generateRandomNumber() {
        return random.nextInt(MAX_RANGE - MIN_RANGE + 1) + MIN_RANGE;
    }

    // Play one round of the game
    private void playRound() {
        int secretNumber = generateRandomNumber();
        int attempts = 0;
        int guess = 0;
        boolean hasGuessedCorrectly = false;

        System.out.printf("\nI'm thinking of a number between %d and %d. Can you guess it?\n", MIN_RANGE, MAX_RANGE);

        while (!hasGuessedCorrectly) {
            System.out.print("Enter your guess: ");
            try {
                guess = scanner.nextInt();
                attempts++;

                if (guess < MIN_RANGE || guess > MAX_RANGE) {
                    System.out.printf("Please enter a number between %d and %d.\n", MIN_RANGE, MAX_RANGE);
                    continue;
                }

                if (guess < secretNumber) {
                    System.out.println("Too low! Try again.");
                } else if (guess > secretNumber) {
                    System.out.println("Too high! Try again.");
                } else {
                    hasGuessedCorrectly = true;
                    System.out.printf("Congratulations! You guessed the number %d in %d attempt(s).\n", secretNumber, attempts);
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a whole number.");
                scanner.next(); // clear invalid token
            }
        }
    }

    // Main game loop – allows multiple rounds
    public void start() {
        System.out.println("=== Welcome to the Number Guessing Game ===");
        boolean keepPlaying = true;

        while (keepPlaying) {
            playRound();

            System.out.print("\nDo you want to play again? (yes/no): ");
            String response = scanner.next().toLowerCase();
            if (!response.equals("yes") && !response.equals("y")) {
                keepPlaying = false;
                System.out.println("Thanks for playing! Goodbye.");
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        number_guess game = new number_guess();
        game.start();
    }
}