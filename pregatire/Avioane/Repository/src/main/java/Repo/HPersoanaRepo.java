package Repo;

import Domain.Persoana;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


public class HPersoanaRepo implements IPersoanaRepository {

    private static SessionFactory factory;


    public HPersoanaRepo( SessionFactory prop) {
        logger.info("intializare sesiune {}", prop);
        factory= prop;
    }

    private  static  final Logger logger = LogManager.getLogger();

    @Override
    public Persoana findOne(Integer id) {

        logger.traceEntry("Caut persoana cu acest id: {}", id);

        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Persoana> abonati = session.createQuery("from Persoana where id =:id").setParameter("id",id).list();

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

    @Override
    public Iterable<Persoana> findAll() {

        logger.traceEntry("afisez toate elementele(Hibernate) ");

        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Persoana> abonati = session.createQuery("from Persoana ").list();

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
    public Persoana save(Persoana entity) {

        logger.traceEntry("Adaug persoana {}", entity);

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
            Persoana abonat = (Persoana) session.get(Persoana.class, integer);
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
    public Persoana update(Persoana entity) {

        logger.traceEntry("Modific persoana {}", entity);
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
    public Persoana getPersDupaDate(String nume, String em) {
        logger.traceEntry("Caut persoana cu acest nume: {}", nume);

        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Persoana> abonati = session.createQuery("from Persoana where parola =:nume and email =:em").setParameter("nume",nume).setParameter("em",em).list();

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

    @Override
    public List<Persoana> getParticipanti() {
        logger.traceEntry("afisez toti participantii(Hibernate) ");

        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Persoana> abonati = session.createQuery("from Persoana where participa = true ").list();

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
}

