package com.israelbatista.bookstoremanager.books.repository;

import com.israelbatista.bookstoremanager.books.entity.Book;
import com.israelbatista.bookstoremanager.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByNameAndIsbnAndUser(String name, String isbn, User user);
}
