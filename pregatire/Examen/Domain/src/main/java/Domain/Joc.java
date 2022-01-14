package Domain;

import java.io.Serializable;
import java.util.Objects;

public class Joc implements Serializable {

    @Override
    public String toString() {
        return "Joc{" +
                "id=" + id +
                ", runda=" + runda +
                ", puncte=" + puncte +
                ", jucator=" + jucator +
                ", cuvant='" + cuvant + '\'' +
                ", caracteristici='" + caracteristici + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Joc)) return false;
        Joc joc = (Joc) o;
        return getId() == joc.getId() && getRunda() == joc.getRunda() && getPuncte() == joc.getPuncte() && getJucator() == joc.getJucator() && getCuvant().equals(joc.getCuvant()) && getCaracteristici().equals(joc.getCaracteristici());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRunda(), getPuncte(), getJucator(), getCuvant(), getCaracteristici());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRunda() {
        return runda;
    }

    public void setRunda(int runda) {
        this.runda = runda;
    }

    public int getPuncte() {
        return puncte;
    }

    public void setPuncte(int puncte) {
        this.puncte = puncte;
    }

    public int getJucator() {
        return jucator;
    }

    public void setJucator(int jucator) {
        this.jucator = jucator;
    }

    public String getCuvant() {
        return cuvant;
    }

    public void setCuvant(String cuvant) {
        this.cuvant = cuvant;
    }

    public String getCaracteristici() {
        return caracteristici;
    }

    public void setCaracteristici(String caracteristici) {
        this.caracteristici = caracteristici;
    }

    public Joc(){

    }

    public Joc(int jucator, String cuvant, String caracteristici) {
        this.jucator = jucator;
        this.cuvant = cuvant;
        this.caracteristici = caracteristici;
    }

    private int id,runda,puncte,jucator;
    private String cuvant,caracteristici;
}
