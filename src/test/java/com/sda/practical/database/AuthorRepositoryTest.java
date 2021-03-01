package com.sda.practical.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import javax.persistence.PersistenceException;
import java.sql.Date;
class AuthorRepositoryTest {

    @Test
    public void deleteAuthorThrowsPersistenceException() {
        AuthorRepository authorRepository = new AuthorRepository();
        AuthorEntity authorEntity = getAuthorForTest();
        Assertions.assertThrows(PersistenceException.class, () -> authorRepository.deleteAuthor(authorEntity));
    }

    @Test
    public void deleteAuthorThrowsIllegalArgumentException() {
        AuthorRepository authorRepository = new AuthorRepository();
        Assertions.assertThrows(IllegalArgumentException.class, () -> authorRepository.deleteAuthor(null));
    }

/*
    @Test
    public void noExceptionTest() {
        AuthorRepository authorRepository = new AuthorRepository();
        AuthorEntity authorEntity = getAuthorForNoException();
        authorRepository.addAuthor(authorEntity);
        Assertions.assertDoesNotThrow(() -> authorRepository.deleteAuthor(authorEntity));
    }
*/

    private AuthorEntity getAuthorForTest() {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setAuthorFirstName("FirstNameTest");
        authorEntity.setAuthorLastName("LastNameTest");
        authorEntity.setAuthorId(Integer.MAX_VALUE);
        authorEntity.setDateOfBirth(Date.valueOf("1997-10-15"));
        return authorEntity;
    }

    private AuthorEntity getAuthorForNoException() {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setAuthorId(2);
        authorEntity.setAuthorFirstName("Second");
        authorEntity.setAuthorLastName("Author");
        authorEntity.setDateOfBirth(Date.valueOf("2000-01-01"));
        return authorEntity;
    }
}