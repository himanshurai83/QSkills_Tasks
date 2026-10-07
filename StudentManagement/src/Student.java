/**
 * A student record. Extends Person and adds academic details.
 * Fields are private (encapsulation) and exposed through getters/setters.
 */
public class Student extends Person {
    private int id;
    private String course;
    private int age;
    private double marks;

    public Student(int id, String name, String email, String course, int age, double marks) {
        super(name, email);
        this.id = id;
        this.course = course;
        this.age = age;
        this.marks = marks;
    }

    public int getId() { return id; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public double getMarks() { return marks; }
    public void setMarks(double marks) { this.marks = marks; }

    public String getGrade() {
        if (marks >= 90) return "A+";
        if (marks >= 80) return "A";
        if (marks >= 70) return "B";
        if (marks >= 60) return "C";
        if (marks >= 50) return "D";
        return "F";
    }

    @Override
    public String toString() {
        return String.format("%-5d %-20s %-25s %-15s %-4d %-7.2f %-5s",
                id, getName(), getEmail(), course, age, marks, getGrade());
    }
}
