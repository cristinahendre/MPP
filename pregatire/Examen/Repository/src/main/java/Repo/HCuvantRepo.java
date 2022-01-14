package Repo;

import Domain.Cuvant;
import Domain.Jucator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class HCuvantRepo implements ICuvantRepository{
    private static SessionFactory factory;


    public HCuvantRepo(SessionFactory prop) {
        factory= prop;
    }


    @Override
    public Cuvant findOne(Integer integer) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Cuvant> abonati = session.createQuery("from Cuvant where id =:id").setParameter("id",integer).list();

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
    public Iterable<Cuvant> findAll() {
        return null;
    }

    @Override
    public Cuvant save(Cuvant entity) {
        return null;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public Cuvant update(Cuvant entity) {
        return null;
    }

    @Override
    public Cuvant getCuvantDupaLitere(String litere) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Cuvant> abonati = session.createQuery("from Cuvant where litere =:id").setParameter("id",litere).list();

            tx.commit();
            if(abonati.isEmpty()) return  null;
            return abonati.get(0);
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;    }
}
