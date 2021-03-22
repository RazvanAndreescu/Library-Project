package com.sda.practical.database;

import com.sda.practical.exceptions.DatabaseCRUDException;
import com.sda.practical.exceptions.DatabaseConnectionException;
import com.sda.practical.exceptions.UnknownException;
import com.sda.practical.utils.GenericTypes;
import com.sda.practical.utils.LoggerUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class AuthorRepository implements Storable<AuthorEntity> {


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
            throw new DatabaseCRUDException(e.getMessage(), e, "The author isn't in the database!");
        } catch (Exception e) {
            throw new UnknownException(e.getMessage(), e, "There are more than one author with this name in the database!");
        } finally {
            session.close();
        }
        return authorEntity;
    }

    public List<AuthorEntity> getAuthorsWithNoBookInTheDatabase() throws DatabaseCRUDException, DatabaseConnectionException, UnknownException {
        List<AuthorEntity> authorList = new ArrayList<>();
        for (AuthorEntity authorEntity : getAll(GenericTypes.getAuthorEntity())) {
            if (authorEntity.getListOfBooks().size() == 0) {
                authorList.add(authorEntity);
            }
        }
        return authorList;
    }

}
