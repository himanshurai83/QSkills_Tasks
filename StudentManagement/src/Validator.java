/** Static helpers for validating user input. */
public final class Validator {
    private Validator() {}

    public static String name(String s) throws InvalidInputException {
        if (s == null || !s.trim().matches("[A-Za-z][A-Za-z .'-]{1,49}")) {
            throw new InvalidInputException("Name must be 2-50 letters (spaces, . ' - allowed).");
        }
        return s.trim();
    }

    public static String email(String s) throws InvalidInputException {
        if (s == null || !s.trim().matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
            throw new InvalidInputException("Invalid email format (example: abc@mail.com).");
        }
        return s.trim();
    }

    public static String course(String s) throws InvalidInputException {
        if (s == null || s.trim().isEmpty() || s.trim().length() > 30) {
            throw new InvalidInputException("Course must be 1-30 characters.");
        }
        return s.trim();
    }

    public static int age(String s) throws InvalidInputException {
        try {
            int a = Integer.parseInt(s.trim());
            if (a < 5 || a > 100) throw new InvalidInputException("Age must be between 5 and 100.");
            return a;
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Age must be a whole number.");
        }
    }

    public static double marks(String s) throws InvalidInputException {
        try {
            double m = Double.parseDouble(s.trim());
            if (m < 0 || m > 100) throw new InvalidInputException("Marks must be between 0 and 100.");
            return m;
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Marks must be a number.");
        }
    }

    public static int id(String s) throws InvalidInputException {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("ID must be a whole number.");
        }
    }
}
