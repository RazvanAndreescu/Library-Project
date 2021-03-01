package com.sda.practical.utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class KeyboardUtils {

    public static int readNumber(Scanner input, String message) {
        System.out.println();
        Integer value=null;
        while (value==null){
            try {
                LoggerUtils.print(message);
                value=input.nextInt();
                input.nextLine();
                return value;
            } catch (InputMismatchException inputMismatchException){
                System.out.println("Invalid input");
                input.nextLine();
                value=null;
            }
        }
        return value;
    }
}
