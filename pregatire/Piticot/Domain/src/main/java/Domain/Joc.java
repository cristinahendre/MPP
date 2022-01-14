package Domain;

import java.io.Serializable;
import java.util.Objects;

public class Joc implements Serializable {

    public Joc(){}

    @Override
    public String toString() {
        return "Joc{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", traseu='" + traseu + '\'' +
                ", pozitii='" + pozitii + '\'' +
                ", numere='" + numere + '\'' +
                '}';
    }

    private int id;
    private String user;
    private String traseu;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Joc)) return false;
        Joc joc = (Joc) o;
        return getId() == joc.getId() && getUser().equals(joc.getUser()) && getTraseu().equals(joc.getTraseu()) && getPozitii().equals(joc.getPozitii()) && getNumere().equals(joc.getNumere());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getTraseu(), getPozitii(), getNumere());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTraseu() {
        return traseu;
    }

    public void setTraseu(String traseu) {
        this.traseu = traseu;
    }

    public String getPozitii() {
        return pozitii;
    }

    public void setPozitii(String pozitii) {
        this.pozitii = pozitii;
    }

    public String getNumere() {
        return numere;
    }

    public void setNumere(String numere) {
        this.numere = numere;
    }

    public Joc(String user, String traseu, String pozitii, String numere) {
        this.user = user;
        this.traseu = traseu;
        this.pozitii = pozitii;
        this.numere = numere;
    }

    private String pozitii;
    private String numere;

}
