/** Thrown when a student with the requested ID does not exist. */
public class StudentNotFoundException extends Exception {
    public StudentNotFoundException(int id) {
        super("No student found with ID " + id);
    }
}
