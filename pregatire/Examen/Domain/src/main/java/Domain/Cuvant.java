package Domain;

import java.io.Serializable;
import java.util.Objects;

public class Cuvant implements Serializable {

    public Cuvant(){}

    private int id;
    private String litere;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLitere() {
        return litere;
    }

    public void setLitere(String litere) {
        this.litere = litere;
    }

    public String getCaracteristici() {
        return caracteristici;
    }

    public void setCaracteristici(String caracteristici) {
        this.caracteristici = caracteristici;
    }

    @Override
    public String toString() {
        return "Cuvant{" +
                "id=" + id +
                ", litere='" + litere + '\'' +
                ", caracteristici='" + caracteristici + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cuvant)) return false;
        Cuvant cuvant = (Cuvant) o;
        return id == cuvant.id && litere.equals(cuvant.litere) && caracteristici.equals(cuvant.caracteristici);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, litere, caracteristici);
    }

    private String caracteristici;

    public Cuvant(String litere, String caracteristici) {
        this.litere = litere;
        this.caracteristici = caracteristici;
    }
}
