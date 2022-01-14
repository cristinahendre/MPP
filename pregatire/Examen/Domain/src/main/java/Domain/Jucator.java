package Domain;

import java.io.Serializable;
import java.util.Objects;

public class Jucator implements Serializable {
    private int id;
    private String parola;
    private String email;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Domain.Jucator{" +
                "id=" + id +
                ", nume='" + parola + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jucator)) return false;
        Jucator persoana = (Jucator) o;
        return id == persoana.id && parola.equals(persoana.parola) && email.equals(persoana.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parola, email);
    }

    public Jucator() {
    }

    public Jucator(String nume, String email) {
        this.parola = nume;
        this.email = email;
    }
}
