package com.sda.practical.database;

import com.sda.practical.exceptions.DatabaseCRUDException;
import com.sda.practical.exceptions.DatabaseConnectionException;
import com.sda.practical.exceptions.UnknownException;
import com.sda.practical.utils.LoggerUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;
class AuthorRepositoryTest {

    @Test
    public void deleteAuthorThrowsUnknownException() {
        AuthorRepository authorRepository = new AuthorRepository();
        AuthorEntity authorEntity = getAuthorForTest();
        Assertions.assertThrows(UnknownException.class, () -> authorRepository.delete(authorEntity));
    }

    @Test
    public void deleteAuthorThrowsDatabaseCRUDException() {
        AuthorRepository authorRepository = new AuthorRepository();
        Assertions.assertThrows(DatabaseCRUDException.class, () -> authorRepository.delete(null));
    }

    @Test
    public void deleteAuthorDoesNotThrowAnyException() {
        AuthorRepository authorRepository = new AuthorRepository();
        AuthorEntity authorEntity = getAuthorForNoException();
        try {
            authorRepository.add(authorEntity);
        } catch (DatabaseCRUDException | DatabaseConnectionException | UnknownException e) {
            LoggerUtils.print(e.getMessage());
        }
        Assertions.assertDoesNotThrow(() -> authorRepository.delete(authorEntity));
    }

    public static  AuthorEntity getAuthorForTest() {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setAuthorFirstName("FirstNameTest");
        authorEntity.setAuthorLastName("LastNameTest");
        authorEntity.setId(Integer.MAX_VALUE);
        authorEntity.setDateOfBirth(Date.valueOf("1997-10-15"));
        return authorEntity;
    }

    private AuthorEntity getAuthorForNoException() {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(2);
        authorEntity.setAuthorFirstName("Second");
        authorEntity.setAuthorLastName("Author");
        authorEntity.setDateOfBirth(Date.valueOf("2000-01-01"));
        return authorEntity;
    }
}