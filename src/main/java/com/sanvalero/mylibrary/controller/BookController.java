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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.sanvalero.mylibrary.controller.Response.NOT_FOUND;

@RestController
@Tag(name = "Books", description = "Catálogo de libros")
public class BookController {

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
                                              @RequestParam(value = "rate", defaultValue = "0") float rate)
    {
        Book searchBook = new Book();
        searchBook.setTitle(title);
        searchBook.setGenre(genre);
        searchBook.setRate(rate);
        Set<Book> books =  bookService.findByParameters(searchBook);
        if(books.isEmpty()) new BookNotFoundException();
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
        Book book = bookService.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @Operation(summary = "Registra un nuevo libro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Libro registrado",
                    content = @Content(schema = @Schema(implementation = Book.class)))
    })
    @PostMapping(value = "/books", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Book> addBook(@RequestBody BookDTO bookDTO){
        Book addedBook = bookService.addBook(bookDTO);
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
        Book book = bookService.modifyBook(id, newBook);
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
        bookService.deleteBook(id);
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
        Book book = bookService.modifyBookRate(id, newRate);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseBody
    @ResponseStatus
    public ResponseEntity<Response> handleException(BookNotFoundException bnfe){
        Response response = Response.errorResponse(NOT_FOUND, bnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
