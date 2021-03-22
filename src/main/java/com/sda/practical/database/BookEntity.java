package com.sda.practical.database;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookId")
    private int Id;

    private String bookTitle;

    private Date releaseDate;

    @ManyToOne
    @JoinColumn(name = "authorId")
    private AuthorEntity authorEntity;

    public BookEntity() {
    }

    public BookEntity(String bookTitle, Date releaseDate, AuthorEntity authorEntity) {
        this.authorEntity = authorEntity;
        this.bookTitle = bookTitle;
        this.releaseDate = releaseDate;
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

    public Date getReleaseDate(){
        return releaseDate;
    }

    public void setId(int id) {
        Id = id;
    }

    @Override
    public String toString() {
        return Id + ". " + bookTitle + " by " + authorEntity.getAuthorFirstName() + " " + authorEntity.getAuthorLastName();
    }
}
