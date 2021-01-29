package com.israelbatista.bookstoremanager.author.builder;

import com.israelbatista.bookstoremanager.author.dto.AuthorDTO;
import lombok.Builder;

@Builder
public class AuthorDTOBuilder {

        @Builder.Default
        private final Long id = 1L;

        @Builder.Default
        private final String name = "Israel Batista";

        @Builder.Default
        private final int age = 23;


        public AuthorDTO buildAuthorDTO() {
            return new AuthorDTO(id, name, age);
        }

}
