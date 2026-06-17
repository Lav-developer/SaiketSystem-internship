package Task_6;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class Transaction {
    private String type; 
    private double amount;
    private double balanceAfter;

    public Transaction(String type, double amount, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    @Override
    public String toString() {
        return String.format("%-10s | $%-8.2f | Balance: $%.2f", type, amount, balanceAfter);
    }
}

abstract class BankAccount {
    private static int nextAccountNumber = 1001;
    protected int accountNumber;
    protected String holderName;
    protected double balance;
    protected ArrayList<Transaction> transactionHistory;

    public BankAccount(String holderName, double initialDeposit) throws IllegalArgumentException {
        if (initialDeposit < 0) {
            throw new IllegalArgumentException("Initial deposit cannot be negative.");
        }
        this.accountNumber = nextAccountNumber++;
        this.holderName = holderName;
        this.balance = initialDeposit;
        this.transactionHistory = new ArrayList<>();
        if (initialDeposit > 0) {
            recordTransaction("Deposit", initialDeposit);
        }
    }

    protected void recordTransaction(String type, double amount) {
        transactionHistory.add(new Transaction(type, amount, balance));
    }

    public void deposit(double amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        balance += amount;
        recordTransaction("Deposit", amount);
    }

    public abstract void withdraw(double amount) throws InsufficientFundsException, IllegalArgumentException;

    public double getBalance() {
        return balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void showTransactionHistory() {
        System.out.println("\nTransaction History for Account " + accountNumber + " (" + holderName + "):");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.printf("%-10s | %-10s | %s%n", "Type", "Amount", "Balance After");
            for (Transaction t : transactionHistory) {
                System.out.println(t);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Account #%d | %s | Balance: $%.2f", accountNumber, holderName, balance);
    }
}

class SavingsAccount extends BankAccount {
    private static final double INTEREST_RATE = 0.03;
    private static final double WITHDRAWAL_LIMIT = 500.0;

    public SavingsAccount(String holderName, double initialDeposit) {
        super(holderName, initialDeposit);
    }

    public void applyInterest() {
        double interest = balance * INTEREST_RATE;
        balance += interest;
        recordTransaction("Interest", interest);
        System.out.printf("Interest of $%.2f added at %.2f%% rate.%n", interest, INTEREST_RATE * 100);
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException, IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > WITHDRAWAL_LIMIT) {
            throw new IllegalArgumentException("Savings accounts have a withdrawal limit of $" + WITHDRAWAL_LIMIT + " per transaction.");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds. Current balance: $" + balance);
        }
        balance -= amount;
        recordTransaction("Withdrawal", amount);
    }
}

public class banking {
    private static ArrayList<BankAccount> accounts = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Welcome to the Basic Banking System ===\n");
        boolean running = true;

        while (running) {
            displayMainMenu();
            int choice = readIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    viewBalance();
                    break;
                case 5:
                    viewTransactionHistory();
                    break;
                case 6:
                    applyInterestToSavings();
                    break;
                case 7:
                    running = false;
                    System.out.println("Thank you for using our banking system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-7.");
            }
        }
        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n===== Main Menu =====");
        System.out.println("1. Create a new savings account");
        System.out.println("2. Deposit money");
        System.out.println("3. Withdraw money");
        System.out.println("4. Check balance");
        System.out.println("5. View transaction history");
        System.out.println("6. Apply interest (Savings account only)");
        System.out.println("7. Exit");
    }

    private static int readIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }

    private static double readDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
                scanner.next();
            }
        }
    }

    private static BankAccount findAccount(int accNumber) {
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber() == accNumber) {
                return acc;
            }
        }
        return null;
    }

    private static void createAccount() {
        System.out.print("Enter account holder name: ");
        scanner.nextLine(); 
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        double initialDeposit = readDoubleInput("Enter initial deposit amount: ");
        try {
            SavingsAccount newAccount = new SavingsAccount(name, initialDeposit);
            accounts.add(newAccount);
            System.out.println("Account created successfully!");
            System.out.println(newAccount);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deposit() {
        int accNum = readIntInput("Enter account number: ");
        BankAccount acc = findAccount(accNum);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }
        double amount = readDoubleInput("Enter amount to deposit: ");
        try {
            acc.deposit(amount);
            System.out.printf("Successfully deposited $%.2f. New balance: $%.2f%n", amount, acc.getBalance());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void withdraw() {
        int accNum = readIntInput("Enter account number: ");
        BankAccount acc = findAccount(accNum);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }
        double amount = readDoubleInput("Enter amount to withdraw: ");
        try {
            acc.withdraw(amount);
            System.out.printf("Successfully withdrew $%.2f. New balance: $%.2f%n", amount, acc.getBalance());
        } catch (InsufficientFundsException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewBalance() {
        int accNum = readIntInput("Enter account number: ");
        BankAccount acc = findAccount(accNum);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }
        System.out.printf("Account #%d (%s) balance: $%.2f%n", acc.getAccountNumber(), acc.getHolderName(), acc.getBalance());
    }

    private static void viewTransactionHistory() {
        int accNum = readIntInput("Enter account number: ");
        BankAccount acc = findAccount(accNum);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }
        acc.showTransactionHistory();
    }

    private static void applyInterestToSavings() {
        int accNum = readIntInput("Enter savings account number: ");
        BankAccount acc = findAccount(accNum);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }
        if (!(acc instanceof SavingsAccount)) {
            System.out.println("Interest can only be applied to savings accounts.");
            return;
        }
        ((SavingsAccount) acc).applyInterest();
    }
}