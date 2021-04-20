package com.sanvalero.mylibrary.service;

import com.sanvalero.mylibrary.domain.Author;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface AuthorService {

    Set<Author> findAllAuthors();
    Optional<Author> findById(long id);
    Author addAuthor(Author author);
    Author modifyAuthor(long id, Author author);
    void deleteAuthor(long id);
    Author modifyAuthorName(long id, String newName);
}
