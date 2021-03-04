package com.israelbatista.bookstoremanager.book.service;

import com.israelbatista.bookstoremanager.author.service.AuthorService;
import com.israelbatista.bookstoremanager.book.builder.BookRequestDTOBuilder;
import com.israelbatista.bookstoremanager.book.builder.BookResponseDTOBuilder;
import com.israelbatista.bookstoremanager.books.mapper.BookMapper;
import com.israelbatista.bookstoremanager.books.repository.BookRepository;
import com.israelbatista.bookstoremanager.books.service.BookService;
import com.israelbatista.bookstoremanager.users.dto.AuthenticatedUser;
import com.israelbatista.bookstoremanager.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserService userService;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private BookService bookService;

    private BookRequestDTOBuilder bookRequestDTOBuilder;

    private BookResponseDTOBuilder bookResponseDTOBuilder;

    private AuthenticatedUser authenticatedUser;

    @BeforeEach
    void setUp() {
        bookRequestDTOBuilder = BookRequestDTOBuilder.builder().build();
        bookResponseDTOBuilder = BookResponseDTOBuilder.builder().build();

        authenticatedUser = new AuthenticatedUser(
                "israel",
                "123456",
                "ADMIN");

    }


}
