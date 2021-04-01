package com.sanvalero.mylibrary.service;

import com.sanvalero.mylibrary.domain.Author;
import com.sanvalero.mylibrary.exception.AuthorNotFoundException;
import com.sanvalero.mylibrary.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AuthorServiceImp implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Set<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author modifyAuthor(long id, Author newAuthor) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
        newAuthor.setId(author.getId());
        return authorRepository.save(newAuthor);
    }

    @Override
    public void deleteAuthor(long id) {
        authorRepository.findById(id).orElseThrow(()-> new AuthorNotFoundException(id));
        authorRepository.deleteById(id);
    }

}
