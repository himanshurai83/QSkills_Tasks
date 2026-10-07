# Student Management System (Java, Console)

QSkill Java Development Internship, Slab 1, Task 1.

A menu-driven console app to add, update, delete, search and display student records.

## Concepts used
- **Inheritance**: `Student extends Person`
- **Encapsulation**: private fields with getters/setters
- **Collections**: records stored in an `ArrayList<Student>`
- **Validation**: `Validator` checks name, email, age, marks and ID
- **Exception handling**: custom `InvalidInputException` and `StudentNotFoundException`
- **Logical components**: model (`Person`, `Student`), service (`StudentService`), validation (`Validator`), UI (`Main`)

## Run
```
mkdir out
javac -d out src/*.java
java -cp out Main
```
Requires JDK 11 or newer (tested on JDK 21).

## Menu
1. Add Student  2. Update Student (blank keeps old value)  3. Delete Student
4. Search (by ID or name)  5. Display All  6. Exit

Grades are computed from marks (A+ at 90 and above, down to F below 50).
Records are held in memory and cleared when the program exits.
