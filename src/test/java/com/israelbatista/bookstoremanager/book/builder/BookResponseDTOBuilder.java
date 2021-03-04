package com.israelbatista.bookstoremanager.book.builder;

import com.israelbatista.bookstoremanager.author.builder.AuthorDTOBuilder;
import com.israelbatista.bookstoremanager.author.dto.AuthorDTO;
import com.israelbatista.bookstoremanager.books.dto.BookRequestDTO;
import com.israelbatista.bookstoremanager.books.dto.BookResponseDTO;
import com.israelbatista.bookstoremanager.publishers.builder.PublisherDTOBuilder;
import com.israelbatista.bookstoremanager.publishers.dto.PublisherDTO;
import com.israelbatista.bookstoremanager.users.builder.UserDTOBuilder;
import com.israelbatista.bookstoremanager.users.dto.UserDTO;
import lombok.Builder;

@Builder
public class BookResponseDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Sprint Boot PRO";

    @Builder.Default
    private String isbn = "978-3-16-148410-0";

    @Builder.Default
    private Integer pages = 200;

    @Builder.Default
    private Integer chapters = 10;

    @Builder.Default
    private AuthorDTO author = AuthorDTOBuilder.builder().build().buildAuthorDTO();

    @Builder.Default
    private PublisherDTO publisher = PublisherDTOBuilder.builder().build().buildPublisherDTO();

    private final UserDTO userDTO = UserDTOBuilder.builder().build().buildUserDTO();

    public BookResponseDTO buildRequestBookDTO() {
        return new BookResponseDTO(id,
                name,
                isbn,
                pages,
                chapters,
                author,
                publisher
        );
    }

}
