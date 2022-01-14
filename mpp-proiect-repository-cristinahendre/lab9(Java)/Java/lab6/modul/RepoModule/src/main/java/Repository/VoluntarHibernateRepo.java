package Repository;

import Domain.CazCaritabil;
import Domain.Donator;
import Domain.Voluntar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class VoluntarHibernateRepo implements VoluntarRepoI {


    private static SessionFactory factory;

    public VoluntarHibernateRepo(SessionFactory prop) {
        logger.info("intializare cu factory {}", prop);
        factory=prop;
    }

    private  static  final Logger logger = LogManager.getLogger();


    @Override
    public Voluntar findOne(Integer integer) {
        logger.traceEntry("Caut voluntarul cu acest id: {}", integer);

        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Voluntar> abonati = session.createQuery("from Voluntar where id =:id").setParameter("id",integer).list();

            tx.commit();
            return abonati.get(0);
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;



    }

    @Override
    public Iterable<Voluntar> findAll() {
        logger.traceEntry("afisez toate elementele ");

        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Voluntar> abonati = session.createQuery("from Voluntar ").list();

            tx.commit();
            return abonati;
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;

    }

    @Override
    public Voluntar save(Voluntar entity) {
        logger.traceEntry("Adaug voluntarul {}", entity);

        Session session = factory.openSession();
        Transaction tx = null;
        int abonatId = 0;

        try {
            tx = session.beginTransaction();
            abonatId = (int) session.save(entity);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        entity.setId(abonatId);
        return entity;
    }

    @Override
    public void delete(Integer integer) {

        logger.traceEntry("Sterg voluntarul cu id-ul {}", integer);
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Voluntar abonat = (Voluntar) session.get(Voluntar.class, integer);
            session.delete(abonat);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public Voluntar update(Voluntar entity) {
        logger.traceEntry("Modific voluntarul {}", entity);

        logger.traceEntry("Modific voluntarul {}", entity);
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            session.update(entity);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return entity;
    }

    @Override
    public int nrElem() {
        logger.traceEntry("Numar cate elemente sunt in voluntar");
        int nr =0;
//        Connection con = utils.getConnection();
//        try(Statement statement = con.createStatement()){
//
//            ResultSet resultSet = statement.executeQuery("select count(*) from voluntar");
//            resultSet.next();
//            nr= resultSet.getInt(1);
//            con.close();
//            return nr;
//
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        logger.traceExit(nr);
        return nr;
    }

    @Override
    public Voluntar getVoluntarDupaDate(String email, String parola) {
        logger.traceEntry("Caut voluntarul cu emailul {}",email);
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Voluntar> abonati = session.createQuery("from Voluntar where email=:e and parola=:p ")
                    .setParameter("e",email).setParameter("p",parola).list();

            tx.commit();
            if(abonati.isEmpty()) return  null;
            return abonati.get(0);
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
}
