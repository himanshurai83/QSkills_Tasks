import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Core logic: books, members, issuing and returning. */
public class Library {
    private final Map<Integer, Book> books = new HashMap<>();
    private final Map<Integer, Member> members = new HashMap<>();
    private int nextBookId = 101;
    private int nextMemberId = 1;

    // ---------- Books ----------
    public Book addBook(String title, String author, String category, int copies) throws LibraryException {
        if (copies < 1) throw new LibraryException("Copies must be at least 1.");
        Book b = new Book(nextBookId++, title, author, category, copies);
        books.put(b.getId(), b);
        return b;
    }

    public Book getBook(int id) throws LibraryException {
        Book b = books.get(id);
        if (b == null) throw new LibraryException("No book with ID " + id);
        return b;
    }

    public List<Book> searchBooks(String keyword) {
        String k = keyword.toLowerCase();
        List<Book> result = new ArrayList<>();
        for (Book b : books.values()) {
            if (b.getTitle().toLowerCase().contains(k)
                    || b.getAuthor().toLowerCase().contains(k)
                    || b.getCategory().toLowerCase().contains(k)) {
                result.add(b);
            }
        }
        result.sort((a, c) -> Integer.compare(a.getId(), c.getId()));
        return result;
    }

    public List<Book> allBooks() {
        List<Book> list = new ArrayList<>(books.values());
        list.sort((a, c) -> Integer.compare(a.getId(), c.getId()));
        return list;
    }

    public void removeBook(int id) throws LibraryException {
        Book b = getBook(id);
        if (b.getAvailableCopies() != b.getTotalCopies()) {
            throw new LibraryException("Cannot remove: some copies are currently issued.");
        }
        books.remove(id);
    }

    // ---------- Members ----------
    public Member addMember(String name, String phone) {
        Member m = new Member(nextMemberId++, name, phone);
        members.put(m.getId(), m);
        return m;
    }

    public Member getMember(int id) throws LibraryException {
        Member m = members.get(id);
        if (m == null) throw new LibraryException("No member with ID " + id);
        return m;
    }

    public List<Member> allMembers() {
        List<Member> list = new ArrayList<>(members.values());
        list.sort((a, c) -> Integer.compare(a.getId(), c.getId()));
        return list;
    }

    // ---------- Issue / Return ----------
    public void issueBook(int bookId, int memberId) throws LibraryException {
        Book b = getBook(bookId);
        Member m = getMember(memberId);
        if (!b.isAvailable()) throw new LibraryException("'" + b.getTitle() + "' has no available copies.");
        if (!m.canBorrow()) throw new LibraryException(m.getName() + " already has " + Member.MAX_BOOKS + " books.");
        if (m.getBorrowedBookIds().contains(bookId)) {
            throw new LibraryException(m.getName() + " already borrowed this book.");
        }
        b.issueCopy();
        m.getBorrowedBookIds().add(bookId);
    }

    public void returnBook(int bookId, int memberId) throws LibraryException {
        Book b = getBook(bookId);
        Member m = getMember(memberId);
        if (!m.getBorrowedBookIds().remove(Integer.valueOf(bookId))) {
            throw new LibraryException(m.getName() + " has not borrowed this book.");
        }
        b.returnCopy();
    }
}
