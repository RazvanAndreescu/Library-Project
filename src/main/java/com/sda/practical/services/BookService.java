package com.sda.practical.services;

import com.sda.practical.database.AuthorEntity;
import com.sda.practical.database.AuthorRepository;
import com.sda.practical.database.BookEntity;
import com.sda.practical.database.BookRepository;
import com.sda.practical.exceptions.DatabaseCRUDException;
import com.sda.practical.exceptions.DatabaseConnectionException;
import com.sda.practical.exceptions.UnknownException;
import com.sda.practical.utils.*;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class BookService extends BaseService {

    public void addBook(Scanner input, BookRepository bookRepository, AuthorRepository authorRepository) throws UnknownException, DatabaseConnectionException {
        BookEntity bookEntity = new BookEntity();
        getBookTitleFromUser(bookEntity, input);
        try {
            getAuthorFromUser(bookEntity, authorRepository, input);
            getBookReleaseDate(bookEntity, input);
            bookRepository.add(bookEntity);
        } catch (DatabaseCRUDException e) {
            LoggerUtils.print(e.getExceptionMessage());
        }
        LoggerUtils.print("The book was successfully added in the database!");
    }


    public void deleteBook(Scanner input, BookRepository bookRepository, AuthorRepository authorRepository) throws DatabaseConnectionException, UnknownException {
        String userCriteria = CriteriaUtils.getUserCriteriaForBook(input);
        BookEntity bookEntity;
        try {
            bookEntity = generateBook(input, bookRepository, userCriteria);
            if (askConfirmationFormUser(input)) {
                bookRepository.delete(bookEntity);
                LoggerUtils.print("This book was successfully deleted form the database!");
                checkIfTheAuthorHasOtherBooksInDatabase(input, bookEntity, authorRepository);
            }
        } catch (DatabaseCRUDException e) {
            LoggerUtils.print(e.getExceptionMessage());
        }
    }

    public void editBook(Scanner input, BookRepository bookRepository, AuthorRepository authorRepository) throws DatabaseConnectionException, UnknownException {
        try {
            LoggerUtils.print(bookRepository.getAll(GenericTypes.getBookEntity()));
            if (bookRepository.getAll(GenericTypes.getBookEntity()).size() != 0) {
                int bookId = KeyboardUtils.readNumber(input, "Insert bookId: ");
                BookEntity bookEntity = bookRepository.getById(bookId, GenericTypes.getBookEntity());
                if (bookEntity != null) {
                    String userCriteria = CriteriaUtils.getUserCriteriaForUpdateBook(input);
                    if (userCriteria.equalsIgnoreCase("t")) {
                        getBookTitleFromUser(bookEntity, input);
                    } else if (userCriteria.equalsIgnoreCase("r")) {
                        getBookReleaseDate(bookEntity, input);
                    }
                    bookRepository.update(bookEntity);
                    LoggerUtils.print("The book was successfully updated!");
                }
            }
        } catch (DatabaseCRUDException e) {
            LoggerUtils.print(e.getExceptionMessage());
        }
    }

    public void printAllBooks(BookRepository bookRepository) throws DatabaseConnectionException, UnknownException {
        try {
            LoggerUtils.print(bookRepository.getAll(GenericTypes.getBookEntity()));
        } catch (DatabaseCRUDException e) {
            LoggerUtils.print(e.getExceptionMessage());
        }
    }

    public void findBook(Scanner input, BookRepository bookRepository) throws UnknownException, DatabaseConnectionException {
        String userCriteria = CriteriaUtils.getUserCriteriaForBook(input);
        BookEntity bookEntity = null;
        try {
            bookEntity = generateBook(input, bookRepository, userCriteria);
        } catch (DatabaseCRUDException e) {
            LoggerUtils.print(e.getExceptionMessage());
        }
        if (bookEntity != null) {
            LoggerUtils.print(bookEntity);
        }
    }

    private void checkIfTheAuthorHasOtherBooksInDatabase(Scanner input, BookEntity bookEntity, AuthorRepository authorRepository) throws DatabaseConnectionException, UnknownException {
        AuthorEntity authorEntity = bookEntity.getAuthorEntity();
        try {
            List<AuthorEntity> authorsWithNoBooksInTheDataBase = authorRepository.getAuthorsWithNoBookInTheDatabase();
            if (authorsWithNoBooksInTheDataBase.contains(authorEntity)) {
                String userOption = CriteriaUtils.getUserConfirmationToDeleteAuthor(input, authorEntity);
                if (userOption.equalsIgnoreCase("y") && askConfirmationFormUser(input)) {
                    authorRepository.delete(authorEntity);
                }
            }
        } catch (DatabaseCRUDException e) {
            LoggerUtils.print(e.getExceptionMessage());
        }
    }

    private BookEntity generateBook(Scanner input, BookRepository bookRepository, String userCriteria) throws UnknownException, DatabaseCRUDException, DatabaseConnectionException {
        BookEntity bookEntity = null;
        LoggerUtils.print(bookRepository.getAll(GenericTypes.getBookEntity()));
        if (userCriteria.equalsIgnoreCase("i")) {
            int bookId = KeyboardUtils.readNumber(input, "Insert bookId: ");
            bookEntity = bookRepository.getById(bookId, GenericTypes.getBookEntity());
        } else {
            LoggerUtils.print("Insert book's title: ");
            String bookTitle = input.nextLine();
            bookEntity = bookRepository.getBookByTitle(bookTitle);
        }
        return bookEntity;
    }

    private void getBookTitleFromUser(BookEntity bookEntity, Scanner input) {
        LoggerUtils.print("Insert book title: ");
        String bookTitle = input.nextLine();
        bookEntity.setBookTitle(bookTitle);
    }

    private void getAuthorFromUser(BookEntity bookEntity, AuthorRepository authorRepository, Scanner input) throws DatabaseCRUDException, DatabaseConnectionException, UnknownException {
        LoggerUtils.print(authorRepository.getAll(GenericTypes.getAuthorEntity()));
        LoggerUtils.print("Insert authorId: ");
        int authorId = input.nextInt();
        input.nextLine();
        AuthorEntity authorEntity = authorRepository.getById(authorId, GenericTypes.getAuthorEntity());
        if (authorEntity == null) {
            LoggerUtils.print("This author doesn't exist in database");
            return;
        }
        bookEntity.setAuthorEntity(authorEntity);
        getBookReleaseDate(bookEntity, input);
    }

    private void getBookReleaseDate(BookEntity bookEntity, Scanner input) {
        String releaseDateString = "";
        do {
            releaseDateString = DataValidator.validateSQLDataFormat("Insert book release date: ", input);
        } while (!DataValidator.validateReleaseDate(bookEntity.getAuthorEntity(), Date.valueOf(releaseDateString)));
        bookEntity.setReleaseDate(Date.valueOf(releaseDateString));
    }
}
