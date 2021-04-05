package com.sanvalero.mylibrary.controller;

import com.sanvalero.mylibrary.domain.Book;
import com.sanvalero.mylibrary.domain.dto.BookDTO;
import com.sanvalero.mylibrary.exception.BookNotFoundException;
import com.sanvalero.mylibrary.service.BookService;
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
@Tag(name = "Books", description = "Catálogo de libros")
public class BookController {

    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @Operation(summary = "Obtiene el listado de libros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de libros",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Book.class)))),
            @ApiResponse(responseCode = "404", description = "Los libros no coinciden",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/books", produces = "application/json")
    public ResponseEntity<Set<Book>> getBooks(@RequestParam(value = "title", defaultValue = "") String title,
                                              @RequestParam(value = "genre", defaultValue = "") String genre,
                                              @RequestParam(value = "rate", defaultValue = "0") float rate) {
        logger.info("inicio getBooks");
        Book searchBook = new Book();
        searchBook.setTitle(title);
        searchBook.setGenre(genre);
        searchBook.setRate(rate);
        Set<Book> books =  bookService.findByParameters(searchBook);
        if(books.isEmpty()) new BookNotFoundException();
        logger.info("fin getBooks");
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un libro determinado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el libro",
                    content = @Content(schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "404", description = "No existe el libro",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/books/{id}", produces = "application/json")
    public ResponseEntity<Book> getBook(@PathVariable long id){
        logger.info("inicio getBook");
        Book book = bookService.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        logger.info("fin getBook");
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @Operation(summary = "Registra un nuevo libro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Libro registrado",
                    content = @Content(schema = @Schema(implementation = Book.class)))
    })
    @PostMapping(value = "/books", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Book> addBook(@RequestBody BookDTO bookDTO){
        logger.info("inicio addBook");
        Book addedBook = bookService.addBook(bookDTO);
        logger.info("fin addBook");
        return new ResponseEntity<>(addedBook, HttpStatus.CREATED);
    }

    @Operation(summary = "Modifica un libro del catálogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro modificado",
                    content = @Content(schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "404", description = "No existe el libro",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value ="/books/{id}", produces = "application/json",consumes = "application/json")
    public ResponseEntity<Book> modifyBook(@PathVariable long id, @RequestBody Book newBook){
        logger.info("inicio modifyBook");
        Book book = bookService.modifyBook(id, newBook);
        logger.info("fin modifyBook");
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un libro del catálogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro eliminado",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "No existe el libro",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/books/{id}", produces = "application/json")
    public ResponseEntity<Response> deleteBook(@PathVariable long id){
        logger.info("inicio deleteBook");
        bookService.deleteBook(id);
        logger.info("fin deleteBook");
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @Operation(summary = "Modifica la calificación del libro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro modicado",
                    content = @Content(schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "404", description = "No existe el libro",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PatchMapping(value = "/books/{id}/change-rate", produces = "application/json")
    public ResponseEntity<Book> changeBookName(@PathVariable long id, @RequestParam(value = "newRate") float newRate){
        logger.info("inicio changeBookRate");
        Book book = bookService.modifyBookRate(id, newRate);
        logger.info("fin changeBookRate");
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseBody
    @ResponseStatus
    public ResponseEntity<Response> handleException(BookNotFoundException bnfe){
        Response response = Response.errorResponse(NOT_FOUND, bnfe.getMessage());
        logger.info(bnfe.getMessage(), bnfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
