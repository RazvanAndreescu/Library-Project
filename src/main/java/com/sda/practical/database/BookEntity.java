package com.sda.practical.database;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    private String bookTitle;

    private Date releaseDate;

    @ManyToOne
    @JoinColumn(name = "authorId")
    private AuthorEntity authorEntity;

    public BookEntity(){}

    public BookEntity(String bookTitle, Date releaseDate, AuthorEntity authorEntity){
        this.authorEntity=authorEntity;
        this.bookTitle=bookTitle;
        this.releaseDate=releaseDate;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setAuthorEntity(AuthorEntity authorEntity) {
        this.authorEntity = authorEntity;
    }

    public AuthorEntity getAuthorEntity() {
        return authorEntity;
    }

    @Override
    public String toString() {
        return bookId + ". " + bookTitle + " by " + authorEntity.getAuthorFirstName() + " " + authorEntity.getAuthorLastName();
    }
}
