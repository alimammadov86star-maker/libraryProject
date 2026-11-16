package com.example.demo.service;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.dto.author.AuthorRequest;
import com.example.demo.model.dto.author.AuthorResponse;
import com.example.demo.model.dto.book.BookRequest;
import com.example.demo.model.dto.book.BookResponse;
import com.example.demo.model.entity.Author;
import com.example.demo.model.entity.Book;
import com.example.demo.repository.AuthorRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Page<AuthorResponse> getAllPaginated(
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size); // показываем page = 0, size = 5
        Page<Author> authors = authorRepository.findAll(pageable); // получаем

        return authors.map(this::convertToResponse);
    }

    public List<AuthorResponse> getAll() {
        return authorRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public Author getById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author is not found: " + id));

        return author;
    }

    public AuthorResponse create(AuthorRequest authorRequest) {
        Author author = new Author();

        author.setFullname(authorRequest.getFullname());
        author.setCountry(authorRequest.getCountry());
        author.setStyle(authorRequest.getStyle());

        Author savedAuthor = authorRepository.save(author);

        return convertToResponse(savedAuthor);
    }


    public Author update(Long id, Author newAuthor) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author is not found: " + id));

        author.setFullname(newAuthor.getFullname());
        author.setCountry(newAuthor.getCountry());
        author.setStyle(newAuthor.getStyle());

        return authorRepository.save(author);
    }

    public void delete(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author is not found: " + id));
        authorRepository.delete(author);
    }

    private AuthorResponse convertToResponse(Author author) {
        return new AuthorResponse(
                author.getId(),
                author.getFullname(),
                author.getCountry(),
                author.getStyle()
        );
    }
}
