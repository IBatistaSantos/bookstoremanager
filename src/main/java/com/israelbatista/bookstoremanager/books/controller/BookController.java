package com.israelbatista.bookstoremanager.books.controller;

import com.israelbatista.bookstoremanager.books.dto.BookRequestDTO;
import com.israelbatista.bookstoremanager.books.dto.BookResponseDTO;
import com.israelbatista.bookstoremanager.books.service.BookService;
import com.israelbatista.bookstoremanager.users.dto.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/books")
public class BookController implements BookControllerDocs {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponseDTO create(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @RequestBody @Valid BookRequestDTO bookRequestDTO) {
        return bookService.create(authenticatedUser, bookRequestDTO);
    }
}
