package com.sda.practical.services;

import com.sda.practical.database.AuthorEntity;
import com.sda.practical.database.AuthorRepository;
import com.sda.practical.database.BookEntity;
import com.sda.practical.database.BookRepository;
import com.sda.practical.exceptions.DatabaseCRUDException;
import com.sda.practical.exceptions.DatabaseConnectionException;
import com.sda.practical.exceptions.UnknownException;
import com.sda.practical.utils.CriteriaUtils;
import com.sda.practical.utils.DataValidator;
import com.sda.practical.utils.LoggerUtils;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class BookService extends BaseService {

    public void addBook(Scanner input, BookRepository bookRepository, AuthorRepository authorRepository) throws UnknownException, DatabaseConnectionException {
        LoggerUtils.print("Insert book title: ");
        String bookTitle = input.nextLine();
        try {
            LoggerUtils.print(authorRepository.getAllAuthors());
            LoggerUtils.print("Insert authorId: ");
            int authorId = input.nextInt();
            input.nextLine();
            AuthorEntity author = authorRepository.getAuthorById(authorId);
            if (author == null) {
                LoggerUtils.print("This author doesn't exist in database");
                return;
            }
            String releaseDateString = "";
            do {
                releaseDateString = DataValidator.validateSQLDataFormat("Insert book release date: ", input);
            } while (!DataValidator.validateReleaseDate(author, Date.valueOf(releaseDateString)));
            Date releaseDate = Date.valueOf(releaseDateString);
            BookEntity bookEntity = new BookEntity(bookTitle, releaseDate, author);
            bookRepository.addBook(bookEntity);
        } catch (DatabaseCRUDException e) {
            LoggerUtils.print(e.getExceptionMessage());
        }
        LoggerUtils.print("The book was successfully added in the database!");
    }


    public void deleteBook(Scanner input, BookRepository bookRepository, AuthorRepository authorRepository) throws DatabaseConnectionException, UnknownException {
        String userCriteria = CriteriaUtils.getUserCriteriaForBook(input);
        BookEntity bookEntity;
        try {
            LoggerUtils.print(bookRepository.getAllBooks());
            if (userCriteria.equalsIgnoreCase("i")) {
            LoggerUtils.print("Insert bookId: ");
            int bookId = input.nextInt();
            bookEntity = bookRepository.getBookById(bookId);
        } else {
            LoggerUtils.print("Insert book's title: ");
            String bookTitle = input.nextLine();
            bookEntity = bookRepository.getBookByTitle(bookTitle);
        }
            if (askConfirmationFormUser(input)) {
                bookRepository.deleteBook(bookEntity);
                LoggerUtils.print("This book was successfully deleted form the database!");
                checkIfTheAuthorHasOtherBooksInDatabase(input, bookEntity, authorRepository);
            }
        } catch (DatabaseCRUDException e) {
            LoggerUtils.print(e.getExceptionMessage());
        }
    }

    public void printAllBooks(BookRepository bookRepository) throws DatabaseConnectionException, UnknownException {
        try {
            LoggerUtils.print(bookRepository.getAllBooks());
        }catch (DatabaseCRUDException e){
            LoggerUtils.print(e.getExceptionMessage());
        }
    }

    public void findBook() {

    }

    private void checkIfTheAuthorHasOtherBooksInDatabase(Scanner input, BookEntity bookEntity, AuthorRepository authorRepository) throws DatabaseConnectionException, UnknownException {
        AuthorEntity authorEntity = bookEntity.getAuthorEntity();
        try {
            List<AuthorEntity> authorsWithNoBooksInTheDataBase = authorRepository.getAuthorsWithNoBookInTheDatabase();
            if (authorsWithNoBooksInTheDataBase.contains(authorEntity)) {
                String userOption = CriteriaUtils.getUserConfirmationToDeleteAuthor(input, authorEntity);
                if (userOption.equalsIgnoreCase("y") && askConfirmationFormUser(input)) {
                    authorRepository.deleteAuthor(authorEntity);
                }
            }
        } catch (DatabaseCRUDException e) {
            LoggerUtils.print(e.getExceptionMessage());
        }
    }

}
