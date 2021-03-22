package com.sda.practical.services;

import com.sda.practical.database.AuthorEntity;
import com.sda.practical.database.AuthorRepository;
import com.sda.practical.exceptions.DatabaseCRUDException;
import com.sda.practical.exceptions.DatabaseConnectionException;
import com.sda.practical.exceptions.UnknownException;
import com.sda.practical.utils.*;

import java.sql.Date;
import java.util.Scanner;

public class AuthorService extends BaseService {

    public void addAuthor(Scanner input, AuthorRepository authorRepository) throws UnknownException, DatabaseConnectionException {
        AuthorEntity authorEntity = new AuthorEntity();
        getAuthorNameFromUser(authorEntity, input);
        getAuthorDateOfBirthFromUser(authorEntity, input);
        try {
            authorRepository.add(authorEntity);
        } catch (DatabaseCRUDException e) {
            LoggerUtils.print(e.getExceptionMessage());
        }
        LoggerUtils.print("The Author was successfully added to the database!");
    }

    public void deleteAuthor(Scanner input, AuthorRepository authorRepository) throws UnknownException, DatabaseConnectionException {
        String userCriteria = CriteriaUtils.getUserCriteriaForAuthor(input);
        AuthorEntity authorEntity;
        try {
            authorEntity = generateAuthor(userCriteria, input, authorRepository);
            if (askConfirmationFormUser(input)) {
                authorRepository.delete(authorEntity);
                LoggerUtils.print("The author was successfully deleted from the database!");
            }
        } catch (DatabaseCRUDException e) {
            LoggerUtils.print(e.getExceptionMessage());
        }
    }

    public void deleteAuthorsWithNoBookInDatabase(AuthorRepository authorRepository) throws UnknownException, DatabaseConnectionException {
        try {
            if (authorRepository.getAuthorsWithNoBookInTheDatabase().size() != 0) {
                for (AuthorEntity authorEntity : authorRepository.getAuthorsWithNoBookInTheDatabase()) {
                    authorRepository.delete(authorEntity);
                }
            }
        } catch (DatabaseCRUDException e) {
            LoggerUtils.print(e.getExceptionMessage() + "!3");
        }
        LoggerUtils.print("All authors without any book in the database were deleted!");
    }

    public void printAllAuthors(AuthorRepository authorRepository) throws UnknownException, DatabaseConnectionException {
        try {
            LoggerUtils.print(authorRepository.getAll(GenericTypes.getAuthorEntity()));
        } catch (DatabaseCRUDException e) {
            LoggerUtils.print(e.getExceptionMessage());
        }
    }

    public void findAuthor(AuthorRepository authorRepository, Scanner input) throws DatabaseConnectionException, UnknownException {
        String userCriteria = CriteriaUtils.getUserCriteriaForAuthor(input);
        AuthorEntity authorEntity = null;
        try {
            authorEntity = generateAuthor(userCriteria, input, authorRepository);
        } catch (DatabaseCRUDException e) {
            LoggerUtils.print(e.getExceptionMessage());
        }
        if (authorEntity != null) {
            LoggerUtils.print(authorEntity);
        }
    }

    public void editAuthor(AuthorRepository authorRepository, Scanner input) throws DatabaseConnectionException, UnknownException {
        try {
            LoggerUtils.print(authorRepository.getAll(GenericTypes.getAuthorEntity()));
            if (authorRepository.getAll(GenericTypes.getAuthorEntity()).size() != 0) {
                int authorId = KeyboardUtils.readNumber(input, "Insert authorId: ");
                AuthorEntity authorEntity = authorRepository.getById(authorId, GenericTypes.getAuthorEntity());
                if (authorEntity != null) {
                    String userCriteria = CriteriaUtils.getUserCriteriaForUpdateAuthor(input);
                    if (userCriteria.equalsIgnoreCase("n")) {
                        getAuthorNameFromUser(authorEntity, input);
                    } else {
                        getAuthorUpdatedDateOfBirthFromUser(authorEntity, input);
                    }
                    authorRepository.update(authorEntity);
                    LoggerUtils.print("The author was successfully updated!");
                }
            }
        } catch (DatabaseCRUDException e) {
            LoggerUtils.print(e.getExceptionMessage());
        }
    }

    private AuthorEntity generateAuthor(String userCriteria, Scanner input, AuthorRepository authorRepository) throws DatabaseCRUDException, UnknownException, DatabaseConnectionException {
        AuthorEntity authorEntity = null;
        LoggerUtils.print(authorRepository.getAll(GenericTypes.getAuthorEntity()));
        if (userCriteria.equalsIgnoreCase("i")) {
            int authorId = KeyboardUtils.readNumber(input, "Insert authorId: ");
            authorEntity = authorRepository.getById(authorId, GenericTypes.getAuthorEntity());
        } else {
            LoggerUtils.print("Insert author's first name: ");
            String firstName = input.nextLine();
            LoggerUtils.print("Insert author's last name: ");
            String lastName = input.nextLine();
            authorEntity = authorRepository.getAuthorByName(firstName, lastName);
        }
        return authorEntity;
    }

    private void getAuthorNameFromUser(AuthorEntity authorEntity, Scanner input) {
        LoggerUtils.print("Insert First Name");
        String firstName = input.nextLine();
        LoggerUtils.print("Insert Last Name");
        String lastName = input.nextLine();
        authorEntity.setAuthorFirstName(firstName);
        authorEntity.setAuthorLastName(lastName);
    }

    private void getAuthorDateOfBirthFromUser(AuthorEntity authorEntity, Scanner input) {
        String date = DataValidator.validateSQLDataFormat("Insert author's date of birth", input);
        authorEntity.setDateOfBirth(Date.valueOf(date));
    }

    private void getAuthorUpdatedDateOfBirthFromUser(AuthorEntity authorEntity, Scanner input) {
        while (true) {
            String dateOfBirth = DataValidator.validateSQLDataFormat("Insert the new date of birth: ", input);
            if (DataValidator.validateUpdatedDateAuthor(authorEntity, Date.valueOf(dateOfBirth))) {
                authorEntity.setDateOfBirth(Date.valueOf(dateOfBirth));
                break;
            }
            LoggerUtils.print("The author's date of birth has to be before his first book's release date!");
        }
    }
}
