package com.israelbatista.bookstoremanager.books.dto;

import com.israelbatista.bookstoremanager.author.dto.AuthorDTO;
import com.israelbatista.bookstoremanager.publishers.dto.PublisherDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {

    private Long id;

    private String name;

    private int isbn;

    private Long pages;

    private Long chapters;

    private AuthorDTO author;

    private PublisherDTO publisher;
}

