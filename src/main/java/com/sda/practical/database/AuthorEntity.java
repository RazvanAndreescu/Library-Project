package com.sda.practical.database;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "authors")
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authorId")
    private int Id;

    @Column(name = "firstName")
    private String authorFirstName;

    @Column(name = "lastName")
    private String authorLastName;

    private Date dateOfBirth;

    @OneToMany(mappedBy = "authorEntity", fetch = FetchType.EAGER)
    private List<BookEntity> bookSet;

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<BookEntity> getListOfBooks() {
        return bookSet;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public String toString() {
        return Id +
                ". " + authorFirstName +
                " " + authorLastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorEntity that = (AuthorEntity) o;
        return authorFirstName.equals(that.authorFirstName) && authorLastName.equals(that.authorLastName) && dateOfBirth.equals(that.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorFirstName, authorLastName, dateOfBirth);
    }
}
