package Repository;

import Domain.CazCaritabil;
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


public class CazHibernateRepo implements  CazRepoI {

    private static SessionFactory factory;

    public CazHibernateRepo(SessionFactory prop) {
        logger.info("intializare sesiune {}", prop);
        factory= prop;
    }

    private  static  final Logger logger = LogManager.getLogger();

    @Override
    public CazCaritabil findOne(Integer id) {

        logger.traceEntry("Caut cazul caritabil cu acest id: {}", id);

        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<CazCaritabil> abonati = session.createQuery("from CazCaritabil where id =:id").setParameter("id",id).list();

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
    public Iterable<CazCaritabil> findAll() {

        logger.traceEntry("afisez toate elementele(Hibernate) ");

        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<CazCaritabil> abonati = session.createQuery("from CazCaritabil ").list();

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
    public CazCaritabil save(CazCaritabil entity) {

        logger.traceEntry("Adaug cazul caritabil {}", entity);

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

        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            CazCaritabil abonat = (CazCaritabil) session.get(CazCaritabil.class, integer);
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
    public CazCaritabil update(CazCaritabil entity) {

        logger.traceEntry("Modific cazul caritabil {}", entity);
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
//        logger.traceEntry("Numar cate elemente sunt in caz");
//        int nr =0;
//        Connection con = utils.getConnection();
//        try(Statement statement = con.createStatement()){
//
//            ResultSet resultSet = statement.executeQuery("select count(*) from caz");
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
//        return nr;
        return  0;
    }
}
