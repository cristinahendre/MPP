import Domain.CazCaritabil;
import Domain.Donatie;
import Domain.Donator;
import Repository.*;

import java.io.IOException;
import java.util.Properties;

public class MainTest {
    public static void main(String[] args) {

        Properties serverProps=new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/chatserver.properties"));

        } catch (IOException e) {

            return;
        }

//        VoluntarRepoI repo  =new VoluntarRepo(serverProps);
//        CazRepoI cazRepoI =new CazRepo(serverProps);
//        DonatorRepoI donaRepo =new DonatorRepo(serverProps);
//        DonatieRepoI donatieR= new DonatieRepo(serverProps,cazRepoI,donaRepo);
//        CazCaritabil caz= cazRepoI.findOne(4);
//        Donator don =donaRepo.findOne(5);
//        Donatie d =new Donatie(don,caz,12);
//        System.out.println(donatieR.getDonatieDupaDate(d));
        //System.out.println(repo.getVoluntarDupaDate("japo1@vol.com", "20201"));
    }
}
