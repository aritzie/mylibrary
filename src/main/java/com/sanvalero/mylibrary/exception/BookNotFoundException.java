package com.sanvalero.mylibrary.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(){
        super();
    }

    public BookNotFoundException(String message){
        super((message));
    }

    public BookNotFoundException(long id){
        super("Book not found exception " + id);
    }
}

