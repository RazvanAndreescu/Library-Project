package com.sda.practical.utils;

import com.sda.practical.database.AuthorEntity;
import com.sda.practical.database.BookEntity;

import java.util.List;

public class LoggerUtils {

    public static void print(String message){
        System.out.println(message);
    }

    public static void print(List messages){
    messages.forEach(System.out::println);
    }

    public static void print(AuthorEntity authorEntity){
        System.out.println(authorEntity);
    }

    public static void print(BookEntity bookEntity){
        System.out.println(bookEntity);
    }
}


