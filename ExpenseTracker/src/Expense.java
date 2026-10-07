import java.time.LocalDate;

/** A single expense entry. */
public class Expense {
    private final int id;
    private double amount;
    private String category;
    private LocalDate date;
    private String description;

    public Expense(int id, double amount, String category, LocalDate date, String description) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    public int getId() { return id; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    /** CSV line used for file storage. Commas in text are replaced by semicolons. */
    public String toCsv() {
        return id + "," + amount + "," + category.replace(',', ';') + "," + date + ","
                + description.replace(',', ';');
    }

    public static Expense fromCsv(String line) {
        String[] p = line.split(",", 5);
        if (p.length < 5) throw new IllegalArgumentException("Malformed line: " + line);
        return new Expense(Integer.parseInt(p[0]), Double.parseDouble(p[1]), p[2],
                LocalDate.parse(p[3]), p[4]);
    }

    @Override
    public String toString() {
        return String.format("%-5d %10.2f  %-14s %-12s %s", id, amount, category, date, description);
    }
}
