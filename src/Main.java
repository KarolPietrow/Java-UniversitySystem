import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        University university =  new University();

        // Utworzenie kont
        Teacher teacher1 = new Teacher("15", "Jan Nowak", "jan.nowak@edu.pl", "haslo123");
        university.addTeacher(teacher1);

        university.addStudent(new Student("144501", "Andrzej Kowalski", "144501@edu.pl", "qwerty"));
//        university.addTeacher(new Teacher("15", "Jan Nowak", "jan.nowak@edu.pl", "haslo123"));
        university.addCourse(new Course("Inżynieria oprogramowania", "Przedmiot, na którym uczymy się obsługi StarUMLa", teacher1));

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
                List<Course> courses = student.getEnrolledCourses();
                if (!courses.isEmpty()) {
                    System.out.println("TWOJE PRZEDMIOTY: ");
                    courses.forEach( c ->
                            System.out.println(courses.indexOf(c)+1 + ". " + c.getTeacher().getName() + " - " + c.getName() + " - " + c.getDescription())
                    );
                    System.out.println("---------------");
                    System.out.println("Wpisz numer przedmiotu, aby wyświetlić szczegóły, lub 0, aby wyjść.");
                    int choice2 = Integer.parseInt(sc.nextLine());
                    if (choice2 == 0) {
                        return true;
                    } else {
//                        studentCourseMenu(sc, university, courses.get(choice2-1));
                    }
                } else {
                    System.out.println("Nie zarządzasz żadnymi przedimotami.");
                }
                return true;
            }
            case 2 -> {
                List<Course> all = university.getAllCourses();
                List<Course> notEnrolled = new ArrayList<>();
                for (Course c : all) {
                    if (!student.getEnrolledCourses().contains(c)) {
                        notEnrolled.add(c);
                    }
                }
                if (!notEnrolled.isEmpty()) {
                    System.out.println("----- DOSTĘPNE PRZEDMIOTY -----");
                    notEnrolled.forEach( c ->
                            System.out.println(notEnrolled.indexOf(c)+1 + ". " + c.getName() + " - " + c.getDescription())
                    );
                    System.out.println("---------------");
                    System.out.println("Wpisz numer przedmiotu, na który chcesz się zapisać, lub 0, aby wyjść.");
                    int choice2 = Integer.parseInt(sc.nextLine());
                    if (choice2 == 0) {
                        return true;
                    } else {
                        enrollCourseMenu(sc, university, student, notEnrolled.get(choice2-1));
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

    static void enrollCourseMenu(Scanner sc, University university, Student student, Course course) {
        System.out.println("----- UWAGA -----");
        System.out.println("Czy na pewno chcesz zapisać się na przedmiot " + course.getName() + "?");
        System.out.println("Y - tak");
        System.out.println("N - nie");
        String choice = sc.nextLine().trim();
        if (choice.equalsIgnoreCase("Y")) {
            student.enroll(course);
        }
    }

    static boolean teacherMenu(Scanner sc, University university, Teacher teacher) {
        System.out.println("----- MENU WYKŁADOWCY -----");
        System.out.println("1. Utwórz nowy przedmiot");
        System.out.println("2. Wyświetl swoje przedmioty");
        System.out.println("3. Wyloguj się");

        int choice = Integer.parseInt(sc.nextLine());
        switch (choice) {
            case 1 -> {
                System.out.println("Podaj nazwę przedmiotu:");
                String name = sc.nextLine();
                System.out.println("Podaj krótki opis przedmiotu:");
                String desc = sc.nextLine();
                university.addCourse(new Course(name, desc, teacher));
                System.out.println("Przedmiot utworzony.");
                return true;
            }
            case 2 -> {
                List<Course> courses = university.getCoursesByTeacher(teacher);
                if (!courses.isEmpty()) {
                    System.out.println("TWOJE PRZEDMIOTY: ");
                    courses.forEach( c ->
                            System.out.println(courses.indexOf(c)+1 + ". " + c.getName() + " - " + c.getDescription())
                    );
                    System.out.println("---------------");
                    System.out.println("Wpisz numer przedmiotu, aby wyświetlić szczegóły, lub 0, aby wyjść.");
                    int choice2 = Integer.parseInt(sc.nextLine());
                    if (choice2 == 0) {
                        return true;
                    } else {
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
        System.out.println("Lista dodanych materiałów: " + course.getListOfFiles().toArray().length);
        System.out.println("---------------");
        System.out.println("1. Wyświetl listę zapisanych studentów");
        System.out.println("2. Wyświetl listę materiałów");

        int choice = Integer.parseInt(sc.nextLine());
        switch (choice) {
            case 1 -> {
                List<Student> students = course.getEnrolledStudents();
                for (int i = 0; i < students.toArray().length; i++) {
                    System.out.println(i + ". " + students.get(i).getName());
                }
            }
            case 2 -> {
                // TODO
            }
            default -> {
                break;
            }
        }
    }
}