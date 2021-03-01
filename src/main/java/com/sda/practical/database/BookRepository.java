package com.sda.practical.database;

import com.sda.practical.exceptions.DatabaseCRUDException;
import com.sda.practical.exceptions.DatabaseConnectionException;
import com.sda.practical.exceptions.UnknownException;
import com.sda.practical.utils.LoggerUtils;
import org.hibernate.*;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import java.util.List;

public class BookRepository {

    public void addBook(BookEntity bookEntity) throws DatabaseConnectionException, DatabaseCRUDException, UnknownException {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.save(bookEntity);
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

    public void deleteBook(BookEntity bookEntity) throws DatabaseConnectionException, DatabaseCRUDException, UnknownException {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.delete(bookEntity);
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

    public BookEntity getBookByTitle(String bookTitle) throws DatabaseCRUDException, UnknownException, DatabaseConnectionException {
        Session session = HibernateUtils.getSessionFactory().openSession();
        BookEntity bookEntity = null;
        try {
            Query query = session.createQuery("FROM BookEntity WHERE bookTitle=:bookTitle");
            query.setParameter("bookTitle", bookTitle);
            bookEntity = (BookEntity) query.getSingleResult();
        } catch (NoResultException | HibernateException e) {
            LoggerUtils.print(e.getMessage());
            throw new DatabaseCRUDException(e.getMessage(), e, "The book isn't in the database or there are more than one book with the same title!");
        } catch (Exception e){
            throw new UnknownException(e.getMessage(), e, "Unknown Exception has occur!");
        } finally {
            session.close();
        }
        return bookEntity;
    }


    public BookEntity getBookById(int bookId) throws DatabaseCRUDException, DatabaseConnectionException, UnknownException{
        Session session = HibernateUtils.getSessionFactory().openSession();
        BookEntity bookEntity = null;
        try {
        Query query = session.createQuery("FROM BookEntity WHERE bookId=:bookId");
        query.setParameter("bookId", bookId);
        bookEntity = (BookEntity) query.getSingleResult();
        } catch (NoResultException | HibernateException e) {
            LoggerUtils.print(e.getMessage());
            throw new DatabaseCRUDException(e.getMessage(), e, "The book isn't in the database!");
        } catch (Exception e){
            throw new UnknownException(e.getMessage(), e, "Unknown exception has occur!");
        }
        session.close();
        return bookEntity;
    }

    public List<BookEntity> getAllBooks() throws UnknownException, DatabaseCRUDException, DatabaseConnectionException{
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<BookEntity> bookList = null;
        try {
            Query query = session.createQuery("FROM BookEntity");
            bookList = query.list();
        } catch (NullPointerException | HibernateException e){
            LoggerUtils.print(e.getMessage());
            throw new DatabaseCRUDException(e.getMessage(), e, "There are no books in the database");
        } catch (Exception e) {
            throw new UnknownException(e.getMessage(), e, "Unknown exception has occur!");
        }finally {
            session.close();
        }
        return bookList;
    }
}
