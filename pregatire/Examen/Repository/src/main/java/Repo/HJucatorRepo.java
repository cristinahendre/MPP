package Repo;

import Domain.Jucator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;


public class HJucatorRepo implements IJucatorRepository {

    private static SessionFactory factory;


    public HJucatorRepo(SessionFactory prop) {
        logger.info("intializare sesiune {}", prop);
        factory= prop;
    }

    /*
    Pentru Hibernate+Rest:
   @Autowired
    public HLucrareRepo(){
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    +adauga hibernate.cfg.xml in resources la rest.
     */

    private  static  final Logger logger = LogManager.getLogger();

    @Override
    public Jucator findOne(Integer id) {

        logger.traceEntry("Caut persoana cu acest id: {}", id);

        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Jucator> abonati = session.createQuery("from Jucator where id =:id").setParameter("id",id).list();

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
    public Iterable<Jucator> findAll() {

        logger.traceEntry("afisez toate elementele(Hibernate) ");

        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Jucator> abonati = session.createQuery("from Jucator ").list();

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
    public Jucator save(Jucator entity) {

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
            Jucator abonat = (Jucator) session.get(Jucator.class, integer);
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
    public Jucator update(Jucator entity) {

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
    public Jucator getPersDupaDate(String nume, String em) {
        logger.traceEntry("Caut persoana cu acest nume: {}", nume);

        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Jucator> abonati = session.createQuery("from Jucator where parola =:nume and email =:em").setParameter("nume",nume).setParameter("em",em).list();

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

