package Domain;

import java.io.Serializable;
import java.util.Objects;

public class Persoana implements Serializable {
    private int id;
    private String parola;
    private String email;
    private boolean participa;

    public boolean isParticipa() {
        return participa;
    }

    public void setParticipa(boolean participa) {
        this.participa = participa;
    }

    @Override
    public String toString() {
        return "Domain.Persoana{" +
                "id=" + id +
                ", nume='" + parola + '\'' +
                ", email='" + email + '\'' +
                ", participa = "+participa+'\''+
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
        if (!(o instanceof Persoana)) return false;
        Persoana persoana = (Persoana) o;
        return id == persoana.id && parola.equals(persoana.parola) && email.equals(persoana.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parola, email);
    }

    public Persoana() {
    }

    public Persoana(String nume, String email) {
        this.parola = nume;
        this.email = email;
    }
}
