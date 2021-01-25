package com.israelbatista.bookstoremanager.books.repository;

import com.israelbatista.bookstoremanager.books.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
