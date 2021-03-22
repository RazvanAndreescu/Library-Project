package com.sda.practical.utils;

import com.sda.practical.database.AuthorEntity;
import com.sda.practical.database.BookEntity;

import java.sql.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidator {

    public static String validateSQLDataFormat(String text, Scanner input) {
        LoggerUtils.print(text);
        String data = input.nextLine();
        String ePattern = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";
        Pattern pattern = Pattern.compile(ePattern);
        Matcher matcher = pattern.matcher(data);
        if (matcher.matches()) {
            return data;
        } else {
            LoggerUtils.print("Invalid data format!");
            return validateSQLDataFormat(text, input);
        }
    }

    public static boolean validateReleaseDate(AuthorEntity authorEntity, Date date) {
        Date authorDateOfBirth = authorEntity.getDateOfBirth();
        if (!date.after(authorDateOfBirth)) {
            LoggerUtils.print("Release date has to be after the author's date of birth!");
        }
        return date.after(authorDateOfBirth);
    }

    public static boolean validateUpdatedDateAuthor(AuthorEntity authorEntity, Date date) {
        for (BookEntity bookEntity : authorEntity.getListOfBooks()) {
            if(date.after(bookEntity.getReleaseDate())){
                return false;
            }
        }
        return true;
    }
}
