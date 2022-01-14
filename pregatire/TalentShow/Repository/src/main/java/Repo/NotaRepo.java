package Repo;

import Domain.Nota;
import Domain.Participant;
import Domain.Persoana;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class NotaRepo implements INoteRepository{

    private JdbcUtils utils;
    private IParticipantRepository partRepo;
    Properties serverProps= new Properties();

    @Autowired
    public NotaRepo(Properties prop, IParticipantRepository partRepo) {

        utils= new JdbcUtils(prop);
        this.partRepo=partRepo;
    }

    @Override
    public Nota findOne(Integer integer) {

        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement("select * from note where participant = ?")){

            statement.setInt(1,integer);
            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    int idPa = resultSet.getInt(1);
                    int nota1= resultSet.getInt(2);
                    int nota2=resultSet.getInt(3);
                    int nota3=resultSet.getInt(4);

                    String juriu1=resultSet.getString(5);
                    String juriu2=resultSet.getString(6);
                    String juriu3=resultSet.getString(7);
                    Nota n=new Nota(idPa);
                    n.setNota1(nota1);
                    n.setNota2(nota2);
                    n.setNota3(nota3);

                    n.setJuriu1(juriu1);
                    n.setJuriu2(juriu2);
                    n.setJuriu3(juriu3);


                    return n;
                }
            }

            //con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Nota> findAll() {
        return null;
    }

    @Override
    public Nota save(Nota entity) {
        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement
                ("insert into note(participant,nota1,nota2,nota3," +
                        "juriu1,juriu2,juriu3) values (?,?,?,?,?,?,?)")){


            statement.setInt(1,entity.getIdParticipant());
            statement.setInt(2,entity.getNota1());
            statement.setInt(3,entity.getNota2());
            statement.setInt(4,entity.getNota3());

            statement.setString(5, entity.getJuriu1());
            statement.setString(6, entity.getJuriu2());
            statement.setString(7, entity.getJuriu3());

            int result= statement.executeUpdate();
            //con.close();
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
    public Nota update(Nota entity) {
        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement
                ("update note set nota1=? , nota2= ?, nota3=? ," +
                        " juriu1=? ,juriu2=? ,juriu3= ? where participant =?")){

            statement.setInt(1,entity.getNota1());
            statement.setInt(2,entity.getNota2());
            statement.setInt(3,entity.getNota3());

            statement.setString(4,entity.getJuriu1());
            statement.setString(5,entity.getJuriu2());
            statement.setString(6,entity.getJuriu3());

            statement.setInt(7,entity.getIdParticipant());

            int result= statement.executeUpdate();
            //con.close();
            return entity;



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  null;
    }

    @Override
    public int votare(Nota no, Persoana p, int nota) {

        //0=>status pending; punctaj=>status complet
        System.out.println("\n[repo] votare!!");
        Nota n =findOne(no.getIdParticipant());
        System.out.println("am gasit nota: "+n);
        if (n.getNota1() != 0 && n.getNota2() == 0 && n.getNota3() == 0) {

            System.out.println("ultimii 2 nesetati.");
            n.setNota2(nota);
            n.setJuriu2(p.getEmail());
            update(n);
            return 0;
        }
        if(n.getNota1()!=0 && n.getNota2()!=0 && n.getNota3() == 0){
            n.setNota3(nota);
            n.setJuriu3(p.getEmail());
            update(n);
            int punctaj=n.getNota1()+n.getNota2()+n.getNota3();
            return punctaj;
        }
        if(n.getNota1()!=0 && n.getNota3()!=0 && n.getNota2()!=0){
            System.out.println("toti 3");
            int punctaj=n.getNota1()+n.getNota2()+n.getNota3();
            return punctaj;
        }
        return 0;
    }

    @Override
    public List<Participant> getParticipantiDelaJuriu(String juriu) {

        System.out.println("[repo] cu juriu = "+juriu);
        List<Participant> lista=new ArrayList<>();
        Connection con = utils.getConnection();

        try(PreparedStatement statement = con.prepareStatement("" +
                "select participant from note where juriu1 = ? or juriu2= ? or juriu3= ?")){

            statement.setString(1,juriu);
            statement.setString(2,juriu);
            statement.setString(3,juriu);
            System.out.println("\nresult set: \n");
            try(ResultSet resultSet = statement.executeQuery()){
                while(resultSet.next()){
                    int idPa = resultSet.getInt(1);
                    System.out.println("id = "+idPa);
                    Participant p=partRepo.findOne(idPa);
                    System.out.println("participant = "+p);
                    lista.add(p);
                }
            }

            //con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return lista;
    }

    @Override
    public Nota getPunctajeParticipant(String nume) {

        int id=partRepo.getIdParticipant(nume);
        return findOne(id);
    }
}
