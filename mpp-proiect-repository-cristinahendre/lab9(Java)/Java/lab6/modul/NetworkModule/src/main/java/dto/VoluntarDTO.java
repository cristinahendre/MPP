package dto;


import java.io.Serializable;


public class VoluntarDTO implements Serializable{
    private String email;
    private String passwd;
    private String nume;
    private String prenume;

    public void setPasswd(String passwd) {
        this.passwd = passwd;
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

    public  VoluntarDTO(String email ,String pass){
        this.email=email;
        this.passwd=pass;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public VoluntarDTO(String id, String passwd, String n, String p) {
        this.email = id;
        this.passwd = passwd;
        this.nume=n;
        this.prenume=p;
    }

    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.email = id;
    }

    public String getPasswd() {
        return passwd;
    }

    @Override
    public String toString(){
        return "VoluntarDTO["+email+' '+passwd+"]";
    }
}
