package com.sanvalero.mylibrary.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "authors")
public class Author {

    @Schema(description = "Identificador del autor", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Schema(description = "Nombre del autor", example = "George", required = true)
    @NotBlank
    @Column
    private String name;

    @Schema(description = "Apellido del autor", example = "Orwell", required = true)
    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "author")
    @JsonBackReference
    private List<Book> books;
}
