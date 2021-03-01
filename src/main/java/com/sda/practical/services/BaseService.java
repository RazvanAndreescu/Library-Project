package com.sda.practical.services;

import com.sda.practical.utils.LoggerUtils;

import java.util.List;
import java.util.Scanner;

public abstract class BaseService<T> {

    protected boolean askConfirmationFormUser(Scanner input) {
            LoggerUtils.print("Are you sure? (y, n)");
            String userFeedback = input.next();
            if (userFeedback.equalsIgnoreCase("y")) {
                return true;
            } else if (userFeedback.equalsIgnoreCase("n")) {
                return false;
            } else {
                return askConfirmationFormUser(input);
            }
        }
    }

