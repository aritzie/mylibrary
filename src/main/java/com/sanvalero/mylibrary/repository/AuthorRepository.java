package com.sanvalero.mylibrary.repository;

import com.sanvalero.mylibrary.domain.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    Set<Author> findAll();
    Optional<Author> findByNameAndLastName(String name, String lastName);
}
