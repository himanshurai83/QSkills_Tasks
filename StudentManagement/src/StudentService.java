import java.util.ArrayList;
import java.util.List;

/** Business logic: stores students in an ArrayList and performs CRUD operations. */
public class StudentService {
    private final List<Student> students = new ArrayList<>();
    private int nextId = 1;

    public Student add(String name, String email, String course, int age, double marks)
            throws InvalidInputException {
        for (Student s : students) {
            if (s.getEmail().equalsIgnoreCase(email)) {
                throw new InvalidInputException("A student with this email already exists.");
            }
        }
        Student student = new Student(nextId++, name, email, course, age, marks);
        students.add(student);
        return student;
    }

    public Student findById(int id) throws StudentNotFoundException {
        for (Student s : students) {
            if (s.getId() == id) return s;
        }
        throw new StudentNotFoundException(id);
    }

    public List<Student> searchByName(String keyword) {
        List<Student> result = new ArrayList<>();
        String k = keyword.toLowerCase();
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(k)) result.add(s);
        }
        return result;
    }

    public void update(int id, String name, String email, String course, Integer age, Double marks)
            throws StudentNotFoundException {
        Student s = findById(id);
        if (name != null) s.setName(name);
        if (email != null) s.setEmail(email);
        if (course != null) s.setCourse(course);
        if (age != null) s.setAge(age);
        if (marks != null) s.setMarks(marks);
    }

    public void delete(int id) throws StudentNotFoundException {
        students.remove(findById(id));
    }

    public List<Student> getAll() {
        return new ArrayList<>(students);
    }

    public boolean isEmpty() {
        return students.isEmpty();
    }
}
