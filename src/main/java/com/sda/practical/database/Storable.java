package com.sda.practical.database;

import com.sda.practical.exceptions.DatabaseCRUDException;
import com.sda.practical.exceptions.DatabaseConnectionException;
import com.sda.practical.exceptions.UnknownException;
import com.sda.practical.utils.LoggerUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import java.util.List;

public interface Storable<T> {

    default void add(T t) throws DatabaseCRUDException, DatabaseConnectionException, UnknownException {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(t);
            transaction.commit();
        } catch (HibernateException | IllegalStateException | RollbackException e) {
            LoggerUtils.print(e.getMessage());
            throw new DatabaseCRUDException(e.getMessage(), e, "Cannot save in the database!");
        } catch (Exception e) {
            throw new UnknownException(e.getMessage(), e, "Unknown exception has occur!");
        }
    }

    default void delete(T t) throws DatabaseCRUDException, DatabaseConnectionException, UnknownException {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(t);
            transaction.commit();
        } catch (HibernateException | IllegalStateException | RollbackException | IllegalArgumentException e) {
            LoggerUtils.print(e.getMessage());
            throw new DatabaseCRUDException(e.getMessage(), e, "Cannot delete from the database!");
        } catch (Exception e) {
            if (t instanceof AuthorEntity) {
                throw new UnknownException(e.getMessage(), e, "This author has books in the database and cannot be deleted!");
            } else {
                throw new UnknownException(e.getMessage(), e, "Unknown exception has occur!");
            }
        }
    }

    default void update(T t) throws DatabaseCRUDException, DatabaseConnectionException, UnknownException {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(t);
            transaction.commit();
        } catch (HibernateException | IllegalStateException | RollbackException | IllegalArgumentException e) {
            LoggerUtils.print(e.getMessage());
            throw new DatabaseCRUDException(e.getMessage(), e, "Cannot update to the database!");
        } catch (Exception e) {
            throw new UnknownException(e.getMessage(), e, "Unknown exception has occur");
        }
    }


    default T getById(int Id, T name) throws DatabaseCRUDException, DatabaseConnectionException, UnknownException {
        Session session = HibernateUtils.getSessionFactory().openSession();
        T t = null;
        int lastIndex = name.getClass().getName().lastIndexOf(".");
        String className = name.getClass().getName().substring(lastIndex + 1);
        try {
            Query query = session.createQuery("FROM " + className + " WHERE Id = :Id");
            query.setParameter("Id", Id);
            t = (T) query.getSingleResult();
        } catch (NoResultException | HibernateException e) {
            LoggerUtils.print(e.getMessage());
            if (t instanceof AuthorEntity) {
                throw new DatabaseCRUDException(e.getMessage(), e, "The author isn't in the database!");
            } else if (t instanceof BookEntity) {
                throw new DatabaseCRUDException(e.getMessage(), e, "The book isn't in the database!");
            }
        } catch (Exception e) {
            throw new UnknownException(e.getMessage(), e, "Unknown Exception has occur!");
        } finally {
            session.close();
        }
        return t;
    }

    default List<T> getAll(T t) throws DatabaseCRUDException, DatabaseConnectionException, UnknownException {
        Session session = HibernateUtils.getSessionFactory().openSession();
        int lastIndex = t.getClass().getName().lastIndexOf(".");
        String className = t.getClass().getName().substring(lastIndex + 1);
        List<T> itemsList = null;
        try {
            Query query = session.createQuery("FROM " + className);
            itemsList = query.list();
        } catch (NullPointerException | HibernateException e) {
            LoggerUtils.print(e.getMessage());
            throw new DatabaseCRUDException(e.getMessage(), e, "There are no authors in the database!");
        } catch (Exception e) {
            throw new UnknownException(e.getMessage(), e, "Unknown Exception has occur!");
        } finally {
            session.close();
        }
        return itemsList;
    }

}
