package com.israelbatista.bookstoremanager.users.builder;

import com.israelbatista.bookstoremanager.users.dto.JwtRequest;
import lombok.Builder;

@Builder
public class JwtRequestBuilder {

    @Builder.Default
    private String username = "israel";

    @Builder.Default
    private String password = "123456";

    public JwtRequest builderJwtRequest() {
        return new JwtRequest(username, password);
    }
}
