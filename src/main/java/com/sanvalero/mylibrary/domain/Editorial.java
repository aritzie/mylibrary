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
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "editorials")
public class Editorial {

    @Schema(description = "Identificador de la editorial", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Schema(description = "Nombre de la editorial", example = "Txalaparta", required = true)
    @NotBlank
    @Column
    private String name;

    @Schema(description = "Número de publicaciones", example = "3")
    @Column
    private int publications;

    @Schema(description = "Si está cerrada", example = "false")
    @Column
    private boolean closed;

    @Schema(description = "Fecha de apertura", example = "01/01/2020")
    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate opening;

    @Schema(description = "Puntuación", example = "5.5")
    @Column
    private float rate;

    @OneToMany(mappedBy = "editorial")
    @JsonBackReference
    private List<Book> books;
}
