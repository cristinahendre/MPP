package Repository;

import Domain.CazCaritabil;
import Domain.Donatie;
import Domain.Donator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DonatieRepo implements  DonatieRepoI {

    private CazRepoI cazRepo;
    private DonatorRepoI donatorRepo;

    private JdbcUtils utils;

    public DonatieRepo(Properties prop, CazRepoI cazRepo, DonatorRepoI donatorRepo) {
        logger.info("intializare cu proprietati {}", prop);
        utils= new JdbcUtils(prop);
        this.cazRepo=cazRepo;
        this.donatorRepo=donatorRepo;
    }

    private  static  final Logger logger = LogManager.getLogger();


    @Override
    public Donatie findOne(Integer integer) {
        logger.traceEntry("Caut donatia cu acest id: {}", integer);

        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement("select * from donatie where id = ?")){

            statement.setInt(1,integer);
            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    int id = resultSet.getInt(1);
                    int caz_id = resultSet.getInt(2);
                    int donator_id = resultSet.getInt(3);
                    int suma =resultSet.getInt(4);
                    CazCaritabil caz= cazRepo.findOne(caz_id);
                    Donator donator = donatorRepo.findOne(donator_id);
                    Donatie d = new Donatie(donator,caz,suma);
                    d.setId(id);
                    return  d;

                }
            }

            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }

    @Override
    public Iterable<Donatie> findAll() {
        logger.traceEntry("afisez toate donatiile ");
        List<Donatie> lista = new ArrayList<>();
        Connection con = utils.getConnection();

        try(PreparedStatement pst = con.prepareStatement("select * from donatie")){

            try(ResultSet resultSet = pst.executeQuery()){
                while(resultSet.next()){
                    int id = resultSet.getInt("id");
                    int caz_id= resultSet.getInt(2);
                    int donator_id =resultSet.getInt(3);
                    int suma =resultSet.getInt("suma");

                    CazCaritabil caz= cazRepo.findOne(caz_id);
                    Donator donator = donatorRepo.findOne(donator_id);
                    Donatie d = new Donatie(donator,caz,suma);
                    d.setId(id);
                    lista.add(d);


                }
            }
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return  lista;
    }


    @Override
    public Donatie save(Donatie entity) {
        logger.traceEntry("Adaug donatia {}", entity);

        CazCaritabil caz = entity.getCaz();
        caz.setSuma_donata(caz.getSuma_donata() + entity.getSuma_donata());
        cazRepo.update(entity.getCaz());
        Connection con = utils.getConnection();


        try(PreparedStatement statement = con.prepareStatement
                ("insert into donatie(id_caz,id_donator,suma) values (?,?,?)")){


            statement.setInt(1,entity.getCaz().getId());
            statement.setInt(2,entity.getDonator().getId());
            statement.setInt(3,entity.getSuma_donata());

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

        Donatie dn = findOne(integer);
        CazCaritabil caz = dn.getCaz();
        caz.setSuma_donata(caz.getSuma_donata()-dn.getSuma_donata());
        cazRepo.update(caz);


        logger.traceEntry("Sterg donatia cu id-ul {}", integer);
        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement
                ("delete from donatie where id = ?")){

            statement.setInt(1,integer);

            int result= statement.executeUpdate();
            logger.traceEntry("S-au modificat {} randuri",result);
            con.close();




        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }




    @Override
    public Donatie update(Donatie entity) {
        logger.traceEntry("Modific donatia {}", entity);
        Donatie initial = findOne(entity.getId());
        if(initial.getCaz().equals(entity.getCaz())){
            //se modifica doar suma donata
            CazCaritabil caz= initial.getCaz();
            caz.setSuma_donata(caz.getSuma_donata() - initial.getSuma_donata() + entity.getSuma_donata());

            cazRepo.update(caz);
        }
        else{

            //se modifica acel caz
            CazCaritabil caz= initial.getCaz();
            caz.setSuma_donata(caz.getSuma_donata()- initial.getSuma_donata());
            cazRepo.update(caz);

            CazCaritabil nou =entity.getCaz();
            nou.setSuma_donata(nou.getSuma_donata() + entity.getSuma_donata());
            cazRepo.update(nou);

        }


        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement
                ("update donatie set id_caz= ? , id_donator=?, suma =? where id =?")){

            statement.setInt(1,entity.getCaz().getId());
            statement.setInt(2,entity.getDonator().getId());
            statement.setInt(3,entity.getSuma_donata());
            statement.setInt(4,entity.getId());

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
        logger.traceEntry("Numar cate elemente sunt in donatie");
        int nr =0;
        Connection con = utils.getConnection();
        try(Statement statement = con.createStatement()){

            ResultSet resultSet = statement.executeQuery("select count(*) from donatie");
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
    public Donatie getDonatieDupaDate(Donatie don) {
        logger.traceEntry("Caut donatia cu aceste date: {}", don);

        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement("select id from donatie where id_caz = ? and id_donator =? and suma =?")){

            statement.setInt(1,don.getCaz().getId());
            statement.setInt(2,don.getDonator().getId());
            statement.setInt(3,don.getSuma_donata());

            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    int id = resultSet.getInt(1);


                    don.setId(id);
                    return  don;

                }
            }

           // con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }
}
