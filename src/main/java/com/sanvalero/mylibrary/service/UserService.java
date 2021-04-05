package com.sanvalero.mylibrary.service;

import com.sanvalero.mylibrary.domain.Book;
import com.sanvalero.mylibrary.domain.User;

import java.util.Optional;
import java.util.Set;

public interface UserService {

    Set<User> findAllUsers();
    Optional<User> findById(long id);
    User addUser(User user);
    User modifyUser(long id, User user);
    void deleteUser(long id);
    User modifyListBooks(long userId, long bookId);
}