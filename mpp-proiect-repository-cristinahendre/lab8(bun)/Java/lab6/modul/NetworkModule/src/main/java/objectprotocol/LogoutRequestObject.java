package objectprotocol;


import Domain.Voluntar;

public class LogoutRequestObject implements RequestObject {

    Voluntar vol;

    public LogoutRequestObject(Voluntar vol) {
        this.vol = vol;
    }

    public Voluntar getVoluntar(){ return  vol;}
}
