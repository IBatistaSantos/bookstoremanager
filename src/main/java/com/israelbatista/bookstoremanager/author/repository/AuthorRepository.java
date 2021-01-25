package com.israelbatista.bookstoremanager.author.repository;

import com.israelbatista.bookstoremanager.author.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {


}
