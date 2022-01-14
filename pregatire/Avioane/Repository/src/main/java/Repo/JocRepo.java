package Repo;

import Domain.Joc;
import Domain.Persoana;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class JocRepo implements IJocRepository{
    private JdbcUtils utils;
    Properties serverProps= new Properties();

    @Autowired
    public JocRepo(Properties prop) {

        utils= new JdbcUtils(prop);
    }

    private  static  final Logger logger = LogManager.getLogger();

    @Override
    public boolean participaLaJocPersoana(int id) {
        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement("select * from jocavion where persoana = ?")){

            statement.setInt(1,id);
            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    System.out.println("intra in true");
                    return  true;
                }
                return false;
            }

            //con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public int getPozitiePersoana(Persoana p) {
        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement("select pozitie from jocavion where persoana != ?")){

            statement.setInt(1,p.getId());
            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    int poz = resultSet.getInt(1);
                    System.out.println("\njoc repo: am gasit pozitia: "+poz);
                    return poz;
                }
            }

            //con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    @Override
    public void seteazaWinner(int idPersoana, int idWinner) {
        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement
                ("update jocavion set castigator=? ")){

            statement.setInt(1,idWinner);

            int result= statement.executeUpdate();
            System.out.println("Update joc..");
            logger.traceEntry("S-au modificat {} randuri",result);
            //con.close();



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public List<Joc> getJocuriPersoana(int id) {
        List<Joc> lista = new ArrayList<>();
        Connection con = utils.getConnection();

        try(PreparedStatement pst = con.prepareStatement("select pozitie, castigator from jocavion where persoana = ? ")){

            pst.setInt(1,id);
            try(ResultSet resultSet = pst.executeQuery()){
                while(resultSet.next()){
                    String poz= resultSet.getString(1);
                    int em = resultSet.getInt(2);

                    Joc j= new Joc(id,"",poz);
                    j.setIdCastigator(em);
                    lista.add(j);
                }
            }
            //con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return  lista;
    }

    @Override
    public Joc findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Joc> findAll() {

        return null;
    }

    @Override
    public Joc save(Joc entity) {
        if(participaLaJocPersoana(entity.getId())) return null;
        logger.traceEntry("Adaug jocul {}", entity);
        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement
                ("insert into jocavion(persoana,culoare,pozitie, castigator) values (?,?,?,?)")){


            statement.setInt(1,entity.getIdPers());
            statement.setString(2,entity.getCuloare());
            statement.setString(3,entity.getPozitie());
            statement.setInt(4,-1);

            int result= statement.executeUpdate();
            logger.traceEntry("S-au modificat {} randuri",result);
            //con.close();
            System.out.println("am adaugat jocul..");
            return entity;



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  null;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public Joc update(Joc entity) {
        return null;
    }
}
