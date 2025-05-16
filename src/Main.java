import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        University university =  new University();

        // Utworzenie kont
        university.addStudent(new Student("144501", "Andrzej Kowalski", "144501@edu.pl", "qwerty"));
        university.addTeacher(new Teacher("15", "Jan Nowak", "jan.nowak@edu.pl", "haslo123"));

        while (true) {
            startMenu(sc, university);
        }
    }

    static void startMenu(Scanner sc, University university) {
        System.out.println("----- SYSTEM ZARZĄDZANIA STUDIAMI -----");
        System.out.println("1. Zaloguj się");
        System.out.println("2. Wyjdź");

        int choice = Integer.parseInt(sc.nextLine());
        switch (choice) {
            case 1 -> loginMenu(sc, university);
            case 2 -> {
                System.out.println("Dziękujemy za skorzystanie z systemu. Do zobaczenia!");
                System.exit(0);
            }
            default ->  {
                System.out.println("Nieprawidłowy wybór");
                return;
            }
        }
    }

    static void loginMenu(Scanner sc, University university) {
        System.out.println("----- ZALOGUJ SIĘ -----");
        System.out.println("Podaj login:");
        String login = sc.nextLine();
        Student student = university.getStudentByLogin(login);
        Teacher teacher = university.getTeacherByLogin(login);
        boolean isAuthenticated;

        if (student != null) {
            System.out.println("Podaj hasło:");
            String password = sc.nextLine();
            isAuthenticated = student.login(password);
            if (isAuthenticated) {
                System.out.println("Zalogowano pomyślnie.");
                while (isAuthenticated) {
                    isAuthenticated = studentMenu(sc, university, student);
                }
            } else {
                System.out.println("Nieprawidłowe hasło.");
            }
        } else if (teacher != null) {
            System.out.println("Podaj hasło: ");
            String password = sc.nextLine();
            isAuthenticated = teacher.login(password);
            if (isAuthenticated) {
                System.out.println("Zalogowano pomyślnie.");
                while (isAuthenticated) {
                    isAuthenticated = teacherMenu(sc, university, teacher);
                }
            } else {
                System.out.println("Nieprawidłowe hasło.");
            }
        } else {
            System.out.println("Nieprawidłowy login.");
        }
    }

    static boolean studentMenu(Scanner sc, University university, Student student) {
        System.out.println("----- MENU STUDENTA -----");
        System.out.println("1. Przedmioty, na które jesteś zapisany");
        System.out.println("2. Przedmioty, na które możesz się zapisać");
        System.out.println("3. Wyloguj się");

        int choice = Integer.parseInt(sc.nextLine());
        switch (choice) {
            case 1 -> {
                // TODO: zapisane kursy
                return true;
            }
            case 2 -> {
                // TODO: dostępne kursy
                return true;
            }
            case 3 -> {
                System.out.println("Wylogowano.");
                return false;
            }
            default -> {
                System.out.println("Nieprawidłowy wybór");
                return true;
            }
        }
    }

    static boolean teacherMenu(Scanner sc, University university, Teacher teacher) {
        System.out.println("----- MENU WYKŁADOWCY -----");
        // TODO: menu wykładowcy
        return false;
    }
}