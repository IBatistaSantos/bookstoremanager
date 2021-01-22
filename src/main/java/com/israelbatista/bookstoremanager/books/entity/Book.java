package com.israelbatista.bookstoremanager.books.entity;

import com.israelbatista.bookstoremanager.author.entity.Author;
import com.israelbatista.bookstoremanager.entity.Auditable;
import com.israelbatista.bookstoremanager.publishers.entity.Publisher;
import com.israelbatista.bookstoremanager.users.entity.User;
import com.israelbatista.bookstoremanager.users.enums.Gender;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@Entity
public class Book extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;


    @Column(nullable = false)
    private int isbn;

    @Column(columnDefinition = "integer default 0")
    private int pages;

    @Column(columnDefinition = "integer default 0")
    private int chapters;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Author author;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Publisher publisher;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private User user;
}
