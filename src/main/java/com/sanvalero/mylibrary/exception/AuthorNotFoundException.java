package com.sanvalero.mylibrary.exception;

public class AuthorNotFoundException extends RuntimeException{

    public AuthorNotFoundException(long id){
        super("Author not found exception " + id);
    }
    public AuthorNotFoundException(String name, String lastName){
        super("Author: " + name + " " + lastName + " not found exception");}
}
