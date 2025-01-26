package com.example.Project1.DTOs;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {
    private String title;
    private String author;
    private String genre;
}
