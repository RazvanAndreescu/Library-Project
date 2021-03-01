package com.sda.practical;

import com.sda.practical.test1.MySQLConnection;
import com.sda.practical.test1.MyTestClass;
import com.sda.practical.test1.PostgreSQLConnection;
import com.sda.practical.utils.DataValidator;
import com.sda.practical.views.MainViewHandler;

import javax.persistence.PersistenceException;
import java.io.PrintWriter;
import java.io.StringWriter;

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
        /*new MainViewHandler().startApplication();*/
        MySQLConnection mySQLConnection = new MySQLConnection();
        MyTestClass myTestClass=new MyTestClass();
        PostgreSQLConnection postgreSQLConnection = new PostgreSQLConnection();
        myTestClass.test(postgreSQLConnection);
    }
}
