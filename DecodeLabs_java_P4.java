import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * DecodeLabs - Project 4: Currency Converter
 * Enterprise Financial Engine Logic
 */
public class DecodeLabs_java_P4 {


    // 1. Exchange Rates (USD Pivot Rates)
    private static final BigDecimal USD_TO_EUR = new BigDecimal("0.92");
    private static final BigDecimal USD_TO_GBP = new BigDecimal("0.79");
    private static final BigDecimal USD_TO_INR = new BigDecimal("83.50");
    private static final BigDecimal USD_TO_JPY = new BigDecimal("155.20");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        System.out.println("=================================================");
        System.out.println("   ENTERPRISE CURRENCY CONVERTER ENGINE v1.2    ");
        System.out.println("=================================================");

        // 2. Continuous Routing (do-while Loop)
        do {
            displayMenu();
            System.out.print("\nSelect Option (1-6): ");

            int choice = -1;

            // Input Validation for Menu Choice
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\n❌ SECURITY GATE: Invalid input type! Please enter a number.");
                scanner.nextLine(); // Clear scanner buffer
                continue;
            }

            if (choice == 6) {
                System.out.println("\nExiting System. Engine shut down successfully.");
                keepRunning = false;
                break;
            }

            if (choice < 1 || choice > 6) {
                System.out.println("\n❌ ERROR: Invalid menu option. Choose between 1 and 6.");
                continue;
            }

            // 3. Security Gate Validation for Amount
            BigDecimal amount = getValidAmount(scanner);

            if (amount != null) {
                processConversion(choice, amount);
            }

        } while (keepRunning);

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n---------------- MAIN MENU ----------------");
        System.out.println("1. USD to INR (Direct Conversion)");
        System.out.println("2. EUR to INR (Cross-Rate Routing)");
        System.out.println("3. GBP to INR (Cross-Rate Routing)");
        System.out.println("4. USD to JPY (Direct Conversion)");
        System.out.println("5. INR to USD (Direct Conversion)");
        System.out.println("6. Exit Engine");
        System.out.println("-------------------------------------------");
    }

    private static BigDecimal getValidAmount(Scanner scanner) {
        while (true) {
            System.out.print("Enter monetary amount: ");
            try {
                String input = scanner.next();
                BigDecimal amount = new BigDecimal(input);

                // Reject negative values
                if (amount.compareTo(BigDecimal.ZERO) < 0) {
                    System.out.println("\n❌ SECURITY GATE: Negative amounts are strictly rejected!");
                    return null;
                }

                return amount;
            } catch (Exception e) {
                System.out.println("\n❌ SECURITY GATE: Invalid input format. Enter a valid number.");
                scanner.nextLine(); // Clear buffer
                return null;
            }
        }
    }

    // 4. Conversion Logic & Financial Polish
    private static void processConversion(int choice, BigDecimal amount) {
        BigDecimal result = BigDecimal.ZERO;
        String sourceCurrency = "";
        String targetCurrency = "";

        switch (choice) {
            case 1:
                sourceCurrency = "USD";
                targetCurrency = "INR";
                result = amount.multiply(USD_TO_INR);
                break;

            case 2: // EUR -> USD -> INR (Cross-Rate)
                sourceCurrency = "EUR";
                targetCurrency = "INR";
                BigDecimal eurInUsd = amount.divide(USD_TO_EUR, 10, RoundingMode.HALF_EVEN);
                result = eurInUsd.multiply(USD_TO_INR);
                break;

            case 3: // GBP -> USD -> INR (Cross-Rate)
                sourceCurrency = "GBP";
                targetCurrency = "INR";
                BigDecimal gbpInUsd = amount.divide(USD_TO_GBP, 10, RoundingMode.HALF_EVEN);
                result = gbpInUsd.multiply(USD_TO_INR);
                break;

            case 4:
                sourceCurrency = "USD";
                targetCurrency = "JPY";
                result = amount.multiply(USD_TO_JPY);
                break;

            case 5:
                sourceCurrency = "INR";
                targetCurrency = "USD";
                result = amount.divide(USD_TO_INR, 10, RoundingMode.HALF_EVEN);
                break;

            default:
                return;
        }

        // Banker's Rounding (HALF_EVEN) & Decimal Formatting
        BigDecimal formattedResult = result.setScale(2, RoundingMode.HALF_EVEN);

        System.out.println("\n---------------- CONVERSION RESULT ----------------");
        System.out.printf("Input Amount : %s %,.2f\n", sourceCurrency, amount);
        System.out.printf("Converted    : %s %,.2f\n", targetCurrency, formattedResult);
        System.out.println("Precision    : Absolute Precision Guaranteed (HALF_EVEN)");
        System.out.println("---------------------------------------------------");
    }
} 
