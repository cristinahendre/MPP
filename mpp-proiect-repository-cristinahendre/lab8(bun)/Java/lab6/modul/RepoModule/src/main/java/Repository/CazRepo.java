package Repository;

import Domain.CazCaritabil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class CazRepo implements  CazRepoI {

    private  JdbcUtils utils;

    public CazRepo(Properties prop) {
        logger.info("intializare cu proprietati {}", prop);
        utils= new JdbcUtils(prop);
    }

    private  static  final Logger logger = LogManager.getLogger();

    @Override
    public CazCaritabil findOne(Integer integer) {

        logger.traceEntry("Caut cazul caritabil cu acest id: {}", integer);

        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement("select * from caz where id = ?")){

            statement.setInt(1,integer);
            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    int id = resultSet.getInt(1);
                    String nume= resultSet.getString(2);
                    int suma =resultSet.getInt(3);
                    CazCaritabil caz =new CazCaritabil(nume);
                    caz.setSuma_donata(suma);
                    caz.setId(id);
                    return caz;
                }
            }

            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }

    @Override
    public Iterable<CazCaritabil> findAll() {

        logger.traceEntry("afisez toate elementele ");
        List<CazCaritabil> lista = new ArrayList<>();
        Connection con = utils.getConnection();

        try(PreparedStatement pst = con.prepareStatement("select * from caz")){

            try(ResultSet resultSet = pst.executeQuery()){
                while(resultSet.next()){
                    int id = resultSet.getInt("id");
                    String nume= resultSet.getString("nume");
                    int suma = resultSet.getInt(3);

                    CazCaritabil c= new CazCaritabil(nume);
                    c.setSuma_donata(suma);
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
    public CazCaritabil save(CazCaritabil entity) {

        logger.traceEntry("Adaug cazul caritabil {}", entity);
        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement
                ("insert into caz(nume,suma_donata) values (?,?)")){


            statement.setString(1,entity.getNume());
            statement.setInt(2,entity.getSuma_donata());

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

        logger.traceEntry("Sterg cazul caritabil cu id-ul {}", integer);
        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement
                ("delete from caz where id = ?")){

            statement.setInt(1,integer);

            int result= statement.executeUpdate();
            logger.traceEntry("S-au modificat {} randuri",result);
            con.close();




        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public CazCaritabil update(CazCaritabil entity) {

        logger.traceEntry("Modific cazul caritabil {}", entity);
        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement
                ("update caz set nume=? , suma_donata =? where id =?")){

            statement.setString(1,entity.getNume());
            statement.setInt(2,entity.getSuma_donata());
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
    public int nrElem() {
        logger.traceEntry("Numar cate elemente sunt in caz");
        int nr =0;
        Connection con = utils.getConnection();
        try(Statement statement = con.createStatement()){

            ResultSet resultSet = statement.executeQuery("select count(*) from caz");
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
