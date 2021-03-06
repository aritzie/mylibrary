package com.sanvalero.mylibrary.service;

import com.sanvalero.mylibrary.domain.Author;
import com.sanvalero.mylibrary.domain.Book;
import com.sanvalero.mylibrary.domain.Editorial;
import com.sanvalero.mylibrary.domain.dto.BookDTO;
import com.sanvalero.mylibrary.exception.AuthorNotFoundException;
import com.sanvalero.mylibrary.exception.BookNotFoundException;
import com.sanvalero.mylibrary.exception.EditorialNotFoundException;
import com.sanvalero.mylibrary.repository.AuthorRepository;
import com.sanvalero.mylibrary.repository.BookRepository;
import com.sanvalero.mylibrary.repository.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class BookServiceImp implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private EditorialRepository editorialRepository;

    @Override
    public Set<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Set<Book> findByParameters(Book book) {
        if(book.getTitle().equals("") && book.getGenre().equals("") && book.getRate()==0){
            return findAllBooks();
        }
        else if(!book.getTitle().equals("") && book.getGenre().equals("")){
            return bookRepository.findByTitleContainingAndRateGreaterThanEqual(book.getTitle(), book.getRate());
        }
        else if(book.getTitle().equals("") && !book.getGenre().equals("")){
            return bookRepository.findByGenreAndRateGreaterThanEqual(book.getGenre(), book.getRate());
        }
        else if(book.getTitle().equals("") && book.getGenre().equals("") && book.getRate()!=0){
            return bookRepository.findByRateGreaterThanEqual(book.getRate());
        }
        else {
            return bookRepository.findByTitleContainingAndGenreAndRateGreaterThanEqual(book.getTitle(), book.getGenre(),
                    book.getRate());
        }
    }

    @Override
    public Set<Book> findByAuthor(Author author) {
        return bookRepository.findByAuthor(author);
    }

    @Override
    public Set<Book> findByEditorial(Editorial editorial) {
        return bookRepository.findByEditorial(editorial);
    }

    @Override
    public Optional<Book> findById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book addBook(BookDTO bookDTO) {
        if(bookDTO.getTitle().equals("") || bookDTO.getAuthorName().equals("")
                || bookDTO.getAuthorLastName().equals("") || bookDTO.getEditorial().equals("")
                || bookDTO.getTitle()==null || bookDTO.getAuthorName()==null
                || bookDTO.getAuthorLastName()==null || bookDTO.getEditorial()==null) return null;

        Author author = authorRepository.findByNameAndLastName(bookDTO.getAuthorName(), bookDTO.getAuthorLastName())
                .orElseThrow(()-> new AuthorNotFoundException(bookDTO.getAuthorName(), bookDTO.getAuthorLastName()));

        Editorial editorial = editorialRepository.findByName(bookDTO.getEditorial())
                .orElseThrow(()-> new EditorialNotFoundException(bookDTO.getEditorial()));

        Book newBook = new Book();
        newBook.setAuthor(author);
        newBook.setEditorial(editorial);
        newBook.setTitle(bookDTO.getTitle());

        int authorPublications = author.getPublications();
        int editorialPublications = editorial.getPublications();
        author.setPublications(authorPublications+1);
        editorial.setPublications(editorialPublications+1);
        authorRepository.save(author);
        editorialRepository.save(editorial);

        return bookRepository.save(newBook);
    }

    @Override
    public Book modifyBook(long id, Book newBook) {
        Book book = bookRepository.findById(id).orElseThrow(()-> new BookNotFoundException(id));
        newBook.setId(book.getId());
        return bookRepository.save(newBook);
    }

    @Override
    public void deleteBook(long id) {
        bookRepository.findById(id).orElseThrow(()-> new BookNotFoundException(id));
        bookRepository.deleteById(id);
    }

    @Override
    public Book modifyBookRate(long id, float newRate) {
        Book book = bookRepository.findById(id).orElseThrow(()-> new BookNotFoundException(id));
        book.setRate(newRate);
        return bookRepository.save(book);
    }
}
