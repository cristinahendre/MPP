package Domain;

import java.util.Objects;

public class Donatie extends  Entity<Integer>{

    private Donator donator;
    private CazCaritabil caz;
    private int suma_donata;

    public Donatie(Donator donator, CazCaritabil caz, int suma_donata) {
        this.donator = donator;
        this.caz = caz;
        this.suma_donata = suma_donata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Donatie)) return false;
        Donatie donatie = (Donatie) o;
        return getSuma_donata() == donatie.getSuma_donata() && Objects.equals(getDonator(), donatie.getDonator()) && Objects.equals(getCaz(), donatie.getCaz());
    }

    @Override
    public String toString() {
        return "Donatie{" +
                " id= "+this.getId() +" "+
                "donator=" + donator +
                ", caz=" + caz +
                ", suma_donata=" + suma_donata +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDonator(), getCaz(), getSuma_donata());
    }

    public Donator getDonator() {
        return donator;
    }

    public void setDonator(Donator donator) {
        this.donator = donator;
    }

    public CazCaritabil getCaz() {
        return caz;
    }

    public void setCaz(CazCaritabil caz) {
        this.caz = caz;
    }

    public int getSuma_donata() {
        return suma_donata;
    }

    public void setSuma_donata(int suma_donata) {
        this.suma_donata = suma_donata;
    }
}
