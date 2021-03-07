package com.israelbatista.bookstoremanager.books.service;

import com.israelbatista.bookstoremanager.author.entity.Author;
import com.israelbatista.bookstoremanager.author.service.AuthorService;
import com.israelbatista.bookstoremanager.books.dto.BookRequestDTO;
import com.israelbatista.bookstoremanager.books.dto.BookResponseDTO;
import com.israelbatista.bookstoremanager.books.entity.Book;
import com.israelbatista.bookstoremanager.books.exception.BookAlreadyExistsException;
import com.israelbatista.bookstoremanager.books.exception.BookNotFoundException;
import com.israelbatista.bookstoremanager.books.mapper.BookMapper;
import com.israelbatista.bookstoremanager.books.repository.BookRepository;
import com.israelbatista.bookstoremanager.publishers.entity.Publisher;
import com.israelbatista.bookstoremanager.publishers.service.PublisherService;
import com.israelbatista.bookstoremanager.users.dto.AuthenticatedUser;
import com.israelbatista.bookstoremanager.users.entity.User;
import com.israelbatista.bookstoremanager.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    private BookRepository bookRepository;

    private UserService userService;

    private AuthorService authorService;

    private PublisherService publisherService;

    public BookResponseDTO create (AuthenticatedUser authenticatedUser, BookRequestDTO bookRequestDTO) {
        User foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        verifyIfBookIsAlreadyRegistered(bookRequestDTO.getName(), bookRequestDTO.getIsbn(), foundAuthenticatedUser);

        Author foundAuthor = authorService.verifyAndGetIfExists(bookRequestDTO.getAuthorId());
        Publisher foundPublisher = publisherService.verifyAndGetIfExists(bookRequestDTO.getPublisherId());

        Book bookToSave = bookMapper.toModel(bookRequestDTO);
        bookToSave.setUser(foundAuthenticatedUser);
        bookToSave.setAuthor(foundAuthor);
        bookToSave.setPublisher(foundPublisher);


        Book savedBook = bookRepository.save(bookToSave);

        return bookMapper.toDTO(savedBook);

    }

    public BookResponseDTO findByIdAndUser(AuthenticatedUser authenticatedUser, Long bookId) {
        User foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        return bookRepository.findByIdAndUser(bookId, foundAuthenticatedUser)
                .map(bookMapper::toDTO)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    public List<BookResponseDTO> findAllByUser(AuthenticatedUser authenticatedUser) {
        User foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        return bookRepository.findAllByUser(foundAuthenticatedUser).stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteByIdAndUser(AuthenticatedUser authenticatedUser, Long bookId) {
        User foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        Book bookToDelete = verifyAndGetIfExists(bookId, foundAuthenticatedUser);
        bookRepository.deleteByIdAndUser(bookToDelete.getId(), foundAuthenticatedUser);
    }

    private Book verifyAndGetIfExists(Long bookId, User foundAuthenticatedUser) {
        return bookRepository.findByIdAndUser(bookId, foundAuthenticatedUser)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    private void verifyIfBookIsAlreadyRegistered(String name, String isbn, User foundUser) {
        bookRepository.findByNameAndIsbnAndUser(name, isbn, foundUser)
                .ifPresent(book -> {
                    throw new BookAlreadyExistsException(name, isbn, foundUser.getUsername());
                });
    }
}
