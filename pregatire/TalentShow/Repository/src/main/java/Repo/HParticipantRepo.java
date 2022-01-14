package Repo;

import Domain.Participant;
import Domain.Persoana;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Primary
public class HParticipantRepo implements IParticipantRepository{

    private static SessionFactory factory;


    public HParticipantRepo( SessionFactory prop) {
        factory= prop;
    }


    @Autowired
    public HParticipantRepo(){
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public Participant findOne(Integer integer) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Participant> abonati = session.createQuery("from Participant where id =:id").setParameter("id",integer).list();

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
    public Iterable<Participant> findAll() {

        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Participant> abonati = session.createQuery("from Participant ").list();

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
    public Participant save(Participant entity) {
        return null;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public Participant update(Participant entity) {
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
    public int getIdParticipant(String nume) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Participant> abonati = session.createQuery("from Participant where nume =:id").setParameter("id",nume).list();

            tx.commit();
            if(abonati.isEmpty()) return  -1;
            return abonati.get(0).getId();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return -1;
    }
}
