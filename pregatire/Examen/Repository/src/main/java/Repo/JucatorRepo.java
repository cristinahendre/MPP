package Repo;

import Domain.Jucator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class JucatorRepo implements IJucatorRepository {

    private JdbcUtils utils;
    Properties serverProps= new Properties();

    @Autowired
    public JucatorRepo(Properties prop) {
//        try {
//            serverProps.load(CazRepo.class.getResourceAsStream("/chatserver.properties"));
//            System.out.println("Server properties set. ");
//            serverProps.list(System.out);
//        } catch (
//                IOException e) {
//            System.err.println("Cannot find chatserver.properties "+e);
//            return;
//        }
//        //logger.info("intializare cu proprietati {}", prop);
        utils= new JdbcUtils(prop);
    }

    private  static  final Logger logger = LogManager.getLogger();

    @Override
    public Jucator findOne(Integer integer) {

        logger.traceEntry("Caut persoana cu acest id: {}", integer);

        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement("select * from persoana where id = ?")){

            statement.setInt(1,integer);
            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    int id = resultSet.getInt(1);
                    String nume= resultSet.getString(2);
                    String email=resultSet.getString(3);
                    Jucator pers =new Jucator(nume,email);
                    pers.setId(id);
                    return pers;
                }
            }

            //con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }

    @Override
    public Iterable<Jucator> findAll() {

        logger.traceEntry("afisez toate elementele ");
        List<Jucator> lista = new ArrayList<>();
        Connection con = utils.getConnection();

        try(PreparedStatement pst = con.prepareStatement("select * from persoana")){

            try(ResultSet resultSet = pst.executeQuery()){
                while(resultSet.next()){
                    int id = resultSet.getInt("id");
                    String nume= resultSet.getString("parola");
                    String em = resultSet.getString(3);

                    Jucator c =new Jucator(nume,em);
                    c.setId(id);
                    lista.add(c);
                }
            }
            //con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return  lista;
    }

    @Override
    public Jucator save(Jucator entity) {

        logger.traceEntry("Adaug persoana{}", entity);
        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement
                ("insert into persoana(parola,email) values (?,?)")){


            statement.setString(1,entity.getParola());
            statement.setString(2,entity.getEmail());

            int result= statement.executeUpdate();
            logger.traceEntry("S-au modificat {} randuri",result);
            //con.close();
            return entity;



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  null;
    }

    @Override
    public void delete(Integer integer) {

        logger.traceEntry("Sterg persoana cu id-ul {}", integer);
        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement
                ("delete from persoana where id = ?")){

            statement.setInt(1,integer);

            int result= statement.executeUpdate();
            logger.traceEntry("S-au modificat {} randuri",result);
            //con.close();




        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Jucator update(Jucator entity) {

        logger.traceEntry("Modific persoana {}", entity);
        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement
                ("update persoana set parola=? , email =? where id =?")){

            statement.setString(1,entity.getParola());
            statement.setString(2,entity.getEmail());
            statement.setInt(3,entity.getId());

            int result= statement.executeUpdate();
            logger.traceEntry("S-au modificat {} randuri",result);
            //con.close();
            return entity;



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  null;
    }


    @Override
    public Jucator getPersDupaDate(String nume, String em) {
        logger.traceEntry("Caut persoana cu acest nume: {}", nume);

        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement("select id from persoana where parola = ? and email =?")){

            statement.setString(1,nume);
            statement.setString(2,em);
            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    int id = resultSet.getInt(1);

                    Jucator pers =new Jucator(nume,em);
                    pers.setId(id);
                    return pers;
                }
            }

            //con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
