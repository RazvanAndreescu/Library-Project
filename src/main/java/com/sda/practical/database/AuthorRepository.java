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
import java.util.ArrayList;
import java.util.List;

public class AuthorRepository {

    public void addAuthor(AuthorEntity authorEntity) throws DatabaseConnectionException, UnknownException, DatabaseCRUDException {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.save(authorEntity);
            transaction.commit();
        } catch (HibernateException | IllegalStateException | RollbackException e) {
            LoggerUtils.print(e.getMessage());
            throw new DatabaseCRUDException(e.getMessage(), e, "Cannot save in the database!");
        } catch (Exception e) {
            throw new UnknownException(e.getMessage(), e, "Unknown exception has occur!");
        } finally {
            session.close();
        }
    }


    public void deleteAuthor(AuthorEntity authorEntity) throws DatabaseConnectionException, UnknownException, DatabaseCRUDException {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.delete(authorEntity);
            transaction.commit();
        } catch (HibernateException | IllegalStateException | RollbackException | IllegalArgumentException e) {
            LoggerUtils.print(e.getMessage());
            throw new DatabaseCRUDException(e.getMessage(), e, "Cannot delete from the database!");
        } catch (Exception e) {
            throw new UnknownException(e.getMessage(), e, "Unknown Exception has occur!");
        } finally {
            session.close();
        }
    }


    public AuthorEntity getAuthorByName(String firstName, String lastName) throws DatabaseCRUDException, DatabaseConnectionException, UnknownException {
        AuthorEntity authorEntity = null;
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("FROM AuthorEntity WHERE firstName =:firstName AND lastName =:lastName");
            query.setParameter("firstName", firstName);
            query.setParameter("lastName", lastName);
            authorEntity = (AuthorEntity) query.getSingleResult();
        } catch (NoResultException | HibernateException e) {
            LoggerUtils.print(e.getMessage());
            throw new DatabaseCRUDException(e.getMessage(), e, "The author isn't in the database or there are more than one authors with the same name in the database!");
        } catch (Exception e) {
            throw new UnknownException(e.getMessage(), e, "Unknown Exception has occur!");
        } finally {
            session.close();
        }
        return authorEntity;
    }


    public AuthorEntity getAuthorById(int authorId) throws DatabaseCRUDException, DatabaseConnectionException, UnknownException {
        Session session = HibernateUtils.getSessionFactory().openSession();
        AuthorEntity authorEntity = null;
        try {
            Query query = session.createQuery("FROM AuthorEntity WHERE authorId = :authorId");
            query.setParameter("authorId", authorId);
            authorEntity = (AuthorEntity) query.getSingleResult();
        } catch (NoResultException | HibernateException e) {
            LoggerUtils.print(e.getMessage());
            throw new DatabaseCRUDException(e.getMessage(), e, "The author isn't in the database!");
        } catch (Exception e) {
            throw new UnknownException(e.getMessage(), e, "Unknown Exception has occur!");
        } finally {
            session.close();
        }
        return authorEntity;
    }

    public List<AuthorEntity> getAllAuthors() throws DatabaseCRUDException, DatabaseConnectionException, UnknownException {
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<AuthorEntity> authorsList = null;
        try {
            Query query = session.createQuery("FROM AuthorEntity");
            authorsList = query.list();
        } catch (NullPointerException | HibernateException e) {
            LoggerUtils.print(e.getMessage());
            throw new DatabaseCRUDException(e.getMessage(), e, "There are no authors in the database!");
        } catch (Exception e) {
            throw new UnknownException(e.getMessage(), e, "Unknown Exception has occur!");
        } finally {
            session.close();
        }
        return authorsList;
    }

    public List<AuthorEntity> getAuthorsWithNoBookInTheDatabase() throws DatabaseCRUDException, DatabaseConnectionException, UnknownException {
        List<AuthorEntity> authorList = new ArrayList<>();
        for (AuthorEntity authorEntity : getAllAuthors()) {
            if (authorEntity.getListOfBooks().size() == 0) {
                authorList.add(authorEntity);
            }
        }
        return authorList;
    }
}
