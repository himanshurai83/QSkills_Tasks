import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/** Menu-driven entry point for the Library Management System. */
public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static final Library library = new Library();

    public static void main(String[] args) {
        System.out.println("=== Library Management System ===");
        seedData();
        try {
            while (true) {
                menu();
                String c = prompt("Enter choice: ");
                try {
                    switch (c) {
                        case "1" -> addBook();
                        case "2" -> searchBooks();
                        case "3" -> updateBook();
                        case "4" -> removeBook();
                        case "5" -> showBooks();
                        case "6" -> addMember();
                        case "7" -> showMembers();
                        case "8" -> issue();
                        case "9" -> giveBack();
                        case "0" -> {
                            System.out.println("Goodbye!");
                            return;
                        }
                        default -> System.out.println("Invalid choice. Enter 0-9.");
                    }
                } catch (LibraryException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("\nInput ended. Exiting.");
        }
    }

    private static void seedData() {
        try {
            library.addBook("Effective Java", "Joshua Bloch", "Programming", 2);
            library.addBook("Clean Code", "Robert C. Martin", "Programming", 1);
            library.addBook("Wings of Fire", "A.P.J. Abdul Kalam", "Biography", 3);
            library.addMember("Demo Member", "9999999999");
        } catch (LibraryException ignored) {
        }
    }

    private static void menu() {
        System.out.println("\n--- Books ---");
        System.out.println("1. Add book        2. Search books");
        System.out.println("3. Update book     4. Remove book");
        System.out.println("5. Show all books");
        System.out.println("--- Members ---");
        System.out.println("6. Add member      7. Show members");
        System.out.println("--- Circulation ---");
        System.out.println("8. Issue book      9. Return book");
        System.out.println("0. Exit");
    }

    private static String prompt(String msg) {
        System.out.print(msg);
        return in.nextLine().trim();
    }

    private static String required(String label) throws LibraryException {
        String s = prompt(label);
        if (s.isEmpty()) throw new LibraryException(label.replace(": ", "") + " cannot be empty.");
        return s;
    }

    private static int readInt(String label) throws LibraryException {
        try {
            return Integer.parseInt(prompt(label));
        } catch (NumberFormatException e) {
            throw new LibraryException("Please enter a valid whole number.");
        }
    }

    private static void addBook() throws LibraryException {
        String title = required("Title: ");
        String author = required("Author: ");
        String category = required("Category: ");
        int copies = readInt("Copies: ");
        Book b = library.addBook(title, author, category, copies);
        System.out.println("Book added with ID " + b.getId());
    }

    private static void searchBooks() throws LibraryException {
        List<Book> found = library.searchBooks(required("Search (title/author/category): "));
        if (found.isEmpty()) {
            System.out.println("No matching books.");
            return;
        }
        bookHeader();
        found.forEach(System.out::println);
    }

    private static void updateBook() throws LibraryException {
        Book b = library.getBook(readInt("Book ID: "));
        System.out.println("Leave blank to keep current value.");
        String t = prompt("Title [" + b.getTitle() + "]: ");
        String a = prompt("Author [" + b.getAuthor() + "]: ");
        String c = prompt("Category [" + b.getCategory() + "]: ");
        String n = prompt("Total copies [" + b.getTotalCopies() + "]: ");
        if (!n.isEmpty()) {
            try {
                b.setTotalCopies(Integer.parseInt(n));
            } catch (NumberFormatException e) {
                throw new LibraryException("Copies must be a whole number.");
            }
        }
        if (!t.isEmpty()) b.setTitle(t);
        if (!a.isEmpty()) b.setAuthor(a);
        if (!c.isEmpty()) b.setCategory(c);
        System.out.println("Book updated.");
    }

    private static void removeBook() throws LibraryException {
        library.removeBook(readInt("Book ID to remove: "));
        System.out.println("Book removed.");
    }

    private static void showBooks() {
        List<Book> all = library.allBooks();
        if (all.isEmpty()) {
            System.out.println("No books in library.");
            return;
        }
        bookHeader();
        all.forEach(System.out::println);
    }

    private static void bookHeader() {
        System.out.printf("%-5s %-28s %-20s %-14s %s%n", "ID", "Title", "Author", "Category", "Avail/Total");
        System.out.println("-".repeat(80));
    }

    private static void addMember() throws LibraryException {
        String name = required("Member name: ");
        String phone = required("Phone (10 digits): ");
        if (!phone.matches("\\d{10}")) throw new LibraryException("Phone must be exactly 10 digits.");
        Member m = library.addMember(name, phone);
        System.out.println("Member added with ID " + m.getId());
    }

    private static void showMembers() {
        List<Member> all = library.allMembers();
        if (all.isEmpty()) {
            System.out.println("No members.");
            return;
        }
        all.forEach(System.out::println);
    }

    private static void issue() throws LibraryException {
        int bookId = readInt("Book ID: ");
        int memberId = readInt("Member ID: ");
        library.issueBook(bookId, memberId);
        System.out.println("Book issued successfully.");
    }

    private static void giveBack() throws LibraryException {
        int bookId = readInt("Book ID: ");
        int memberId = readInt("Member ID: ");
        library.returnBook(bookId, memberId);
        System.out.println("Book returned successfully.");
    }
}
