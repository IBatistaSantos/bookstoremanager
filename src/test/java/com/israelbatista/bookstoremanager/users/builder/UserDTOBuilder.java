package com.israelbatista.bookstoremanager.users.builder;

import com.israelbatista.bookstoremanager.users.dto.UserDTO;
import com.israelbatista.bookstoremanager.users.enums.Gender;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class UserDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Israel Batista";

    @Builder.Default
    private int age = 23;

    @Builder.Default
    private Gender gender = Gender.MALE;


    @Builder.Default
    private String email = "israelbatiista19@gmail.com";

    @Builder.Default
    private String username = "IBatistaSantos";

    @Builder.Default
    private String password = "123123";

    @Builder.Default
    private LocalDate birthDate = LocalDate.of(1997, 6,23);

    public UserDTO buildUserDTO() {
        return new UserDTO(id, name, age, gender, email, username, password, birthDate);
    }
}
