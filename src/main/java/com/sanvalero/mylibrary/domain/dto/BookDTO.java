package com.sanvalero.mylibrary.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDTO {

    @Schema(description = "Título del libro", example = "El hombre en busca de sentido", required = true)
    private String title;
    @Schema(description = "Nombre del autor del libro", example = "Viktor E.", required = true)
    private String authorName;
    @Schema(description = "Apellido del autor", example = "Frankl", required = true)
    private String authorLastName;
    @Schema(description = "Nombre de la editorial que publica el libro", example = "Herder", required = true)
    private String editorial;

}
