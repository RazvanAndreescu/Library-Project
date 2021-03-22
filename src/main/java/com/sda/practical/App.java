package com.sda.practical;
import com.sda.practical.views.MainViewHandler;
import com.sun.xml.bind.v2.TODO;


public class App {
    public static void main(String[] args) {
        /**
         * Book management system
         *
         * -keep in the database the books and the authors from a library
         *
         * -interface for: -add authors, add books for an specific author,
         * print books, authors, books for an specific author
         *
         * Sections:
         * -database, CRUD;
         * -graphic interface;
         * -Business logic: add authors, add book to a specific author
         *
         * Flows:
         * -> Print principal menu -> 1. -> first name, last name (from keyboard) -> Validate them -> Save to database
         * -> Back to the principal menu
         *
         *
         * */
        new MainViewHandler().startApplication();
    }
}
