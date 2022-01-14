import Domain.CazCaritabil;
import Domain.Donatie;
import Domain.Donator;
import Domain.Voluntar;
import Repository.CazRepo;
import Repository.DonatieRepo;
import Repository.DonatorRepo;
import Repository.VoluntarRepo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {


        Properties prop = new Properties();
        try{
            prop.load(new FileReader("bd.config"));

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        CazRepo repoC = new CazRepo(prop);
        DonatorRepo donatorRepo = new DonatorRepo(prop);
        VoluntarRepo voluntarRepo = new VoluntarRepo(prop);
        DonatieRepo donatieRepo =new DonatieRepo(prop,repoC,donatorRepo);




        //donatieRepo.delete(2);
       // CazCaritabil caz= repoC.findOne(10);






    }
}
