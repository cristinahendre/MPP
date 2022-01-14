package Repo;

import Domain.Joc;
import Domain.Persoana;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HJocRepo implements IJocRepository{

    private static SessionFactory factory;

    @Autowired
    public HJocRepo(){
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    public HJocRepo( SessionFactory prop) {
        factory= prop;
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
        return entity;    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public Joc update(Joc entity) {
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
    public Joc getJocDupaDate(int id, String user) {

        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Joc> abonati = session.createQuery("from Joc where " +
                    "id =:id and user =:u").setParameter("id",id)
                    .setParameter("u",user).list();

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
