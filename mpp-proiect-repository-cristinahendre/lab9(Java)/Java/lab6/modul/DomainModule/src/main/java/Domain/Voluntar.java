package Domain;

import java.io.Serializable;
import java.util.Objects;

public class Voluntar  implements Serializable {


    public Voluntar(){}
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String nume;
    private String prenume;
    private String parola;
    private String email;

    @Override
    public String toString() {
        return "Voluntar{" +
                " id = "+ this.getId()+" "+
                "nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", parola='" + parola + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }


    public Voluntar(String nume, String prenume, String email, String parola) {
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.parola = parola;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Voluntar)) return false;
        Voluntar voluntar = (Voluntar) o;
        return Objects.equals(nume, voluntar.nume) && Objects.equals(prenume, voluntar.prenume) && email.equals(voluntar.email) && Objects.equals(parola, voluntar.parola);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, prenume, email, parola);
    }
}
