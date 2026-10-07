/** A book in the library. Tracks total and available copies. */
public class Book {
    private final int id;
    private String title;
    private String author;
    private String category;
    private int totalCopies;
    private int availableCopies;

    public Book(int id, String title, String author, String category, int copies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.totalCopies = copies;
        this.availableCopies = copies;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }

    public boolean isAvailable() { return availableCopies > 0; }

    void issueCopy() { availableCopies--; }
    void returnCopy() { availableCopies++; }

    /** Changes total copies, keeping the number currently issued unchanged. */
    public void setTotalCopies(int newTotal) throws LibraryException {
        int issued = totalCopies - availableCopies;
        if (newTotal < issued) {
            throw new LibraryException("Cannot set copies below the " + issued + " currently issued.");
        }
        this.totalCopies = newTotal;
        this.availableCopies = newTotal - issued;
    }

    @Override
    public String toString() {
        return String.format("%-5d %-28s %-20s %-14s %d/%d",
                id, title, author, category, availableCopies, totalCopies);
    }
}
