package objectprotocol;


import Domain.Voluntar;

public class LoginResponse implements ResponseObject {

    private Voluntar vol;
    public LoginResponse(Voluntar vol ){ this.vol=vol;}

    public Voluntar getVoluntar(){ return vol;}
}

