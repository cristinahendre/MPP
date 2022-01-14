//import Domain.CazCaritabil;
//import Repository.CazRepo;
//import org.junit.jupiter.api.Test;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.Properties;
//
//public class TestCaz {
//
//    @Test
//    public void testez(){
//        Properties prop = new Properties();
//        try{
//            prop.load(new FileReader("bd.config"));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        CazRepo repoC = new CazRepo(prop);
//
//        CazCaritabil nou = new CazCaritabil("Testare Probleme");
//        nou.setId(1000);
//        nou.setSuma_donata(1000);
//        repoC.save(nou);
//
//        assert (repoC.findOne(1000).getNume().equals("Testare Probleme"));
//        assert (repoC.findOne(1000).getSuma_donata()==1000);
//
//        nou.setSuma_donata(2500);
//        nou.setNume("Nume");
//        repoC.update(nou);
//
//        assert (repoC.findOne(1000).getNume().equals("Nume"));
//        assert (repoC.findOne(1000).getSuma_donata()==2500);
//
//        repoC.delete(1000);
//        assert (repoC.findOne(1000) == null);
//
//
//
//
//    }
//}
