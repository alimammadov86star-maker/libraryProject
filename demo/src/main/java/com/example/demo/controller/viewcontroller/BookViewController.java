package com.example.demo.controller.viewcontroller;

import com.example.demo.model.dto.book.BookRequest;
import com.example.demo.model.entity.Book;
import com.example.demo.service.AuthorService;
import com.example.demo.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/library")
public class BookViewController {

    private final BookService bookService;
    private final AuthorService authorService;

    @GetMapping("/books")
    public String books(Model model) {
        model.addAttribute("books", bookService.getAll());
        return "books";
    }

    @GetMapping("/books/add")
    public String addBookPage(Model model) {
        model.addAttribute("bookRequest", new BookRequest());
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("editMode", false);

        return "books/create";
    }

    @PostMapping("/books/add")
    public String addBook(
            @Valid @ModelAttribute("bookRequest") BookRequest bookRequest,
            Model model,
            BindingResult result) {

        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.getAll());
            model.addAttribute("editMode", false);
            return "books/create";
        }

        bookService.create(bookRequest);

        return "redirect:/library/books";
    }

    @GetMapping("/books/{id}/edit")
    public String editBookPage(@PathVariable Long id, Model model) {

        Book book = bookService.getById(id);

        BookRequest request = new BookRequest();
        request.setTitle(book.getTitle());
        request.setGenre(book.getGenre());
        request.setPrice(book.getPrice());

        model.addAttribute("bookRequest", request);
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("bookId", id);
        model.addAttribute("editMode", true);

        return "books/create";
    }

    @PostMapping("/books/{id}/edit")
    public String editBook(
            @PathVariable Long id,
            @Valid @ModelAttribute BookRequest bookRequest,
            Model model,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.getAll());
            model.addAttribute("bookId", id);
            model.addAttribute("editMode", true);
            return "books/create";
        }

        bookService.update(id, bookRequest);

        return "redirect:/library/books";
    }

    @PostMapping("/books/{id}/delete")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);

        return "redirect:/library/books";

    }
}