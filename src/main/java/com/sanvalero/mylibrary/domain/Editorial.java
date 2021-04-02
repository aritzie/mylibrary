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

    @OneToMany(mappedBy = "editorial")
    @JsonBackReference
    private List<Book> books;
}
