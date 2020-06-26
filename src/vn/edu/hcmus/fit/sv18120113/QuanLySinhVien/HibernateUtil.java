package vn.edu.hcmus.fit.sv18120113.QuanLySinhVien;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * vn.edu.hcmus.fit.sv18120113.QuanLySinhVien
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @date 6/24/20 - 4:17 PM
 * @description
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory Creation Failed!");
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static <T> List<T> queryList(Class<T> tClass, String hqlString, Map<String, String> hqlParameters) {
        List<T> list = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<T> query = session.createQuery(hqlString, tClass);

            for (String key : hqlParameters.keySet()) {
                query.setParameter(key, hqlParameters.get(key));
            }

            list = query.list();

            session.close();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public static <T> T querySingle(Class<T> tClass, String hqlString, Map<String,String> hqlParameters) {
        List<T> lomList = HibernateUtil.queryList(tClass, hqlString, hqlParameters);

        if (lomList.size() > 0) {
            return lomList.get(0);
        }

        return null;
    }

    public static <T> boolean insertRow(T row) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(row);
            transaction.commit();

            session.close();
            return true;

        } catch (HibernateException ex) {
            ex.printStackTrace();

            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }

    public static <T> T getRow(Class<T> classT, Serializable primaryKey) {
        T row = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            row = session.get(classT, primaryKey);
            session.close();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }

        return row;
    }

    public static <T> boolean updateRow(T row) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(row);
            transaction.commit();

            session.close();
            return true;

        } catch (HibernateException ex) {
            ex.printStackTrace();

            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }

    public static <T> boolean deleteRow(T row) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(row);
            transaction.commit();

            session.close();
            return true;

        } catch (HibernateException ex) {
            ex.printStackTrace();

            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }
}

//    Draft
//    public static <T> boolean hqlUpdate(Class<T> classT, String hqlUpdate, Map<String, String> hqlParams) {
//        Transaction transaction = null;
//
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//
//            Query<T> query = session.createQuery(hqlUpdate, classT);
//
//            for (String key : hqlParams.keySet()) {
//                System.out.println(hqlParams.get(key));
//                query.setParameter(key, hqlParams.get(key));
//            }
//
//            query.executeUpdate();
//
//            transaction.commit();
//            session.close();
//
//            return true;
//
//        } catch (HibernateException ex) {
//            ex.printStackTrace();
//
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            return false;
//        }
//    }