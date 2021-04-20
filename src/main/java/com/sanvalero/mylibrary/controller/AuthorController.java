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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.sanvalero.mylibrary.controller.Response.NOT_FOUND;

@RestController
@Tag(name = "Author", description = "Autores con libros publicados")
public class AuthorController {

    private final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    @Autowired
    private AuthorService authorService;

    @Operation(summary = "Obtiene el listado de autores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de autores",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Author.class))))
    })
    @GetMapping(value = "/authors", produces = "application/json")
    public ResponseEntity<Set<Author>> getAuthors(){
        logger.info("inicio getAuthors");
        Set<Author> authors = authorService.findAllAuthors();
        logger.info("fin getAuthors");
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
        logger.info("inicio getAuthor");
        Author author = authorService.findById(id).orElseThrow(()-> new AuthorNotFoundException(id));
        logger.info("fin getAuthor");
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @Operation(summary = "Registra un nuevo autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Autor registrado",
                    content = @Content(schema = @Schema(implementation = Author.class))),
    })
    @PostMapping(value = "/authors", produces = "aplication/json", consumes = "application/json")
    public ResponseEntity<Author> addAuthor(@RequestBody Author author){
        logger.info("inicio addAuthor");
        Author addedAuthor = authorService.addAuthor(author);
        logger.info("fin addAuthor");
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
        logger.info("inicio modifyAuthor");
        Author author = authorService.modifyAuthor(id, newAuthor);
        logger.info("fin modifyAuthor");
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
        logger.info("inicio deleteAuthor");
        authorService.deleteAuthor(id);
        logger.info("fin deleteAuthor");
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @Operation(summary = "Modifica el nombre del autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor modificado",
                    content = @Content(schema = @Schema(implementation = Author.class))),
            @ApiResponse(responseCode = "404", description = "No existe el autor",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PatchMapping(value = "/authors/{id}/change-name")
    ResponseEntity<Author> changeName(@PathVariable long id, @RequestParam(value = "newName") String newName){
        logger.info("inicio changeName");
        Author author = authorService.modifyAuthorName(id, newName);
        logger.info("fin changeName");
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    @ResponseBody
    @ResponseStatus
    public ResponseEntity<Response> handleException(AuthorNotFoundException anfe){
        Response response = Response.errorResponse(NOT_FOUND, anfe.getMessage());
        logger.info(anfe.getMessage(), anfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
