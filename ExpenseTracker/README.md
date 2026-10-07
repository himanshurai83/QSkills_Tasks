# Expense Tracker (Java, Console)

QSkill Java Development Internship, Slab 1, Task 3.

Record daily expenses, view totals and category-wise spending. Data is saved to a CSV file, so it is still there next time you run the program.

## Features
- Add, update, delete and view expenses (amount, category, date, description)
- Total spending and category-wise summary (case-insensitive categories)
- Monthly total (`YYYY-MM`)
- Persistent storage in `expenses.csv` (or a path you pass as the first argument)
- Corrupted lines in the file are skipped with a message instead of crashing

## Concepts used
- Collections: `ArrayList<Expense>`, `TreeMap` for category totals
- File handling: `java.nio.file.Files`
- `LocalDate` for dates
- Validation (positive amount, valid date, non-empty category) and a custom `ExpenseException`

## Run
```
mkdir out
javac -d out src/*.java
java -cp out Main            # uses expenses.csv in current folder
java -cp out Main my.csv     # custom data file
```
Requires JDK 11 or newer (tested on JDK 21).
