# Library Management System (Java, Console)

QSkill Java Development Internship, Slab 1, Task 2.

Manages books, members, and issuing/returning of books.

## Features
- Add, search (title/author/category), update, remove and list books
- Add and list members (10-digit phone validation)
- Issue and return books with availability tracking (available/total copies)
- Rules: a member can hold at most 3 books, cannot borrow the same book twice, and a book with issued copies cannot be removed
- Three sample books and one member are preloaded for quick testing

## Concepts used
- Classes and objects: `Book`, `Member`, `Library`
- Collections: `HashMap<Integer, Book>`, `HashMap<Integer, Member>`, `ArrayList`
- Custom checked exception: `LibraryException`
- Encapsulation: issue/return methods on `Book` are package-private so only `Library` can change availability

## Run
```
mkdir out
javac -d out src/*.java
java -cp out Main
```
Requires JDK 11 or newer (tested on JDK 21). Data is held in memory.
