package com.israelbatista.bookstoremanager.publishers.builder;


import com.israelbatista.bookstoremanager.publishers.dto.PublisherDTO;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class PublisherDTOBuilder {

    @Builder.Default
    private final Long id = 1L;

    private final String name = "Israel Editora";

    private final String code = "IS1234";

    private final LocalDate foundationDate = LocalDate.of(2020,06,29);

    public PublisherDTO buildPublisherDTO() {
        return new PublisherDTO(id, name, code, foundationDate);
    }
}
