import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * DecodeLabs Engineering Directive // Phase 1: Logic & Control Flow
 * Project 1: Number Guessing Game Engine
 * Class Name: DecodeLabs_Java_P1
 */
public class DecodeLabs_Java_P1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int totalRounds = 0;
        int totalScore = 0;
        final int MAX_ATTEMPTS = 7; // Binary search strategy solves 1-100 in ~7 guesses

        System.out.println("=================================================");
        System.out.println("    WELCOME TO DECODELABS NUMBER GAME ENGINE     ");
        System.out.println("=================================================");

        boolean playAgain = true;

        // Session Persistence: Multiple rounds loop
        while (playAgain) {
            totalRounds++;
            
            // Stochastic Generation: 1 to 100 inclusive
            int targetNumber = random.nextInt(100) + 1;
            int attemptsUsed = 0;
            boolean guessedCorrectly = false;

            System.out.println("\n--- Round " + totalRounds + " ---");
            System.out.println("Target generated! Guess the number between 1 and 100.");
            System.out.println("Limit: " + MAX_ATTEMPTS + " attempts available.");

            // Feedback loop & Attempt limiter
            while (attemptsUsed < MAX_ATTEMPTS && !guessedCorrectly) {
                attemptsUsed++;
                System.out.print("\nAttempt " + attemptsUsed + "/" + MAX_ATTEMPTS + " - Enter your guess: ");

                int guess = -1;
                boolean validInteger = false;

                // Defensive Engineering: Input Validation to prevent runtime crashes
                while (!validInteger) {
                    try {
                        guess = scanner.nextInt();
                        validInteger = true;
                    } catch (InputMismatchException e) {
                        System.out.println("[Error] Invalid input type. Please enter a valid integer (1-100).");
                        scanner.nextLine(); // Flush invalid token from buffer
                        System.out.print("Try entering your guess again: ");
                    }
                }

                // Core Logic & High/Low Feedback
                if (guess == targetNumber) {
                    guessedCorrectly = true;
                    int roundPoints = (MAX_ATTEMPTS - attemptsUsed + 1) * 10;
                    totalScore += roundPoints;

                    System.out.println(">> SUCCESS: You guessed the correct number!");
                    System.out.println(">> Attempts Used : " + attemptsUsed);
                    System.out.println(">> Round Score   : " + roundPoints + " pts");
                } else if (guess > targetNumber) {
                    System.out.println(">> Feedback: Too High!");
                } else {
                    System.out.println(">> Feedback: Too Low!");
                }
            }

            if (!guessedCorrectly) {
                System.out.println("\n>> OUT OF ATTEMPTS! Game Over for Round " + totalRounds);
                System.out.println(">> The target number was: " + targetNumber);
            }

            // Hazard Warning Handling: Scanner Trap Prevention
            // Flush leftover newline character before reading String input
            scanner.nextLine(); 

            boolean validChoice = false;
            while (!validChoice) {
                System.out.print("\nWould you like to play another round? (Y/N): ");
                String choice = scanner.nextLine().trim();

                if (choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("YES")) {
                    playAgain = true;
                    validChoice = true;
                } else if (choice.equalsIgnoreCase("N") || choice.equalsIgnoreCase("NO")) {
                    playAgain = false;
                    validChoice = true;
                } else {
                    System.out.println("[Error] Please enter 'Y' for Yes or 'N' for No.");
                }
            }
        }

        // Display Final Session Statistics
        System.out.println("\n=================================================");
        System.out.println("               FINAL GAME SESSION SUMMARY        ");
        System.out.println("=================================================");
        System.out.println(" Total Rounds Played : " + totalRounds);
        System.out.println(" Cumulative Score    : " + totalScore + " pts");
        System.out.println(" Thank you for engineering with DecodeLabs!");
        System.out.println("=================================================");

        scanner.close();
    }
} 
