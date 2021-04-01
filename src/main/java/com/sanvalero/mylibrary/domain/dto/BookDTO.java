package com.sanvalero.mylibrary.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDTO {

    private String title;
    private String authorName;
    private String authorLastName;
    private String editorial;

}
