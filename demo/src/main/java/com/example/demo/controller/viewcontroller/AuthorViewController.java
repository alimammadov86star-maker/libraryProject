package com.example.demo.controller.viewcontroller;

import com.example.demo.model.dto.author.AuthorRequest;
import com.example.demo.model.entity.Author;
import com.example.demo.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/library")
public class AuthorViewController {

    private final AuthorService authorService;

    @GetMapping("/authors")
    public String authors(Model model) {
        model.addAttribute("authors", authorService.getAll());

        return "authors";
    }


    @GetMapping("authors/add")
    public String addAuthorPage(Model model) {
        model.addAttribute("authorRequest", new AuthorRequest());
        model.addAttribute("editMode", false);

        return "author-form";
    }

    @PostMapping("/authors/add")
    public String addAuthor(@Valid @ModelAttribute("authorRequest") AuthorRequest authorRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("editMode", false);

            return "author-form";
        }

        authorService.create(authorRequest);
        return "redirect:/library/authors";
    }


    @GetMapping("/authors/{id}/edit")
    public String editBookPage(@PathVariable Long id, Model model) {
        Author author = authorService.getById(id);

        AuthorRequest request = new AuthorRequest();

        request.setFullname(author.getFullname());
        request.setCountry(author.getCountry());
        request.setStyle(author.getStyle());

        model.addAttribute("authorRequest", request);
        model.addAttribute("authorId", id);
        model.addAttribute("editMode", true);

        return "author-form";
    }


    @PostMapping("/authors/{id}/edit")
    public String updateAuthor(@PathVariable Long id,
                               @Valid @ModelAttribute("authorRequest") AuthorRequest authorRequest,
                               BindingResult result,
                               Model model) {

        if (result.hasErrors()) {
            model.addAttribute("authorId", id);
            model.addAttribute("editMode", true);

            return "author-form";
        }


        return "redirect:/library/authors";
    }



}