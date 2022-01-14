package dto;


import java.io.Serializable;


public class DonatorDTO implements Serializable{
    private String nume;
    private String prenume;
    private String adresa;

    @Override
    public String toString() {
        return "DonatorDTO{" +
                "nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", adresa='" + adresa + '\'' +
                ", tel=" + tel +
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

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public long getTel() {
        return tel;
    }

    public void setTel(long tel) {
        this.tel = tel;
    }

    private long tel;

    public DonatorDTO(String nume, String prenume, String adresa, long tel) {
        this.nume = nume;
        this.prenume = prenume;
        this.adresa = adresa;
        this.tel = tel;
    }

    public DonatorDTO(String nume){
        this.nume=nume;
    }
}
