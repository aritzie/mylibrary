package com.sanvalero.mylibrary.controller;

import com.sanvalero.mylibrary.domain.Editorial;
import com.sanvalero.mylibrary.exception.EditorialNotFoundException;
import com.sanvalero.mylibrary.service.EditorialService;
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

import javax.websocket.server.PathParam;
import java.util.Set;

import static com.sanvalero.mylibrary.controller.Response.NOT_FOUND;

@RestController
@Tag(name = "Editorial", description = "Editoriales con libros publicados")
public class EditorialController {

    private final Logger logger = LoggerFactory.getLogger(EditorialController.class);

    @Autowired
    EditorialService editorialService;

    @Operation(summary = "Obtiene un listado de las editoriales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de editoriales",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Editorial.class))))
    })
    @GetMapping(value = "/editorials", produces = "application/json")
    public ResponseEntity<Set<Editorial>> getEditorials(){
        logger.info("inicio getEditorials");
        Set<Editorial> editorials = editorialService.findAllEditorials();
        logger.info("fin getEditorials");
        return new ResponseEntity<>(editorials, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene una editorial determinada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Editorial existente",
                    content = @Content(schema = @Schema(implementation = Editorial.class))),
            @ApiResponse(responseCode = "404", description = "No existe editorial",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/editorials/{id}", produces = "application/json")
    public ResponseEntity<Editorial> getEditorial(@PathVariable long id){
        logger.info("inicio getEditorial");
        Editorial editorial = editorialService.findById(id).orElseThrow(()-> new EditorialNotFoundException(id));
        logger.info("fin getEditorial");
        return new ResponseEntity<>(editorial, HttpStatus.OK);
    }

    @Operation(summary = "Registra una nueva editorial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Editorial registrada",
                    content = @Content(schema = @Schema(implementation = Editorial.class)))
    })
    @PostMapping(value = "/editorials", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Editorial> addEditorial(@RequestBody  Editorial editorial){
        logger.info("inicio addEditorial");
        Editorial addedEditorial = editorialService.addEditorial(editorial);
        logger.info("fin addEditorial");
        return new ResponseEntity<>(addedEditorial, HttpStatus.CREATED);
    }

    @Operation(summary = "Modifica una editorial existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Editorial modificada",
                    content = @Content(schema = @Schema(implementation = Editorial.class))),
            @ApiResponse(responseCode = "404", description = "No existe editorial",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value = "/editorials/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Editorial> modifyEditorial(@PathVariable long id, @RequestBody Editorial newEditorial){
        logger.info("inicio modifyEditorial");
        Editorial editorial = editorialService.modifyEditorial(id, newEditorial);
        logger.info("fin modifyEditorial");
        return new ResponseEntity<>(editorial, HttpStatus.OK);
    }

    @Operation(summary = "Borra una editorial existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Editorial eliminada",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "No existe editorial",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/editorials/{id}", produces = "application/json")
    public ResponseEntity<Response> deleteEditorial(@PathVariable long id){
        logger.info("inicio deleteEditorial");
        editorialService.deleteEditorial(id);
        logger.info("fin deleteEditorial");
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @Operation(summary = "Modifica el nombre de la editorial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nombre de la editorial modificado",
                    content = @Content(schema = @Schema(implementation = Editorial.class))),
            @ApiResponse(responseCode = "404", description = "No existe la editorial",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PatchMapping(value = "editorials/{id}/change-name")
    public ResponseEntity<Editorial> changeEditorialName(@PathVariable long id,
                                                         @RequestParam(value = "newName", defaultValue = "") String newName){
        logger.info("inicio changeEditorialName");
        Editorial editorial = editorialService.modifyEditorialName(id, newName);
        logger.info("fin changeEditorialName");
        return new ResponseEntity<>(editorial, HttpStatus.OK);
    }

    @ExceptionHandler(EditorialNotFoundException.class)
    @ResponseBody
    @ResponseStatus
    public ResponseEntity<Response> handlerException(EditorialNotFoundException enfe){
        Response response = Response.errorResponse(NOT_FOUND, enfe.getMessage());
        logger.error(enfe.getMessage(), enfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
