package com.sda.practical.views;

import java.util.ArrayList;
import java.util.List;

//It will keep our menus
public class MenuHandler {

    private List<String> getMainMenu() {
        List<String> menuList = new ArrayList<>();
        menuList.add("1. Add author");
        menuList.add("2. Delete author");
        menuList.add("3. Add book");
        menuList.add("4. Delete book");
        menuList.add("5. Print all authors");
        menuList.add("6. Print all books");
        menuList.add("7. Find author");
        menuList.add("8. Find book");
        menuList.add("9. Exit Application");
        return menuList;
    }

    public void printMainMenu(){
        for(String option : getMainMenu()){
            System.out.print(option+"     ");
        }
    }
}
