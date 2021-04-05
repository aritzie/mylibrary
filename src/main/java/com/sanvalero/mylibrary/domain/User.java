package com.sanvalero.mylibrary.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "users")
public class User {

    @Schema(description = "Identificador del usuario", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Schema(description = "Nombre del usuario", example = "aritz95", required = true)
    @NotBlank
    @Column
    private String userName;

    @ManyToMany(targetEntity = Book.class)
    private List<Book> books;

    public User(){
        books = new ArrayList<>();
    }

    public void addBook(Book book){
        books.add(book);
    }

}
