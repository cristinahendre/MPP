package dto;


import Domain.CazCaritabil;
import Domain.Donatie;
import Domain.Donator;
import Domain.Voluntar;

import javax.print.attribute.standard.DocumentName;

public class DTOUtils {
    public static Voluntar getFromDTO(VoluntarDTO usdto){
        String id=usdto.getEmail();
        String pass=usdto.getPasswd();
        String nume=usdto.getNume();
        String prenume =usdto.getPrenume();
        return new Voluntar(nume,prenume, id, pass);

    }
    public static VoluntarDTO getDTO(Voluntar user){
        String id=user.getEmail();
        String pass=user.getParola();
        String nume =user.getNume();
        String prenume =user.getPrenume();
        return new VoluntarDTO(id, pass,nume,prenume);
    }

    public static VoluntarDTO getHalfVoluntar(String e, String pass){

        return new VoluntarDTO(e,pass);
    }



    public static Donatie getDonatieFromDTO(DonatieDTO usdto){
        CazCaritabil id_caz=usdto.getId_caz();

        int suma=usdto.getSumaDonata();
        Donator id_donator =usdto.getId_donator();
        Donatie don = new Donatie(id_donator,id_caz,suma);
        return don;

    }

    public static Donator getDonatorDTO(DonatorDTO don){
        String nume=don.getNume();
        String pr= don.getPrenume();
        long tel =don.getTel();
        String addr= don.getAdresa();
        return new Donator(nume,pr,addr,tel);
    }

    public static DonatieDTO getDonatieDTO(Donatie user){
        CazCaritabil caz=user.getCaz();
        Donator donator=user.getDonator();
        int suma = user.getSuma_donata();
        return new DonatieDTO(caz,donator,suma);
    }


}
