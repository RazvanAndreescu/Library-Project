package com.sda.practical.utils;

import com.sda.practical.database.AuthorEntity;

import java.util.Scanner;

public class CriteriaUtils {
    private static String invalidAnswer = "Your answer is not valid";

    public static String getUserCriteriaForAuthor(Scanner input) {
        LoggerUtils.print("By id(i) or by name(n)?");
        String userFeedback = "";
        while (true) {
            userFeedback = input.nextLine();
            if (userFeedback.equalsIgnoreCase("i") || userFeedback.equalsIgnoreCase("n")) {
                break;
            } else {
                LoggerUtils.print(invalidAnswer);
            }
        }
        return userFeedback;
    }

    public static String getUserCriteriaForBook(Scanner input) {
        LoggerUtils.print("By Id(i) or by title(t)?");
        String userFeedback = " ";
        while (true) {
            userFeedback = input.nextLine();
            if (userFeedback.equalsIgnoreCase("i") || userFeedback.equalsIgnoreCase("t")) {
                break;
            } else {
                LoggerUtils.print(invalidAnswer);
            }
        }
        return userFeedback;
    }

    public static String getUserConfirmationToDeleteAuthor(Scanner input, AuthorEntity authorEntity) {
        LoggerUtils.print(authorEntity.getAuthorFirstName() + " " + authorEntity.getAuthorLastName() + " has not other books in the database!" +
                " Do you want to delete him from the database? (y or n)");
        String userFeedBack = "";
        while (true) {
            userFeedBack = input.nextLine();
            if(userFeedBack.equalsIgnoreCase("y") || userFeedBack.equalsIgnoreCase("n")){
                break;
            } else if (!userFeedBack.equalsIgnoreCase("")){
                LoggerUtils.print(invalidAnswer); //TODO it is printed before the first answer
            }
        }
        return userFeedBack;
    }
}

