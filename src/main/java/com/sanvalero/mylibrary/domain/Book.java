package com.sanvalero.mylibrary.domain;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String title;
    @Column
    private String isbn;
    @Column
    private String genre;
    @Column(name = "publishing_house")
    private String publishingHouse;
    @Column
    private String author;
    @Column
    float price;
}

