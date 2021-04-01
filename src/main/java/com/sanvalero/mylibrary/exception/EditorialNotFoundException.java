package com.sanvalero.mylibrary.exception;

public class EditorialNotFoundException extends RuntimeException {

    public EditorialNotFoundException(long id){
        super("Editorial not found exception " + id);
    }
    public EditorialNotFoundException(String name){super ("Editorial not found exception" + name);}
}
