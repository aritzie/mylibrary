package com.sanvalero.mylibrary.exception;

public class AuthorNotFoundException extends RuntimeException{

    public AuthorNotFoundException(long id){
        super("Author not found exception " + id);
    }
}
