import java.util.ArrayList;
import java.util.List;

/** A library member. Keeps the list of book IDs currently borrowed. */
public class Member {
    public static final int MAX_BOOKS = 3;

    private final int id;
    private String name;
    private String phone;
    private final List<Integer> borrowedBookIds = new ArrayList<>();

    public Member(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public List<Integer> getBorrowedBookIds() { return borrowedBookIds; }
    public boolean canBorrow() { return borrowedBookIds.size() < MAX_BOOKS; }

    @Override
    public String toString() {
        return String.format("%-5d %-22s %-12s borrowed: %d", id, name, phone, borrowedBookIds.size());
    }
}
