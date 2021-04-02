package com.sanvalero.mylibrary.controller;

import com.sanvalero.mylibrary.domain.Author;
import com.sanvalero.mylibrary.exception.AuthorNotFoundException;
import com.sanvalero.mylibrary.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.sanvalero.mylibrary.controller.Response.NOT_FOUND;

@RestController
@Tag(name = "Author", description = "Autores con libros publicados")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Operation(summary = "Obtiene el listado de autores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de autores",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Author.class))))
    })
    @GetMapping(value = "/authors", produces = "application/json")
    public ResponseEntity<Set<Author>> getAuthors(){
        Set<Author> authors = authorService.findAllAuthors();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un autor determinado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el autor",
                content = @Content(schema = @Schema(implementation = Author.class))),
            @ApiResponse(responseCode = "404", description = "No existe el autor",
                content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/authors/{id}", produces = "application/json")
    public ResponseEntity<Author> getAuthor(@PathVariable long id){
        Author author = authorService.findById(id).orElseThrow(()-> new AuthorNotFoundException(id));
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @Operation(summary = "Registra un nuevo autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor registrado",
                content = @Content(schema = @Schema(implementation = Author.class))),
    })
    @PostMapping(value = "/authors", produces = "aplication/json", consumes = "application/json")
    public ResponseEntity<Author> addAuthor(@RequestBody Author author){
        Author addedAuthor = authorService.addAuthor(author);
        return new ResponseEntity<>(addedAuthor, HttpStatus.CREATED);
    }

    @Operation(summary = "Modifica un autor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor modificado",
                content = @Content(schema = @Schema(implementation = Author.class))),
            @ApiResponse(responseCode = "404", description = "No existe el autor",
                content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value = "/authors/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Author> modifyAuthor(@PathVariable long id, @RequestBody Author newAuthor){
        Author author = authorService.modifyAuthor(id, newAuthor);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @Operation(summary = "Borra un autor exitente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor eliminado",
                content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado",
                content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/authors/{id}", produces = "application/json")
    ResponseEntity<Response> deleteAuthor(@PathVariable long id){
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    @ResponseBody
    @ResponseStatus
    public ResponseEntity<Response> handleException(AuthorNotFoundException anfe){
        Response response = Response.errorResponse(NOT_FOUND, anfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
