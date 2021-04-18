package com.sanvalero.mylibrary.service;

import com.sanvalero.mylibrary.domain.Book;
import com.sanvalero.mylibrary.domain.User;
import com.sanvalero.mylibrary.exception.BookNotFoundException;
import com.sanvalero.mylibrary.exception.UserNotFoundException;
import com.sanvalero.mylibrary.repository.BookRepository;
import com.sanvalero.mylibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Override
    public Set<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User modifyUser(long id, User newUser) {
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
        newUser.setId(user.getId());
        return userRepository.save(newUser);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
        userRepository.deleteById(id);
    }

    @Override
    public User modifyListBooks(long userId, long bookId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new BookNotFoundException(bookId));
        user.addBook(book);
        int readBooks = user.getReadBooks();
        user.setReadBooks(readBooks+1);
        return userRepository.save(user);
    }
}
