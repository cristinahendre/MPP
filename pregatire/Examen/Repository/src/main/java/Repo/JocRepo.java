package Repo;

import Domain.Joc;
import Domain.Jucator;
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

    @Override
    public String getCuvinteRunde(int id) {
        String rez="";
        Connection con = utils.getConnection();

        try(PreparedStatement pst = con.prepareStatement("select distinct cuvant,runda from joc where id = ?")){

            pst.setInt(1,id);
            try(ResultSet resultSet = pst.executeQuery()){
                while(resultSet.next()){
                    String nume= resultSet.getString(1);
                    int runda=resultSet.getInt(2);
                    rez+="In runda "+runda+" s-a trimis cuvantul "+nume+" \n";

                }
            }
            //con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return  rez;
    }

    @Override
    public String getCaracteristiciPeJoc(int id, int idUser) {
        String rez="";
        Connection con = utils.getConnection();

        try(PreparedStatement pst = con.prepareStatement("select caracteristici,puncte,runda from joc where id = ? " +
                "  and jucator = ?")){

            pst.setInt(1,id);
            pst.setInt(2,idUser);
            try(ResultSet resultSet = pst.executeQuery()){
                while(resultSet.next()){
                    String carac= resultSet.getString(1);
                    int pcte=resultSet.getInt(2);
                    int runda=resultSet.getInt(3);
                    rez+="In runda "+runda+" s-au trimis caracteristicile "+carac+
                            " si s-au obtinut "+pcte+" puncte" +" \n";

                }
            }
            //con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return  rez;    }

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
        return null;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public Joc update(Joc entity) {
        return null;
    }
}
