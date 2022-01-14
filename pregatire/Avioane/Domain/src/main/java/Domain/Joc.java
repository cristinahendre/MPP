package Domain;


import java.io.Serializable;

public class Joc implements Serializable {
    int idPers;
    String culoare;
    int idCastigator;

    public int getIdCastigator() {
        return idCastigator;
    }

    public void setIdCastigator(int idCastigator) {
        this.idCastigator = idCastigator;
    }

    public int getIdPers() {
        return idPers;
    }

    public void setIdPers(int idPers) {
        this.idPers = idPers;
    }

    @Override
    public String toString() {
        return "Joc{" +
                "idPers=" + idPers +
                ", culoare='" + culoare + '\'' +
                ", pozitie='" + pozitie + '\'' +
                ", id=" + id +
                ", idWinner= "+idCastigator+ '\''+
                '}';
    }

    public String getCuloare() {
        return culoare;
    }

    public void setCuloare(String culoare) {
        this.culoare = culoare;
    }

    public String getPozitie() {
        return pozitie;
    }

    public void setPozitie(String pozitie) {
        this.pozitie = pozitie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String pozitie;

    public Joc(int idPers, String culoare, String pozitie) {
        this.idPers = idPers;
        this.culoare = culoare;
        this.pozitie = pozitie;
    }

    int id;


}
