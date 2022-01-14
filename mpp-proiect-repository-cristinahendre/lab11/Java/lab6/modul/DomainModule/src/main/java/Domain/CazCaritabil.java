package Domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class CazCaritabil extends Entity<Integer> implements Serializable {


    private String nume;

    private int suma_donata;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CazCaritabil)) return false;
        CazCaritabil that = (CazCaritabil) o;
        return getSuma_donata() == that.getSuma_donata() && getNume().equals(that.getNume());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNume(), getSuma_donata());
    }

    public String getNume() {
        return nume;
    }

    @Override
    public String toString() {
        return "CazCaritabil{" +
                "id= "+ this.getId()+" "+
                "nume='" + nume + '\'' +
                ", suma_donata=" + suma_donata +
                '}';
    }

    public void setNume(@JsonProperty("nume") String nume) {
        this.nume = nume;
    }

    public int getSuma_donata() {
        return suma_donata;
    }

    public void setSuma_donata(@JsonProperty("suma_donata") int suma_donata) {
        this.suma_donata = suma_donata;
    }


    public CazCaritabil(String nume) {
        this.nume = nume;
    }

    @JsonCreator
    public CazCaritabil( @JsonProperty("nume") String nume,  @JsonProperty("suma_donata") int suma_donata) {
        this.nume = nume;
        this.suma_donata=suma_donata;
    }
}
