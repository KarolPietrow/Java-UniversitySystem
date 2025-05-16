import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        University university =  new University();

        // Utworzenie kont studentów
        Student student = new Student("144501", "Jan Kowalski", "144501@edu.pl", "qwerty");
        // Utworzenie kont wykładowców
        Teacher teacher = new Teacher("15", "Andrzej Nowak", "jan.nowak@edu.pl", "haslo123");

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
        Student student = university.getStudentById(login);
        Teacher teacher = university.getTeacherById(login);
        if (student != null) {
            System.out.println("Podaj hasło:");
            String password = sc.nextLine();
            if (student.login(password)) {
                System.out.println("Zalogowano pomyślnie.");
            } else {
                System.out.println("Nieprawidłowe hasło.");
            }
        } else if (teacher != null) {
            System.out.println("Podaj hasło: ");
            String password = sc.nextLine();
            if (teacher.login(password)) {
                System.out.println("Zalogowano pomyślnie.");
            } else {
                System.out.println("Nieprawidłowe hasło.");
            }
        } else {
            System.out.println("Nieprawidłowy login.");
        }
    }
}