package com.sda.practical.database;

import com.sda.practical.exceptions.DatabaseCRUDException;
import com.sda.practical.exceptions.DatabaseConnectionException;
import com.sda.practical.exceptions.UnknownException;
import com.sda.practical.utils.LoggerUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;

class BookRepositoryTest {

    @Test
    public void deleteBookThrowsUnknownException() {
        BookRepository bookRepository = new BookRepository();
        BookEntity bookEntity = getBookEntityForExceptionTest();
        Assertions.assertThrows(UnknownException.class, () -> bookRepository.delete(bookEntity));
    }

    @Test
    public void deleteBookThrowsDatabaseCRUDException() {
        BookRepository bookRepository = new BookRepository();
        Assertions.assertThrows(DatabaseCRUDException.class, () -> bookRepository.delete(null));
    }

    @Test
    public void deleteBookDoesNotThrowAnyException() {
        BookRepository bookRepository = new BookRepository();
        AuthorRepository authorRepository=new AuthorRepository();
        BookEntity bookEntity = getBookEntityForExceptionTest();
        AuthorEntity authorEntity = bookEntity.getAuthorEntity();
        try {
            authorRepository.add(authorEntity);
            bookRepository.add(bookEntity);
            Assertions.assertDoesNotThrow(() -> bookRepository.delete(bookEntity));
            authorRepository.delete(authorEntity);
        } catch (DatabaseCRUDException | DatabaseConnectionException | UnknownException e) {
            LoggerUtils.print(e.getMessage());
        }
    }

    public BookEntity getBookEntityForExceptionTest() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(Integer.MAX_VALUE);
        String title = "BookTest";
        AuthorEntity authorEntity = AuthorRepositoryTest.getAuthorForTest();
        Date releaseDate = Date.valueOf("2020-01-01");
        bookEntity.setReleaseDate(releaseDate);
        bookEntity.setBookTitle(title);
        bookEntity.setAuthorEntity(authorEntity);
        return bookEntity;
    }
}