package com.israelbatista.bookstoremanager.users.exception;

import javax.persistence.EntityExistsException;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserAlreadyExistsException extends EntityExistsException {
    public UserAlreadyExistsException(String email, String username) {
        super(String.format("User with email %s or username %s already exists", email, username));
    }
}
