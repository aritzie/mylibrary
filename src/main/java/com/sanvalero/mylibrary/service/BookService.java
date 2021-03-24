package com.sanvalero.mylibrary.service;

import com.sanvalero.mylibrary.domain.Book;

import java.util.Optional;
import java.util.Set;

public interface BookService {

    Set<Book> findAllBooks();
    Optional<Book> findById(long id);
    Book addBook(Book book);
    Book modifyBook(long id, Book book);
    void deleteBook(long id);
}
