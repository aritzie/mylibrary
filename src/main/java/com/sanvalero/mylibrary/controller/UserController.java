package com.sanvalero.mylibrary.controller;

import com.sanvalero.mylibrary.domain.Book;
import com.sanvalero.mylibrary.domain.User;
import com.sanvalero.mylibrary.exception.UserNotFoundException;
import com.sanvalero.mylibrary.service.UserService;
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
@Tag(name = "User", description = "Usuarios de la aplicación")
public class UserController {

    @Autowired
    UserService userService;

    @Operation(summary = "Obtiene un listado de los usuarios de la aplicación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de usuarios",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = User.class))))
    })
    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<Set<User>> getUsers(){
        Set<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un usuario determinado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario existente",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "No existe el usuario",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/users{id}", produces = "application/json")
    public ResponseEntity<User> getUser(@PathVariable long id){
        User user = userService.findById(id).orElseThrow(()-> new UserNotFoundException(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Registra un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario registrado",
                    content = @Content(schema = @Schema(implementation = User.class)))
    })
    @PostMapping(value = "/users", produces = "application/json", consumes = "application/json")
    public ResponseEntity<User> addUser(@RequestBody User user){
        User addedUser = userService.addUser(user);
        return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
    }

    @Operation(summary = "Modifica un usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario modificado",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "No existe el usuario",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value = "/users/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<User> modifyUser(@PathVariable long id, @RequestBody User newUser){
        User user = userService.modifyUser(id, newUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "No existe el usuario",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/users/{id}", produces = "appilcation/json", consumes = "application/json")
    public ResponseEntity<Response> deleteUser(@PathVariable long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @PatchMapping(value = "/users/{userId}/{bookId}/addBook")
    public ResponseEntity<User> modifyListBooks(@PathVariable long userId, @PathVariable long bookId){
        User user =userService.modifyListBooks(userId, bookId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    @ResponseStatus
    public ResponseEntity<Response> handlerException(UserNotFoundException unfe){
        Response response = Response.errorResponse(NOT_FOUND, unfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
