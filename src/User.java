import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public abstract class User {
    String id;
    String name;
    String login;
    String password;
    boolean authenticated;

    public User(String id, String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = hashPassword(password);
        this.authenticated = false;
    }

    boolean login(String password) {
        if (!authenticated) {
            if (Objects.equals(this.password, hashPassword(password))) {
                authenticated = true;
                return true;
            } else {
                System.out.println("Niepoprawne dane logowania");
                return false;
            }
        } else {
            System.out.println("Błąd: Użytkownik jest już zalogowany.");
            return true;
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not supported", e);
        }
    }
}
