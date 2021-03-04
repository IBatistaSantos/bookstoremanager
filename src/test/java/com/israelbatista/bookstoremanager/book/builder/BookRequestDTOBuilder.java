package com.israelbatista.bookstoremanager.book.builder;

import com.israelbatista.bookstoremanager.books.dto.BookRequestDTO;
import com.israelbatista.bookstoremanager.users.builder.UserDTOBuilder;
import com.israelbatista.bookstoremanager.users.dto.UserDTO;
import lombok.Builder;

@Builder
public class BookRequestDTOBuilder {

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
    private Long authorId = 3L;

    @Builder.Default
    private Long publisherId = 2L;

    private final UserDTO userDTO = UserDTOBuilder.builder().build().buildUserDTO();

    public BookRequestDTO buildRequestBookDTO() {
        return new BookRequestDTO(id,
                name,
                isbn,
                pages,
                chapters,
                publisherId,
                authorId
        );
    }
}
