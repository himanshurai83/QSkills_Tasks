import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/** Menu-driven entry point for the Student Management System. */
public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static final StudentService service = new StudentService();

    public static void main(String[] args) {
        System.out.println("=== Student Management System ===");
        try {
            while (true) {
                printMenu();
                String choice = prompt("Enter choice: ");
                switch (choice) {
                    case "1" -> addStudent();
                    case "2" -> updateStudent();
                    case "3" -> deleteStudent();
                    case "4" -> searchStudent();
                    case "5" -> displayAll();
                    case "6" -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please enter 1-6.");
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("\nInput ended. Exiting.");
        }
    }

    private static void printMenu() {
        System.out.println("\n1. Add Student");
        System.out.println("2. Update Student");
        System.out.println("3. Delete Student");
        System.out.println("4. Search Student");
        System.out.println("5. Display All Students");
        System.out.println("6. Exit");
    }

    private static String prompt(String msg) {
        System.out.print(msg);
        return in.nextLine();
    }

    private static void addStudent() {
        try {
            String name = Validator.name(prompt("Name: "));
            String email = Validator.email(prompt("Email: "));
            String course = Validator.course(prompt("Course: "));
            int age = Validator.age(prompt("Age: "));
            double marks = Validator.marks(prompt("Marks (0-100): "));
            Student s = service.add(name, email, course, age, marks);
            System.out.println("Student added with ID " + s.getId());
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateStudent() {
        try {
            int id = Validator.id(prompt("Enter student ID to update: "));
            Student s = service.findById(id);
            System.out.println("Leave a field blank to keep its current value.");

            String name = prompt("Name [" + s.getName() + "]: ");
            String email = prompt("Email [" + s.getEmail() + "]: ");
            String course = prompt("Course [" + s.getCourse() + "]: ");
            String age = prompt("Age [" + s.getAge() + "]: ");
            String marks = prompt("Marks [" + s.getMarks() + "]: ");

            service.update(id,
                    name.isBlank() ? null : Validator.name(name),
                    email.isBlank() ? null : Validator.email(email),
                    course.isBlank() ? null : Validator.course(course),
                    age.isBlank() ? null : Validator.age(age),
                    marks.isBlank() ? null : Validator.marks(marks));
            System.out.println("Student updated.");
        } catch (InvalidInputException | StudentNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteStudent() {
        try {
            int id = Validator.id(prompt("Enter student ID to delete: "));
            service.delete(id);
            System.out.println("Student deleted.");
        } catch (InvalidInputException | StudentNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void searchStudent() {
        System.out.println("1. Search by ID\n2. Search by name");
        String c = prompt("Choice: ");
        try {
            if (c.equals("1")) {
                int id = Validator.id(prompt("Enter ID: "));
                printHeader();
                System.out.println(service.findById(id));
            } else if (c.equals("2")) {
                String kw = prompt("Enter name or part of name: ");
                List<Student> found = service.searchByName(kw);
                if (found.isEmpty()) {
                    System.out.println("No matching students.");
                } else {
                    printHeader();
                    found.forEach(System.out::println);
                }
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (InvalidInputException | StudentNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void displayAll() {
        if (service.isEmpty()) {
            System.out.println("No student records yet.");
            return;
        }
        printHeader();
        service.getAll().forEach(System.out::println);
    }

    private static void printHeader() {
        System.out.printf("%-5s %-20s %-25s %-15s %-4s %-7s %-5s%n",
                "ID", "Name", "Email", "Course", "Age", "Marks", "Grade");
        System.out.println("-".repeat(85));
    }
}
