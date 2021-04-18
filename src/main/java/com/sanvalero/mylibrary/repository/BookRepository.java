package com.sanvalero.mylibrary.repository;

import com.sanvalero.mylibrary.domain.Author;
import com.sanvalero.mylibrary.domain.Book;
import com.sanvalero.mylibrary.domain.Editorial;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    Set<Book> findAll();
    Set<Book> findByTitleContainingAndRateGreaterThanEqual(String cad, float rate);
    Set<Book> findByGenreAndRateGreaterThanEqual(String genre, float rate);
    Set<Book> findByRateGreaterThanEqual(float rate);
    Set<Book> findByTitleContainingAndGenreAndRateGreaterThanEqual(String genre, String cad, float rate);
    Set<Book> findByAuthor(Author author);
    Set<Book> findByEditorial(Editorial editorial);
    Optional<Book> findByTitle(String title);
}
