package Repo;

import Domain.Joc;
import Domain.Jucator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class HJoc2Repo implements IJoc2Repository{

    private static SessionFactory factory;


    public HJoc2Repo(SessionFactory prop) {
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
        System.out.println("update joc-repo");
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
        return entity;    }

    @Override
    public List<Joc> getJocuriDinRunda(int id, int rundaId) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Joc> abonati = session.createQuery("from Joc  where id=:id" +
                    " and runda=:j").setParameter("id",id)
                    .setParameter("j",rundaId).list();

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
    public Joc getJocParticipant(int id, int runda, int user) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Joc> abonati = session.createQuery("from Joc  where id=:id" +
                    " and runda=:j and jucator =:juc").setParameter("id",id)
                    .setParameter("j",runda).
                            setParameter("juc",user).
                            list();

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
    public List<Joc> getToateJocurile(int id) {
        return null;
    }
}
