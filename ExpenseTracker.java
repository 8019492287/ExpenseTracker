import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ExpenseTracker {
    private User user;
    private List<Expense> expenses;

    public ExpenseTracker(User user) {
        this.user = user;
        this.expenses = new ArrayList<>();
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public double calculateTotalExpenses(String category) {
        double total = 0;
        for (Expense expense : expenses) {
            if (expense.getCategory().equals(category)) {
                total += expense.getAmount();
            }
        }
        return total;
    }

    public void saveExpensesToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Expense expense : expenses) {
                writer.println(expense.getDate() + "," + expense.getCategory() + "," + expense.getAmount());
            }
        } catch (IOException e) {
            System.err.println("Error saving expenses to file: " + e.getMessage());
        }
    }

    public void loadExpensesFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Date date = new Date(parts[0]);
                String category = parts[1];
                double amount = Double.parseDouble(parts[2]);
                Expense expense = new Expense(date, category, amount);
                addExpense(expense);
            }
        } catch (IOException e) {
            System.err.println("Error loading expenses from file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        User user = new User(username, password);

        ExpenseTracker expenseTracker = new ExpenseTracker(user);

        while (true) {
            System.out.println("1. Add expense");
            System.out.println("2. View expenses");
            System.out.println("3. Calculate total expenses by category");
            System.out.println("4. Save expenses to file");
            System.out.println("5. Load expenses from file");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter date (yyyy-mm-dd): ");
                    String dateStr = scanner.next();
                    Date date = new Date(dateStr);
                    System.out.print("Enter category: ");
                    String category = scanner.next();
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();
                    Expense expense = new Expense(date, category, amount);
                    expenseTracker.addExpense(expense);
                    break;
                case 2:
                    List<Expense> expenses = expenseTracker.getExpenses();
                    for (Expense e : expenses) {
                        System.out.println(e.getDate() + " - " + e.getCategory() + " - " + e.getAmount());
                    }
                    break;
                case 3:
                    System.out.print("Enter category: ");
                    String cat = scanner.next();
                    double total = expenseTracker.calculateTotalExpenses(cat);
                    System.out.println("Total expenses for " + cat + ": " + total);
                    break;
                case 4:
                    System.out.print("Enter filename: ");
                    String filename = scanner.next();
                    expenseTracker.saveExpensesToFile(filename);
                    break;
                case 5:
                    System.out.print("Enter filename: ");
                    filename = scanner.next();
                    expenseTracker.loadExpensesFromFile(filename);
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Expense {
    private Date date;
    private String category;
    private double amount;

    public Expense(Date date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }
}