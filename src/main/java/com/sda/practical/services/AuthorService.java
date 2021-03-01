package com.sda.practical.services;

import com.sda.practical.database.AuthorEntity;
import com.sda.practical.database.AuthorRepository;
import com.sda.practical.exceptions.DatabaseCRUDException;
import com.sda.practical.exceptions.DatabaseConnectionException;
import com.sda.practical.exceptions.UnknownException;
import com.sda.practical.utils.CriteriaUtils;
import com.sda.practical.utils.DataValidator;
import com.sda.practical.utils.LoggerUtils;

import java.sql.Date;
import java.util.Scanner;

public class AuthorService extends BaseService {

    public void addAuthor(Scanner input, AuthorRepository authorRepository) throws UnknownException, DatabaseConnectionException {
        LoggerUtils.print("Insert First Name");
        String firstName = input.nextLine();
        LoggerUtils.print("Insert Last Name");
        String lastName = input.nextLine();
        String date = DataValidator.validateSQLDataFormat("Insert author's date of birth", input);
        Date dateOfBirth = Date.valueOf(date);
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setAuthorFirstName(firstName);
        authorEntity.setAuthorLastName(lastName);
        authorEntity.setDateOfBirth(dateOfBirth);
        try {
            authorRepository.addAuthor(authorEntity);
        } catch (DatabaseCRUDException e) {
            LoggerUtils.print(e.getExceptionMessage());
        }
        LoggerUtils.print("The Author was successfully added to the database!");
    }

    public void deleteAuthor(Scanner input, AuthorRepository authorRepository) throws UnknownException, DatabaseConnectionException {
        String userCriteria = CriteriaUtils.getUserCriteriaForAuthor(input);
        AuthorEntity authorEntity;
        try {
            LoggerUtils.print(authorRepository.getAllAuthors());
            if (userCriteria.equalsIgnoreCase("i")) {
                LoggerUtils.print("Insert authorId: ");
                int authorId = input.nextInt();
                authorEntity = authorRepository.getAuthorById(authorId);
            } else {
                LoggerUtils.print("Insert author's first name: ");
                String firstName = input.nextLine();
                LoggerUtils.print("Insert author's last name: ");
                String lastName = input.nextLine();
                authorEntity = authorRepository.getAuthorByName(firstName, lastName);
            }

            if (askConfirmationFormUser(input)) {
                authorRepository.deleteAuthor(authorEntity);
                LoggerUtils.print("The author was successfully deleted from the database!");
            }
        } catch (DatabaseCRUDException e) {
            LoggerUtils.print(e.getExceptionMessage());
        }
    }

    public void deleteAuthorsWithNoBookInDatabase(AuthorRepository authorRepository) throws UnknownException, DatabaseConnectionException {
        try {
            if (authorRepository.getAuthorsWithNoBookInTheDatabase().size() == 0) {
                return;
            }
            for (AuthorEntity authorEntity : authorRepository.getAuthorsWithNoBookInTheDatabase()) {

                authorRepository.deleteAuthor(authorEntity);
            }
        } catch (DatabaseCRUDException e) {
            LoggerUtils.print(e.getExceptionMessage());
        }
        LoggerUtils.print("All authors without any book in the database were deleted!");
    }

    public void printAllAuthors(AuthorRepository authorRepository) throws UnknownException, DatabaseConnectionException {
        try {
            LoggerUtils.print(authorRepository.getAllAuthors());
        } catch (DatabaseCRUDException e) {
            LoggerUtils.print(e.getExceptionMessage());
        }
    }

    public void findAuthor(AuthorRepository authorRepository, Scanner input) {
        String userCriteria = CriteriaUtils.getUserCriteriaForAuthor(input);
        if (userCriteria.equalsIgnoreCase("i")) {
            LoggerUtils.print("Insert authorId: ");
            int authorId = input.nextInt();
            input.nextLine();
            AuthorEntity authorEntity = authorRepository.getAuthorById(authorId);
        }
    }
}
