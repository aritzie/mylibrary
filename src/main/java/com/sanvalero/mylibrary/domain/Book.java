package com.sanvalero.mylibrary.domain;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "books")
public class Book {

    @Schema(description = "Identificador del libro", example="1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Schema(description = "Título del libro", example = "1984", required = true)
    @NotBlank
    @Column
    private String title;

    @Schema(description = "Género del libro", example = "Narrativa")
    @Column
    private String genre;

    @Schema(description = "Calificación del libro", example = "8.5")
    @Column
    private float rate;

    @Schema(description = "El libro está catalogado", example = "true")
    @Column
    private boolean cataloged;

    @Schema(description = "Fecha de la primera publicación", example = "01/01/2021")
    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate firstPublication;

    @ManyToOne
    @JoinColumn(name = "editorial_id")
    private Editorial editorial;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}

