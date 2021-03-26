package com.sanvalero.mylibrary.controller;

import com.sanvalero.mylibrary.domain.Editorial;
import com.sanvalero.mylibrary.exception.EditorialNotFoundException;
import com.sanvalero.mylibrary.service.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.sanvalero.mylibrary.controller.Response.NOT_FOUND;

@RestController
public class EditorialController {

    @Autowired
    EditorialService editorialService;

    @GetMapping("/editorials")
    public ResponseEntity<Set<Editorial>> getEditorials(){
        Set<Editorial> editorials = editorialService.findAllEditorials();
        return new ResponseEntity<>(editorials, HttpStatus.OK);
    }

    @GetMapping("/editorials/{id}")
    public ResponseEntity<Editorial> getEditorial(@PathVariable long id){
        Editorial editorial = editorialService.findById(id).orElseThrow(()-> new EditorialNotFoundException(id));
        return new ResponseEntity<>(editorial, HttpStatus.OK);
    }

    @PostMapping("/editorials")
    public ResponseEntity<Editorial> addEditorial(@RequestBody  Editorial editorial){
        Editorial addedEditorial = editorialService.addEditorial(editorial);
        return new ResponseEntity<>(addedEditorial, HttpStatus.CREATED);
    }

    @PutMapping("/editorials/{id}")
    public ResponseEntity<Editorial> modifyEditorial(@PathVariable long id, @RequestBody Editorial newEditorial){
        Editorial editorial = editorialService.modifyEditorial(id, newEditorial);
        return new ResponseEntity<>(editorial, HttpStatus.OK);
    }

    @DeleteMapping("/editorials/{id}")
    public ResponseEntity<Response> deleteEditorial(@PathVariable long id){
        editorialService.deleteEditorial(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(EditorialNotFoundException.class)
    @ResponseBody
    @ResponseStatus
    public ResponseEntity<Response> handlerException(EditorialNotFoundException enfe){
        Response response = Response.errorResponse(NOT_FOUND, enfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
