import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** Holds expenses in an ArrayList and persists them to a CSV file. */
public class ExpenseManager {
    private final List<Expense> expenses = new ArrayList<>();
    private final Path file;
    private int nextId = 1;

    public ExpenseManager(String fileName) {
        this.file = Paths.get(fileName);
        load();
    }

    public Expense add(double amount, String category, LocalDate date, String description) {
        Expense e = new Expense(nextId++, amount, category, date, description);
        expenses.add(e);
        save();
        return e;
    }

    public Expense find(int id) throws ExpenseException {
        for (Expense e : expenses) {
            if (e.getId() == id) return e;
        }
        throw new ExpenseException("No expense found with ID " + id);
    }

    public void update(int id, Double amount, String category, LocalDate date, String description)
            throws ExpenseException {
        Expense e = find(id);
        if (amount != null) e.setAmount(amount);
        if (category != null) e.setCategory(category);
        if (date != null) e.setDate(date);
        if (description != null) e.setDescription(description);
        save();
    }

    public void delete(int id) throws ExpenseException {
        expenses.remove(find(id));
        save();
    }

    public List<Expense> all() {
        return new ArrayList<>(expenses);
    }

    public double total() {
        double sum = 0;
        for (Expense e : expenses) sum += e.getAmount();
        return sum;
    }

    /** Category name (title-cased key kept as entered, case-insensitive) mapped to total spent. */
    public Map<String, Double> totalsByCategory() {
        Map<String, Double> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (Expense e : expenses) {
            map.merge(e.getCategory(), e.getAmount(), Double::sum);
        }
        return map;
    }

    public double totalForMonth(int year, int month) {
        double sum = 0;
        for (Expense e : expenses) {
            if (e.getDate().getYear() == year && e.getDate().getMonthValue() == month) {
                sum += e.getAmount();
            }
        }
        return sum;
    }

    // ---------- File handling ----------
    private void save() {
        List<String> lines = new ArrayList<>();
        for (Expense e : expenses) lines.add(e.toCsv());
        try {
            Files.write(file, lines);
        } catch (IOException ex) {
            System.out.println("Warning: could not save data (" + ex.getMessage() + ")");
        }
    }

    private void load() {
        if (!Files.exists(file)) return;
        try {
            for (String line : Files.readAllLines(file)) {
                if (line.isBlank()) continue;
                try {
                    Expense e = Expense.fromCsv(line);
                    expenses.add(e);
                    nextId = Math.max(nextId, e.getId() + 1);
                } catch (RuntimeException bad) {
                    System.out.println("Skipping corrupted record: " + line);
                }
            }
        } catch (IOException ex) {
            System.out.println("Warning: could not read data file (" + ex.getMessage() + ")");
        }
    }
}
