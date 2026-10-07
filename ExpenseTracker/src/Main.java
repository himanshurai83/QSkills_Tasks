import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/** Menu-driven entry point for the Expense Tracker. */
public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static ExpenseManager manager;

    public static void main(String[] args) {
        String dataFile = args.length > 0 ? args[0] : "expenses.csv";
        manager = new ExpenseManager(dataFile);
        System.out.println("=== Expense Tracker === (data file: " + dataFile + ")");
        try {
            while (true) {
                menu();
                String c = prompt("Enter choice: ");
                try {
                    switch (c) {
                        case "1" -> add();
                        case "2" -> update();
                        case "3" -> delete();
                        case "4" -> viewAll();
                        case "5" -> summary();
                        case "6" -> monthly();
                        case "7" -> {
                            System.out.println("Goodbye! Your data is saved.");
                            return;
                        }
                        default -> System.out.println("Invalid choice. Enter 1-7.");
                    }
                } catch (ExpenseException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("\nInput ended. Exiting.");
        }
    }

    private static void menu() {
        System.out.println("\n1. Add expense");
        System.out.println("2. Update expense");
        System.out.println("3. Delete expense");
        System.out.println("4. View all expenses");
        System.out.println("5. Total & category-wise summary");
        System.out.println("6. Monthly total");
        System.out.println("7. Exit");
    }

    private static String prompt(String msg) {
        System.out.print(msg);
        return in.nextLine().trim();
    }

    private static double parseAmount(String s) throws ExpenseException {
        try {
            double a = Double.parseDouble(s);
            if (a <= 0 || Double.isNaN(a) || Double.isInfinite(a)) {
                throw new ExpenseException("Amount must be greater than 0.");
            }
            return a;
        } catch (NumberFormatException e) {
            throw new ExpenseException("Amount must be a number.");
        }
    }

    private static LocalDate parseDate(String s) throws ExpenseException {
        if (s.isEmpty()) return LocalDate.now();
        try {
            return LocalDate.parse(s);
        } catch (DateTimeParseException e) {
            throw new ExpenseException("Date must be in YYYY-MM-DD format.");
        }
    }

    private static int parseId(String s) throws ExpenseException {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new ExpenseException("ID must be a whole number.");
        }
    }

    private static void add() throws ExpenseException {
        double amount = parseAmount(prompt("Amount: "));
        String category = prompt("Category (e.g. Food, Travel): ");
        if (category.isEmpty()) throw new ExpenseException("Category cannot be empty.");
        LocalDate date = parseDate(prompt("Date YYYY-MM-DD (blank = today): "));
        String desc = prompt("Description: ");
        Expense e = manager.add(amount, category, date, desc);
        System.out.println("Expense saved with ID " + e.getId());
    }

    private static void update() throws ExpenseException {
        int id = parseId(prompt("Expense ID to update: "));
        Expense e = manager.find(id);
        System.out.println("Leave blank to keep current value.");
        String a = prompt("Amount [" + e.getAmount() + "]: ");
        String c = prompt("Category [" + e.getCategory() + "]: ");
        String d = prompt("Date [" + e.getDate() + "]: ");
        String ds = prompt("Description [" + e.getDescription() + "]: ");
        manager.update(id,
                a.isEmpty() ? null : parseAmount(a),
                c.isEmpty() ? null : c,
                d.isEmpty() ? null : parseDate(d),
                ds.isEmpty() ? null : ds);
        System.out.println("Expense updated.");
    }

    private static void delete() throws ExpenseException {
        manager.delete(parseId(prompt("Expense ID to delete: ")));
        System.out.println("Expense deleted.");
    }

    private static void viewAll() {
        List<Expense> list = manager.all();
        if (list.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }
        System.out.printf("%-5s %10s  %-14s %-12s %s%n", "ID", "Amount", "Category", "Date", "Description");
        System.out.println("-".repeat(70));
        list.forEach(System.out::println);
    }

    private static void summary() {
        if (manager.all().isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }
        System.out.printf("Total spending: %.2f%n", manager.total());
        System.out.println("By category:");
        for (Map.Entry<String, Double> en : manager.totalsByCategory().entrySet()) {
            System.out.printf("  %-14s %10.2f%n", en.getKey(), en.getValue());
        }
    }

    private static void monthly() throws ExpenseException {
        String s = prompt("Month (YYYY-MM): ");
        try {
            String[] p = s.split("-");
            int y = Integer.parseInt(p[0]);
            int m = Integer.parseInt(p[1]);
            if (m < 1 || m > 12) throw new ExpenseException("Month must be 1-12.");
            System.out.printf("Total for %s: %.2f%n", s, manager.totalForMonth(y, m));
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new ExpenseException("Use the format YYYY-MM (example: 2026-10).");
        }
    }
}
