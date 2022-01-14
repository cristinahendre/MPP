package Repository;

import Domain.CazCaritabil;
import Domain.Donator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DonatorRepo implements  DonatorRepoI{

    private JdbcUtils utils;

    public DonatorRepo(Properties prop) {
        logger.info("intializare cu proprietati {}", prop);
        utils= new JdbcUtils(prop);
    }

    private  static  final Logger logger = LogManager.getLogger();


    @Override
    public Iterable<Donator> getDonatorDupaNume(String numeDat) {
        logger.traceEntry("afisez toti donatorii cu numele {} ", numeDat);
        List<Donator> lista = new ArrayList<>();
        Connection con = utils.getConnection();

        try(PreparedStatement pst = con.prepareStatement("select * from donator where nume like ? or prenume like ?")){
            pst.setString(1,numeDat+"%");
            pst.setString(2,numeDat+"%");

            try(ResultSet resultSet = pst.executeQuery()){
                while(resultSet.next()){
                    int id = resultSet.getInt("id");
                    String nume= resultSet.getString("nume");
                    String prenume =resultSet.getString("prenume");
                    String adresa= resultSet.getString("adresa");
                    long nr= resultSet.getLong("nr_telefon");

                    Donator donator = new Donator(nume,prenume,adresa,nr);
                    donator.setId(id);
                    lista.add(donator);
                }
            }
            //con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        logger.traceExit(lista);
        return  lista;

    }


    @Override
    public Donator getDonatorDupaDate(String nume, String prenume, String adresa, long telefon) {
        logger.traceEntry("caut donatorul cu aceste date: {},{},{},{} ", nume,prenume,adresa,telefon);

        Connection con = utils.getConnection();

        try(PreparedStatement pst = con.prepareStatement("select id from donator where nume =? and prenume= ? and adresa= ? and nr_telefon = ? ")){
            pst.setString(1,nume);
            pst.setString(2,prenume);
            pst.setString(3,adresa);
            pst.setLong(4,telefon);

            try(ResultSet resultSet = pst.executeQuery()){
                while(resultSet.next()){
                    int id = resultSet.getInt("id");


                    Donator donator = new Donator(nume,prenume,adresa,telefon);
                    donator.setId(id);
                    logger.traceEntry("Am gasit!!");
                    logger.traceExit(donator);
                    return donator;
                }
            }
           // con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return  null;
    }

    @Override
    public Donator findOne(Integer integer) {
        logger.traceEntry("Caut donatorul cu acest id: {}", integer);

        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement("select * from donator where id = ?")){

            statement.setInt(1,integer);
            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    int id = resultSet.getInt(1);
                    String nume= resultSet.getString(2);
                    String prenume= resultSet.getString(3);
                    String adresa= resultSet.getString(4);
                    long nrTelefon = resultSet.getLong(5);

                    Donator donator = new Donator(nume,prenume,adresa,nrTelefon);
                    donator.setId(id);
                    return donator;
                }
            }

            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }

    @Override
    public Iterable<Donator> findAll() {
        logger.traceEntry("afisez toate elementele ");
        List<Donator> lista = new ArrayList<>();
        Connection con = utils.getConnection();

        try(PreparedStatement pst = con.prepareStatement("select * from donator")){

            try(ResultSet resultSet = pst.executeQuery()){
                while(resultSet.next()){
                    int id = resultSet.getInt("id");
                    String nume= resultSet.getString("nume");
                    String prenume =resultSet.getString("prenume");
                    String adresa= resultSet.getString("adresa");
                    long nr= resultSet.getLong("nr_telefon");

                    Donator donator = new Donator(nume,prenume,adresa,nr);
                    donator.setId(id);

                    lista.add(donator);
                }
            }
           // con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return  lista;
    }

    @Override
    public Donator save(Donator entity) {
        logger.traceEntry("Adaug donatorul {}", entity);
        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement
                ("insert into donator(nume,prenume,adresa,nr_telefon) " +
                        "values (?,?,?,?)")){


            statement.setString(1,entity.getNume());
            statement.setString(2,entity.getPrenume());
            statement.setString(3,entity.getAdresa());
            statement.setLong(4,entity.getNrTelefon());

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
        logger.traceEntry("Sterg donatorul cu id-ul {}", integer);
        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement
                ("delete from donator where id = ?")){

            statement.setInt(1,integer);

            int result= statement.executeUpdate();
            logger.traceEntry("S-au modificat {} randuri",result);
            con.close();




        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    @Override
    public Donator update(Donator entity) {

        logger.traceEntry("Modific donatorul {}", entity);
        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement
                ("update donator set nume=? , prenume =? ," +
                        "adresa =?,  nr_telefon =? where id =?")){

            statement.setString(1,entity.getNume());
            statement.setString(2,entity.getPrenume());
            statement.setString(3,entity.getAdresa());
            statement.setLong(4,entity.getNrTelefon());
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
        logger.traceEntry("Numar cate elemente sunt in donator");
        int nr =0;
        Connection con = utils.getConnection();
        try(Statement statement = con.createStatement()){

            ResultSet resultSet = statement.executeQuery("select count(*) from donator");
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
}
