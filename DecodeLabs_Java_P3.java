import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * ============================================================================
 * CLASS: BankAccount
 * OOP CONCEPT: Encapsulation & Data Hiding
 * RESPONSIBILITY: Models bank account data, handles core financial logic, 
 *                 ensures data integrity, and maintains transaction history.
 * ============================================================================
 */
class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private int pin;
    private List<String> transactionHistory;

    /**
     * Parameterized Constructor to initialize account state.
     * @param accountNumber     Unique identifier for the bank account
     * @param accountHolderName Name of the account owner
     * @param initialBalance    Starting balance amount
     * @param pin               4-digit security authentication PIN
     */
    public BankAccount(String accountNumber, String accountHolderName, double initialBalance, int pin) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.pin = pin;
        this.transactionHistory = new ArrayList<>();
        
        // Log account creation as the initial transaction entry
        this.transactionHistory.add("Account opened with initial balance: ₹" + String.format("%.2f", initialBalance));
    }

    /**
     * Authenticates user access by comparing input PIN with stored PIN.
     * @param inputPin User-entered 4-digit PIN
     * @return boolean True if authenticated successfully, false otherwise
     */
    public boolean validatePin(int inputPin) {
        return this.pin == inputPin;
    }

    // Controlled Getter methods providing read-only access to private fields
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    /**
     * Deposits money into the account after strict input validation.
     * @param amount Deposit quantity
     * @return boolean True if transaction succeeded, false if validation failed
     */
    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Invalid amount! Deposit must be greater than zero.");
            return false;
        }

        balance += amount;
        transactionHistory.add("Deposited: ₹" + String.format("%.2f", amount) + " | Balance: ₹" + String.format("%.2f", balance));
        System.out.printf("✅ Successfully deposited ₹%.2f\n", amount);
        return true;
    }

    /**
     * Withdraws money from the account after balance verification and validation checks.
     * @param amount Withdrawal quantity
     * @return boolean True if transaction succeeded, false if validation failed
     */
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Invalid amount! Withdrawal must be greater than zero.");
            return false;
        }

        if (amount > balance) {
            System.out.println("❌ Insufficient Balance! Transaction failed.");
            return false;
        }

        balance -= amount;
        transactionHistory.add("Withdrew: ₹" + String.format("%.2f", amount) + " | Balance: ₹" + String.format("%.2f", balance));
        System.out.printf("✅ Successfully withdrew ₹%.2f\n", amount);
        return true;
    }

    /**
     * Prints all recorded account transactions in chronological order.
     */
    public void printTransactionHistory() {
        System.out.println("\n--- Transaction History ---");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (String record : transactionHistory) {
                System.out.println("• " + record);
            }
        }
        System.out.println("---------------------------");
    }
}

/**
 * ============================================================================
 * CLASS: ATM
 * OOP CONCEPT: Modular Design & Separation of Concerns
 * RESPONSIBILITY: Handles User Interface (UI), menu navigation, input scanner,
 *                 and delegates business operations to the BankAccount class.
 * ============================================================================
 */
class ATM {
    private BankAccount account;
    private Scanner scanner;

    public ATM(BankAccount account) {
        this.account = account;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("=====================================================");
        System.out.println("     WELCOME TO DECODELABS DIGITAL ATM INTERFACE     ");
        System.out.println("=====================================================");

        System.out.print("Enter your 4-digit PIN: ");
        int enteredPin = scanner.nextInt();

        if (!account.validatePin(enteredPin)) {
            System.out.println("❌ Incorrect PIN. Access Denied!");
            return;
        }

        System.out.println("✅ Login Successful!");
        System.out.println("Account Holder: " + account.getAccountHolderName() + " | Acc No: " + account.getAccountNumber());
        
        boolean running = true;
        while (running) {
            displayMenu();
            System.out.print("Select an option (1-5): ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> checkBalance();
                case 2 -> handleDeposit();
                case 3 -> handleWithdrawal();
                case 4 -> account.printTransactionHistory();
                case 5 -> {
                    System.out.println("Thank you for using DecodeLabs ATM. Goodbye!");
                    running = false;
                }
                default -> System.out.println("❌ Invalid option! Choose between 1 and 5.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Transaction History");
        System.out.println("5. Exit");
    }

    private void checkBalance() {
        System.out.printf("Current Balance: ₹%.2f\n", account.getBalance());
    }

    private void handleDeposit() {
        System.out.print("Enter deposit amount: ₹");
        double amount = scanner.nextDouble();
        account.deposit(amount);
    }

    private void handleWithdrawal() {
        System.out.print("Enter withdrawal amount: ₹");
        double amount = scanner.nextDouble();
        account.withdraw(amount);
    }
}

/**
 * ============================================================================
 * CLASS: DecodeLabs_Java_P3
 * RESPONSIBILITY: Main application entry point matching the exact file name.
 * ============================================================================
 */
public class DecodeLabs_Java_P3 {
    public static void main(String[] args) {
        BankAccount userAccount = new BankAccount("7569192190", "Sirimalla Naga Vamsi", 5000.00, 1234);
        ATM atmMachine = new ATM(userAccount);
        atmMachine.start();
    }
}