package com.sda.practical.views;

import com.sda.practical.database.AuthorRepository;
import com.sda.practical.database.BookRepository;
import com.sda.practical.exceptions.DatabaseCRUDException;
import com.sda.practical.exceptions.DatabaseConnectionException;
import com.sda.practical.exceptions.UnknownException;
import com.sda.practical.services.AuthorService;
import com.sda.practical.services.BookService;
import com.sda.practical.utils.KeyboardUtils;
import com.sda.practical.utils.LoggerUtils;

import java.util.Scanner;

public class MainViewHandler {

    public void startApplication() {
        LoggerUtils.print("Start Application");
        Scanner input = new Scanner(System.in);
        MenuHandler menuHandler = new MenuHandler();
        AuthorService authorService = new AuthorService();
        AuthorRepository authorRepository = new AuthorRepository();
        BookService bookService = new BookService();
        BookRepository bookRepository = new BookRepository();
        int userOption = 0;
        while (userOption != 9) {
            menuHandler.printMainMenu();
            userOption = KeyboardUtils.readNumber(input, "Insert an option");
            option(authorService, authorRepository, bookService, bookRepository, input, userOption);
        }
        try {
            authorService.deleteAuthorsWithNoBookInDatabase(authorRepository);
        } catch (UnknownException e) {
            LoggerUtils.print(e.getExceptionMessage());
        } catch (DatabaseConnectionException e) {
            LoggerUtils.print(e.getExceptionMessage());
        }
    }

    private static void option(AuthorService authorService, AuthorRepository authorRepository, BookService bookService,
                               BookRepository bookRepository, Scanner input, int userOption) {
        try {
            switch (userOption) {
                case 1:
                    authorService.addAuthor(input, authorRepository);
                    break;
                case 2:
                    authorService.deleteAuthor(input, authorRepository);
                    break;
                case 3:
                    bookService.addBook(input, bookRepository, authorRepository);
                    break;
                case 4:
                    bookService.deleteBook(input, bookRepository, authorRepository);
                    break;
                case 5:
                    authorService.printAllAuthors(authorRepository);
                    break;
                case 6:
                    bookService.printAllBooks(bookRepository);
                    break;
                case 7:
                    authorService.findAuthor(authorRepository, input);
                    break;
                case 8:
                    bookService.findBook(input, bookRepository);
                    break;
                case 9:
                    break;
                case 10:
                    authorService.editAuthor(authorRepository, input);
                    break;
                case 11:
                    bookService.editBook(input, bookRepository, authorRepository);
                    break;
                default:
                    LoggerUtils.print("Return to the main menu");
                    break;
            }
        } catch (DatabaseConnectionException e) {
            LoggerUtils.print(e.getExceptionMessage());
        } catch (UnknownException e) {
            LoggerUtils.print(e.getExceptionMessage());
        }

    }
}
