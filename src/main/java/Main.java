import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        University university = StateManager.load();

//        Utworzenie kont
        Teacher teacher1 = new Teacher("17", "Ewelina Kowalczyk", "ewelina.kowalczyk@edu.pl", "haslo123");
//        Teacher teacher2 = new Teacher("16", "Stanisław Wiśniewski", "staniwslaw.wisniewski@edu.pl", "studia");
        university.addTeacher(teacher1);
//        university.addTeacher(teacher2);
//
//        university.addStudent(new Student("144503", "Marta Zielińska", "144503@edu.pl", "qwerty2"));
//        university.addCourse(new Course("Programowanie obiektowe 2025", "Nauka OOP w języku Java", teacher1));
//        university.addCourse(new Course("ASyKo25", "Architektury Systemów Komputerowych 2025", teacher2));

        StateManager.save(university);

        while (true) {
            startMenu(sc, university);
        }
    }

    private static int getIntInput(Scanner sc) {
        int input;
        while (true) {
            try {
                input = Integer.parseInt(sc.nextLine());
                return input;
            } catch (NumberFormatException e) {
                System.out.println("Nieprawidłowy format. Proszę wprowadzić liczbę.");
            }
        }
    }


    static void startMenu(Scanner sc, University university) {
        System.out.println("----- SYSTEM ZARZĄDZANIA STUDIAMI -----");
        System.out.println("1. Zaloguj się");
        System.out.println("2. Wyjdź");

//        int choice = Integer.parseInt(sc.nextLine());
        int choice = getIntInput(sc);

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

        int choice = getIntInput(sc);
        switch (choice) {
            case 1 -> {
                List<Course> courses = university.getCoursesByStudent(student);
                if (!courses.isEmpty()) {
                    System.out.println("TWOJE PRZEDMIOTY: ");
                    for (int i = 0; i < courses.size(); i++) {
                        Course c = courses.get(i);
                        System.out.println(i+1 + ". " + c.getTeacher().getName() + " - " + c.getName() + " - " + c.getDescription());
                    }
                    System.out.println("---------------");
                    System.out.println("Wpisz numer przedmiotu, aby wyświetlić szczegóły, lub 0, aby wyjść.");
                    int choice2 = getIntInput(sc);
                    if (choice2 == 0) {
                        return true;
                    } else if (choice2 <= courses.size()) {
                        studentCourseMenu(sc, university, student, courses.get(choice2-1));
                    }
                } else {
                    System.out.println("Nie jesteś zapisany na żaden przedmiot.");
                }
                return true;
            }
            case 2 -> {
                List<Course> all = university.getAllCourses();
                List<Course> notEnrolled = new ArrayList<>();
                for (Course c : all) {
                    if (!university.getCoursesByStudent(student).contains(c)) {
                        notEnrolled.add(c);
                    }
                }
                if (!notEnrolled.isEmpty()) {
                    System.out.println("----- DOSTĘPNE PRZEDMIOTY -----");
                    for (int i = 0; i < notEnrolled.size(); i++) {
                        Course c = notEnrolled.get(i);
                        System.out.println(i+1 + ". " + c.getTeacher().getName() + " - " + c.getName() + " - " + c.getDescription());
                    }
                    System.out.println("---------------");
                    System.out.println("Wpisz numer przedmiotu, na który chcesz się zapisać, lub 0, aby wyjść.");
                    int choice2 = getIntInput(sc);
                    if (choice2 == 0) {
                        return true;
                    } else if (choice2 <= notEnrolled.size()) {
                        studentAddToCourseMenu(sc, university, student, notEnrolled.get(choice2-1));
                    }
                } else {
                    System.out.println("Brak dostępnych przedmiotów do zapisania.");
                }
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

    static void studentAddToCourseMenu(Scanner sc, University university, Student student, Course course) {
        System.out.println("----- UWAGA -----");
        System.out.println("Czy na pewno chcesz zapisać się na przedmiot " + course.getName() + "?");
        System.out.println("Y - tak");
        System.out.println("N - nie");
        String choice = sc.nextLine().trim();
        if (choice.equalsIgnoreCase("Y")) {
            course.addStudent(student);
            StateManager.save(university);
        }
    }

    static void studentRemoveFromCourseMenu(Scanner sc, University university, Student student, Course course) {
        System.out.println("----- UWAGA -----");
        System.out.println("Czy na pewno chcesz wypisać się z przedmiotu " + course.getName() + "? Nie będziesz miał dostępu do materiałów do czasu ponownego zapisu.");
        System.out.println("Y - tak");
        System.out.println("N - nie");
        String choice = sc.nextLine().trim();
        if (choice.equalsIgnoreCase("Y")) {
            course.removeStudent(student);
            StateManager.save(university);
        }
        System.out.println("Wypisano z przedmiotu.");
    }

    static void studentCourseMenu(Scanner sc, University university, Student student, Course course) {
        System.out.println("----- SZCZEGÓŁY PRZEDMIOTU -----");
        System.out.println("Nazwa: " + course.getName());
        System.out.println("Opis: " + course.getDescription());
        System.out.println("Wykładowca: " + course.getTeacher().getName());
        System.out.println("Liczba zapisanych studentów: " + course.getEnrolledStudents().toArray().length);
        System.out.println("Lista dodanych materiałów: " + course.getMaterials().toArray().length);
        System.out.println("---------------");
        System.out.println("1. Wyświetl listę materiałów");
        System.out.println("2. Wypisz się z przedmiotu");
        System.out.println("3. Powrót");

        int choice = getIntInput(sc);
        switch (choice) {
            case 1 -> {
                studentMaterialList(sc, course);
            }
            case 2 -> {
                studentRemoveFromCourseMenu(sc, university, student, course);
                StateManager.save(university);
            }
            case 3 -> {
                break;
            }
            default -> {
                break;
            }
        }
    }

    static void studentMaterialList(Scanner sc, Course course) {
        System.out.println("Materiały:");
        List<Material> mats = course.getMaterials();
        if (mats.isEmpty()) {
            System.out.println("Brak materiałów.");
        } else {
            for (int i = 0; i < mats.size(); i++) {
                System.out.printf("%d. %s%n", i+1, mats.get(i));
            }
            System.out.print("Wybierz numer materiału, by go wyświetlić/pobrać, lub 0, by wrócić: ");
            int mChoice = getIntInput(sc);
            if (mChoice > 0 && mChoice <= mats.size()) {
                Material m = mats.get(mChoice - 1);
                if (m.getFilePath().startsWith("http://") || m.getFilePath().startsWith("https://")) {
                    System.out.println("1. Otwórz link");
                    System.out.println("0. Powrót");
                    int action = getIntInput(sc);
                    if (action == 1) {
                        m.displayContent();
                    }
                } else if (m.getFilePath().toLowerCase().endsWith(".txt")) {
                    System.out.println("1. Podgląd");
                    System.out.println("2. Pobierz plik");
                    System.out.println("0. Powrót");
                    int action = getIntInput(sc);
                    switch (action) {
                        case 1 -> m.displayContent();
                        case 2 -> {
                            System.out.print("Podaj ścieżkę katalogu docelowego: ");
                            String dir = sc.nextLine().trim();
                            m.download(dir);
                        }
                        default -> {

                        }
                    }
                } else {
                    System.out.println("1. Pobierz plik");
                    System.out.println("0. Powrót");
                    int action = getIntInput(sc);
                    if (action == 1) {
                        System.out.print("Podaj ścieżkę katalogu docelowego: ");
                        String dir = sc.nextLine().trim();
                        m.download(dir);
                    }
                }
            }
        }
    }

    static boolean teacherMenu(Scanner sc, University university, Teacher teacher) {
        System.out.println("----- MENU WYKŁADOWCY -----");
        System.out.println("1. Utwórz nowy przedmiot");
        System.out.println("2. Wyświetl swoje przedmioty");
        System.out.println("3. Wyloguj się");

        int choice = getIntInput(sc);
        switch (choice) {
            case 1 -> {
                System.out.println("Podaj nazwę przedmiotu:");
                String name = sc.nextLine();
                System.out.println("Podaj krótki opis przedmiotu:");
                String desc = sc.nextLine();
                university.addCourse(new Course(name, desc, teacher));
                System.out.println("Przedmiot utworzony.");
                StateManager.save(university);
                return true;
            }
            case 2 -> {
                List<Course> courses = university.getCoursesByTeacher(teacher);
                if (!courses.isEmpty()) {
                    System.out.println("TWOJE PRZEDMIOTY: ");
                    for (int i = 0; i < courses.size(); i++) {
                        Course c = courses.get(i);
                        System.out.println(i+1 + ". " + c.getName() + " - " + c.getDescription());
                    }
                    System.out.println("---------------");
                    System.out.println("Wpisz numer przedmiotu, aby wyświetlić szczegóły, lub 0, aby wyjść.");
                    int choice2 = getIntInput(sc);
                    if (choice2 == 0) {
                        return true;
                    } else if (choice2 <= courses.size()) {
                        teacherCourseMenu(sc, university, courses.get(choice2-1));
                    }
                } else {
                    System.out.println("Nie zarządzasz żadnymi przedimotami.");
                }
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

    static void teacherCourseMenu(Scanner sc, University university, Course course) {
        System.out.println("----- SZCZEGÓŁY PRZEDMIOTU -----");
        System.out.println("Nazwa: " + course.getName());
        System.out.println("Opis: " + course.getDescription());
        System.out.println("Wykładowca: " + course.getTeacher().getName());
        System.out.println("Liczba zapisanych studentów: " + course.getEnrolledStudents().toArray().length);
        System.out.println("Lista dodanych materiałów: " + course.getMaterials().toArray().length);
        System.out.println("---------------");
        System.out.println("1. Wyświetl listę zapisanych studentów");
        System.out.println("2. Dodaj studenta");
        System.out.println("3. Wyświetl listę materiałów");
        System.out.println("4. Dodaj materiał");
        System.out.println("5. Usuń przedmiot");
        System.out.println("0. Powrót");

        int choice = getIntInput(sc);
        switch (choice) {
            case 1 -> {
                List<Student> students = course.getEnrolledStudents();
                if (!students.isEmpty()) {
                    for (int i = 0; i < students.toArray().length; i++) {
                        System.out.println(i + 1 + ". " + students.get(i).getName());
                    }
                } else {
                    System.out.println("Brak studentów zapisanych na przedmiot");
                }
            }
            case 2 -> {
                System.out.println("Podaj ID studenta, którego chcesz dodać, lub 0 aby anulować:");
                int choice2 = getIntInput(sc);
                if (choice2 != 0) {
                    Student student = university.getStudentById(String.valueOf(choice2));
                    if (student != null) {
                        course.addStudent(student);
                        System.out.println(student.getName() + " został dodany do przedmiotu");
                        StateManager.save(university);
                    } else {
                        System.out.println("Niepoprawne ID studenta.");
                    }
                }
            }
            case 3 -> {
                teacherMaterialList(sc, university, course);
            }
            case 4 -> {
                System.out.print("Podaj tytuł materiału: ");
                String title = sc.nextLine().trim();
                System.out.print("Podaj pełną ścieżkę do pliku, lub link (https://):");
                String source = sc.nextLine().trim();
                try {
                    Material m = new Material(title, source);
                    course.addMaterial(m);
                    System.out.println("Materiał dodany: " + m.getFilePath());
                    StateManager.save(university);
                } catch (IOException e) {
                    System.out.println("Nie udało się dodać materiału: " + e.getMessage());
                }
                break;
            }
            case 5 -> {
                System.out.println("----- UWAGA !!! -----");
                System.out.print("Czy na pewno chcesz USUNĄĆ cały przedmiot \""
                        + course.getName() + "\"? Tej czynności NIE MOŻNA cofnąć! (Y/N): ");
                String conf = sc.nextLine().trim();
                if (conf.equalsIgnoreCase("Y")) {
                    boolean removed = university.removeCourse(course);
                    if (removed) {
                        System.out.println("Przedmiot usunięty pomyślnie.");
                        StateManager.save(university);
                    } else {
                        System.out.println("Błąd: nie udało się usunąć przedmiotu.");
                    }
                    return;
                } else {
                    System.out.println("Anulowano usuwanie przedmiotu.");
                }
            }
            case 0 -> {
                return;
            }
            default -> {
                System.out.println("Nieprawidłowy wybór.");
            }
        }
    }

    static void teacherMaterialList(Scanner sc, University university, Course course) {
        System.out.println("Materiały:");
        List<Material> mats = course.getMaterials();
        if (mats.isEmpty()) {
            System.out.println("Brak materiałów.");
        } else {
            for (int i = 0; i < mats.size(); i++) {
                System.out.printf("%d. %s%n", i+1, mats.get(i));
            }
            System.out.print("Wybierz numer materiału, by go wyświetlić/pobrać, lub 0, by wrócić: ");
            int mChoice = getIntInput(sc);
            if (mChoice > 0 && mChoice <= mats.size()) {
                Material m = mats.get(mChoice - 1);
                if (m.getFilePath().startsWith("http://") || m.getFilePath().startsWith("https://")) {
                    System.out.println("1. Otwórz link");
                    System.out.println("2. Usuń linka");
                    System.out.println("0. Powrót");
                    int action = getIntInput(sc);
                    switch (action) {
                        case 1 -> m.displayContent();
                        case 2 -> {
                            System.out.print("Czy na pewno chcesz usunąć link \""
                                    + m.getTitle() + "\"? Tej czynności NIE MOŻNA cofnąć! (Y/N):");
                            String conf = sc.nextLine().trim();
                            if (conf.equalsIgnoreCase("Y")) {
                                if (m.delete()) {
                                    course.getMaterials().remove(m);
                                    System.out.println("Link usunięty pomyślnie.");
                                    StateManager.save(university);
                                } else {
                                    System.out.println("Nie udało się usunąć linka: " + m.getFilePath());
                                }
                            } else {
                                System.out.println("Anulowano usuwanie.");
                            }
                        }
                        default -> {

                        }
                    }
                } else if (m.getFilePath().toLowerCase().endsWith(".txt")) {
                    System.out.println("1. Podgląd");
                    System.out.println("2. Pobierz plik");
                    System.out.println("3. Usuń plik");
                    System.out.println("0. Powrót");
                    int action = getIntInput(sc);
                    switch (action) {
                        case 1 -> m.displayContent();
                        case 2 -> {
                            System.out.print("Podaj katalog docelowy (pełna ścieżka): ");
                            String dir = sc.nextLine().trim();
                            m.download(dir);
                        }
                        case 3 -> {
                            System.out.print("Czy na pewno chcesz usunąć materiał \""
                                    + m.getTitle() + "\"? Tej czynności NIE MOŻNA cofnąć! (Y/N):");
                            String conf = sc.nextLine().trim();
                            if (conf.equalsIgnoreCase("Y")) {
                                if (m.delete()) {
                                    course.getMaterials().remove(m);
                                    System.out.println("Materiał usunięty pomyślnie.");
                                    StateManager.save(university);
                                } else {
                                    System.out.println("Nie udało się usunąć pliku: " + m.getFilePath());
                                }
                            } else {
                                System.out.println("Anulowano usuwanie.");
                            }
                        }
                        default -> {

                        }
                    }
                } else {
                    System.out.println("1. Pobierz plik");
                    System.out.println("2. Usuń plik");
                    System.out.println("0. Powrót");
                    int action = getIntInput(sc);
                    switch (action) {
                        case 1 -> {
                            System.out.print("Podaj katalog docelowy (pełna ścieżka): ");
                            String dir = sc.nextLine().trim();
                            m.download(dir);
                        }
                        case 2 -> {
                            System.out.print("Czy na pewno chcesz usunąć materiał \""
                                    + m.getTitle() + "\"? Tej czynności NIE MOŻNA cofnąć! (Y/N):");
                            String conf = sc.nextLine().trim();
                            if (conf.equalsIgnoreCase("Y")) {
                                if (m.delete()) {
                                    course.getMaterials().remove(m);
                                    System.out.println("Materiał usunięty pomyślnie.");
                                    StateManager.save(university);
                                } else {
                                    System.out.println("Nie udało się usunąć pliku: " + m.getFilePath());
                                }
                            } else {
                                System.out.println("Anulowano usuwanie.");
                            }
                        }
                        default -> {

                        }
                    }
                }
            }
        }
    }
}