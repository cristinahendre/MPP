package Domain;

import java.io.Serializable;

public class Nota implements Serializable {
    public Nota(){
    }

    int idParticipant;
    int nota1,nota2,nota3;

    public int getIdParticipant() {
        return idParticipant;
    }

    public void setIdParticipant(int idParticipant) {
        this.idParticipant = idParticipant;
    }

    public int getNota1() {
        return nota1;
    }

    public void setNota1(int nota1) {
        this.nota1 = nota1;
    }

    public int getNota2() {
        return nota2;
    }

    public void setNota2(int nota2) {
        this.nota2 = nota2;
    }

    public int getNota3() {
        return nota3;
    }

    public void setNota3(int nota3) {
        this.nota3 = nota3;
    }

    public String getJuriu1() {
        return juriu1;
    }

    public void setJuriu1(String juriu1) {
        this.juriu1 = juriu1;
    }

    public String getJuriu2() {
        return juriu2;
    }

    public void setJuriu2(String juriu2) {
        this.juriu2 = juriu2;
    }

    public String getJuriu3() {
        return juriu3;
    }

    public void setJuriu3(String juriu3) {
        this.juriu3 = juriu3;
    }

    @Override
    public String toString() {
        return "Nota{" +
                "idParticipant=" + idParticipant +
                ", nota1=" + nota1 +
                ", nota2=" + nota2 +
                ", nota3=" + nota3 +
                ", juriu1='" + juriu1 + '\'' +
                ", juriu2='" + juriu2 + '\'' +
                ", juriu3='" + juriu3 + '\'' +
                '}';
    }

    public Nota(int idParticipant) {
        this.idParticipant = idParticipant;
    }

    String juriu1,juriu2,juriu3;

}
