package Repository;

import Domain.Donator;
import Domain.Voluntar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class VoluntarRepo implements VoluntarRepoI {


    private JdbcUtils utils;
    Properties serverProps =new Properties();

    public VoluntarRepo(Properties prop) {
//        try {
//            serverProps.load(VoluntarRepo.class.getResourceAsStream("/chatserver.properties"));
//            System.out.println("Server properties set. ");
//            serverProps.list(System.out);
//        } catch (
//                IOException e) {
//            System.err.println("Cannot find chatserver.properties "+e);
//            return;
//        }
        //logger.info("intializare cu proprietati {}", prop);
        utils= new JdbcUtils(prop);
//        logger.info("intializare cu proprietati {}", prop);
        //utils= new JdbcUtils(prop);
    }

    private  static  final Logger logger = LogManager.getLogger();


    @Override
    public Voluntar findOne(Integer integer) {
        logger.traceEntry("Caut voluntarul cu acest id: {}", integer);

        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement("select * from voluntar where id = ?")){

            statement.setInt(1,integer);
            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    int id = resultSet.getInt(1);
                    String nume= resultSet.getString(2);
                    String prenume= resultSet.getString(3);
                    String email= resultSet.getString(4);
                    String parola= resultSet.getString(5);

                    Voluntar voluntar = new Voluntar(nume,prenume,email,parola);
                    voluntar.setId(id);
                    return voluntar;
                }
            }

            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }

    @Override
    public Iterable<Voluntar> findAll() {
        logger.traceEntry("afisez toate elementele ");
        List<Voluntar> lista = new ArrayList<>();
        Connection con = utils.getConnection();

        try(PreparedStatement pst = con.prepareStatement("select * from voluntar")){

            try(ResultSet resultSet = pst.executeQuery()){
                while(resultSet.next()){
                    int id = resultSet.getInt("id");
                    String nume= resultSet.getString("nume");
                    String prenume =resultSet.getString("prenume");
                    String email= resultSet.getString("email");
                    String parola =resultSet.getString("parola");

                    Voluntar vol = new Voluntar(nume,prenume,email,parola);
                    vol.setId(id);

                    lista.add(vol);
                }
            }
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return  lista;
    }

    @Override
    public Voluntar save(Voluntar entity) {
        logger.traceEntry("Adaug voluntarul {}", entity);
        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement
                ("insert into voluntar(nume,prenume,email,parola) " +
                        "values (?,?,?,?)")){

            statement.setString(1,entity.getNume());
            statement.setString(2,entity.getPrenume());
            statement.setString(3,entity.getEmail());
            statement.setString(4,entity.getParola());

            int result= statement.executeUpdate();
            logger.traceEntry("S-au modificat {} randuri",result);
            con.close();
            return entity;



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  null;
    }

    @Override
    public void delete(Integer integer) {

        logger.traceEntry("Sterg voluntarul cu id-ul {}", integer);
        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement
                ("delete from voluntar where id = ?")){

            statement.setInt(1,integer);

            int result= statement.executeUpdate();
            logger.traceEntry("S-au modificat {} randuri",result);
            con.close();




        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Voluntar update(Voluntar entity) {
        logger.traceEntry("Modific voluntarul {}", entity);
        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement
                ("update voluntar set nume=? , prenume =? ," +
                        "email =?,  parola =? where id =?")){

            statement.setString(1,entity.getNume());
            statement.setString(2,entity.getPrenume());
            statement.setString(3,entity.getEmail());
            statement.setString(4,entity.getParola());
            statement.setInt(5,entity.getId());

            int result= statement.executeUpdate();
            logger.traceEntry("S-au modificat {} randuri",result);
            con.close();
            return entity;



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  null;
    }

    @Override
    public int nrElem() {
        logger.traceEntry("Numar cate elemente sunt in voluntar");
        int nr =0;
        Connection con = utils.getConnection();
        try(Statement statement = con.createStatement()){

            ResultSet resultSet = statement.executeQuery("select count(*) from voluntar");
            resultSet.next();
            nr= resultSet.getInt(1);
            con.close();
            return nr;


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        logger.traceExit(nr);
        return nr;
    }

    @Override
    public Voluntar getVoluntarDupaDate(String email, String parola) {
       logger.traceEntry("Caut voluntarul cu emailul {}",email);
       Connection con =utils.getConnection();

       try(PreparedStatement statement = con.prepareStatement(
               "select * from voluntar where email =? and parola =?")) {

           statement.setString(1,email);
           statement.setString(2,parola);

           try(ResultSet resultSet = statement.executeQuery()){
               if(resultSet.next()){
                   int id = resultSet.getInt(1);
                   String nume= resultSet.getString(2);
                   String prenume= resultSet.getString(3);


                   Voluntar voluntar = new Voluntar(nume,prenume,email,parola);
                   voluntar.setId(id);
                   return voluntar;
               }
           }
       } catch (SQLException throwables) {
           throwables.printStackTrace();
       }
       return null;
    }
}
