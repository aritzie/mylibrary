package com.sanvalero.mylibrary.service;

import com.sanvalero.mylibrary.domain.Author;
import com.sanvalero.mylibrary.domain.Book;
import com.sanvalero.mylibrary.domain.Editorial;
import com.sanvalero.mylibrary.domain.dto.BookDTO;

import java.util.Optional;
import java.util.Set;

public interface BookService {

    Set<Book> findAllBooks();
    Set<Book> findByParameters(Book book);
    Set<Book> findByAuthor(Author author);
    Set<Book> findByEditorial(Editorial editorial);
    Optional<Book> findById(long id);
    Book addBook(BookDTO bookDTO);
    Book modifyBook(long id, Book book);
    void deleteBook(long id);
    Book modifyBookRate(long id, float newRate);
}
