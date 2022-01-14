package Repo;

import Domain.Joc;
import Domain.Jucator;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Joc2Repo implements IJoc2Repository{

    private JdbcUtils utils;
    Properties serverProps= new Properties();

    public Joc2Repo(Properties prop) {

        utils= new JdbcUtils(prop);
    }

    @Override
    public List<Joc> getJocuriDinRunda(int id, int rundaId) {
        List<Joc> lista = new ArrayList<>();
        Connection con = utils.getConnection();

        try(PreparedStatement pst = con.prepareStatement("select * from joc2 where id= ? and " +
                " runda= ?")){

            pst.setInt(1,id);
            pst.setInt(2,rundaId);
            try(ResultSet resultSet = pst.executeQuery()){
                while(resultSet.next()){
                    int idDat = resultSet.getInt("id");
                    int rd=resultSet.getInt(2);
                    int player=resultSet.getInt(3);
                    String cuv=resultSet.getString(4);
                    String car=resultSet.getString(5);
                    int pcte=resultSet.getInt(6);


                    Joc jc=new Joc(player,cuv,car);
                    jc.setId(idDat);
                    jc.setRunda(rd);
                    jc.setPuncte(pcte);
                    lista.add(jc);
                }
            }
            //con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return  lista;
    }

    @Override
    public Joc getJocParticipant(int id, int runda, int user) {
        return null;
    }

    @Override
    public List<Joc> getToateJocurile(int id) {
        List<Joc> lista=new ArrayList<>();
        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement("select * from joc2 where id = ?")){

            statement.setInt(1,id);
            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    int idDat = resultSet.getInt("id");
                    int rd=resultSet.getInt(2);
                    int player=resultSet.getInt(3);
                    String cuv=resultSet.getString(4);
                    String car=resultSet.getString(5);
                    int pcte=resultSet.getInt(6);


                    Joc jc=new Joc(player,cuv,car);
                    jc.setId(idDat);
                    jc.setRunda(rd);
                    jc.setPuncte(pcte);
                    lista.add(jc);
                }
            }

            //con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return lista;
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
        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement
                ("insert into joc2(id,runda,jucator,cuvant,caracteristici," +
                        "puncte) values (?,?,?,?,?,?)")){


            statement.setInt(1,entity.getId());
            statement.setInt(2,entity.getRunda());
            statement.setInt(3,entity.getJucator());

            statement.setString(4, entity.getCuvant());
            statement.setString(5, entity.getCaracteristici());
            statement.setInt(6,0);


            int result= statement.executeUpdate();
            //con.close();
            return entity;



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  null;    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public Joc update(Joc entity) {
        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement
                ("update joc2 set puncte= ? where id =? and runda = ?" +
                        "  and jucator =?")){

            statement.setInt(1,entity.getPuncte());
            statement.setInt(2,entity.getId());
            statement.setInt(3,entity.getRunda());
            statement.setInt(4,entity.getJucator());

            int result= statement.executeUpdate();
            //con.close();
            System.out.println("gata update");
            return entity;



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  null;
    }
}
