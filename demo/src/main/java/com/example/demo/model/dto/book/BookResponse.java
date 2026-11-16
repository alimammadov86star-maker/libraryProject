package com.example.demo.model.dto.book;

import com.example.demo.model.entity.Book;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@RequiredArgsConstructor
public class BookResponse extends Book {
    private Long id;
    private String title;
    private String genre;
    private Double price;
    private String authorName;

    public BookResponse(Long id, String title, String genre, Double price, String authorName) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.authorName = authorName;
    }
}
