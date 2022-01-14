package Domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Donator extends  Entity<Integer> implements Serializable {


    private String nume;
    private String prenume;
    private String adresa;
    private long nrTelefon;

    public Donator(){}

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

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public long getNrTelefon() {
        return nrTelefon;
    }

    public void setNrTelefon(long nrTelefon) {
        this.nrTelefon = nrTelefon;
    }

    public String getAdresaSpatii(){
        String[] list = adresa.split(",");
        String rez="";
        for(String cuv: list){
            rez+=cuv;
            rez+='\n';
        }
        return rez;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Donator)) return false;
        Donator donator = (Donator) o;
        return nrTelefon == donator.nrTelefon && nume.equals(donator.nume) && prenume.equals(donator.prenume) && adresa.equals(donator.adresa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, prenume, adresa, nrTelefon);
    }

    @Override
    public String toString() {
        return "Donator{" +
                " id = "+this.getId()+ " "+
                "nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", adresa='" + adresa + '\'' +
                ", nrTelefon=" + nrTelefon +
                '}';
    }

    public Donator(String nume, String prenume, String adresa, long nrTelefon) {
        this.nume = nume;
        this.prenume = prenume;
        this.adresa = adresa;
        this.nrTelefon = nrTelefon;
    }


}
