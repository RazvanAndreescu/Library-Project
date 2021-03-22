package com.sda.practical.database;

import com.sda.practical.exceptions.DatabaseCRUDException;
import com.sda.practical.exceptions.DatabaseConnectionException;
import com.sda.practical.exceptions.UnknownException;
import com.sda.practical.utils.LoggerUtils;
import org.hibernate.*;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;

public class BookRepository implements Storable<BookEntity>{

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
            throw new UnknownException(e.getMessage(), e, "Unknown Exception has occur!#8");
        } finally {
            session.close();
        }
        return bookEntity;
    }
}
