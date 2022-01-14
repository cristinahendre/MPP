package dto;

import Domain.CazCaritabil;
import Domain.Donator;

import java.io.Serializable;

public class DonatieDTO implements Serializable {
    private CazCaritabil id_caz;
    private String id_donatie;
    private Donator id_donator;
    private int sumaDonata;

    public DonatieDTO(CazCaritabil id_caz, Donator id_donator, int sumaDonata) {
        this.id_caz = id_caz;
        this.id_donator = id_donator;
        this.sumaDonata = sumaDonata;
    }

    @Override
    public String toString() {
        return "DonatieDTO{" +
                "id_caz='" + id_caz + '\'' +
                ", id_donatie='" + id_donatie + '\'' +
                ", id_donator='" + id_donator + '\'' +
                ", sumaDonata='" + sumaDonata + '\'' +
                '}';
    }

    public CazCaritabil getId_caz() {
        return id_caz;
    }

    public void setId_caz(CazCaritabil id_caz) {
        this.id_caz = id_caz;
    }

    public String getId_donatie() {
        return id_donatie;
    }

    public void setId_donatie(String id_donatie) {
        this.id_donatie = id_donatie;
    }

    public Donator getId_donator() {
        return id_donator;
    }

    public void setId_donator(Donator id_donator) {
        this.id_donator = id_donator;
    }

    public int getSumaDonata() {
        return sumaDonata;
    }

    public void setSumaDonata(int sumaDonata) {
        this.sumaDonata = sumaDonata;
    }
}