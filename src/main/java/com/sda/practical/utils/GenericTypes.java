package com.sda.practical.utils;

import com.sda.practical.database.AuthorEntity;
import com.sda.practical.database.BookEntity;

public class GenericTypes {
    private static final AuthorEntity authorEntity = new AuthorEntity();
    private static final BookEntity bookEntity = new BookEntity();

    public static AuthorEntity getAuthorEntity() {
        return authorEntity;
    }

    public static BookEntity getBookEntity() {
        return bookEntity;
    }
}
