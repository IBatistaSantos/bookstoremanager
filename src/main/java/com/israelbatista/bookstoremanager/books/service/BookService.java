package com.israelbatista.bookstoremanager.books.service;

import com.israelbatista.bookstoremanager.author.service.AuthorService;
import com.israelbatista.bookstoremanager.books.mapper.BookMapper;
import com.israelbatista.bookstoremanager.books.repository.BookRepository;
import com.israelbatista.bookstoremanager.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    private BookRepository bookRepository;

    private UserService userService;

    private AuthorService authorService;

}
