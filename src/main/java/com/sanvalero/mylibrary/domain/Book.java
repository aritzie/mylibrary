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
    long id;
    @Column
    String title;
    @Column
    String isbn;
    @Column
    String genre;
    @Column(name = "publishing_house")
    String publishingHouse;
    @Column
    String author;
    @Column
    float price;
}

