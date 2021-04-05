package com.sanvalero.mylibrary.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(long id){ super("User not found exception " + id); }
}
