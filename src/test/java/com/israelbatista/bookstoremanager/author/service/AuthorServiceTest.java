package com.israelbatista.bookstoremanager.author.service;


import com.israelbatista.bookstoremanager.author.builder.AuthorDTOBuilder;
import com.israelbatista.bookstoremanager.author.dto.AuthorDTO;
import com.israelbatista.bookstoremanager.author.entity.Author;
import com.israelbatista.bookstoremanager.author.exception.AuthorAlreadyExistsException;
import com.israelbatista.bookstoremanager.author.exception.AuthorNotFoundException;
import com.israelbatista.bookstoremanager.author.mapper.AuthorMapper;
import com.israelbatista.bookstoremanager.author.repository.AuthorRepository;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    private final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private AuthorDTOBuilder authorDTOBuilder;

    @BeforeEach
    void setUp() {
        authorDTOBuilder = AuthorDTOBuilder.builder().build();
    }

    @Test
    void whenExistingAuthorIsInformedThenAnExceptionShouldBeThrown() {

        //given
        AuthorDTO expectedAuthorToCreateDTO = authorDTOBuilder.buildAuthorDTO();
        Author expectedCreatedAuthor = authorMapper.toModel(expectedAuthorToCreateDTO);

        //when
        when(authorRepository.save(expectedCreatedAuthor)).thenReturn(expectedCreatedAuthor);
        when(authorRepository.findByName(expectedAuthorToCreateDTO.getName()))
                .thenReturn(Optional.empty());

        AuthorDTO createdAuthorDTO = authorService.create(expectedAuthorToCreateDTO);

        // when
        assertThat(createdAuthorDTO, is(IsEqual.equalTo(expectedAuthorToCreateDTO)));
    }


    @Test
    void whenNewAuthorIsInformedThenItShouldBeCreated() {
        //given
        AuthorDTO expectedAuthorToCreateDTO = authorDTOBuilder.buildAuthorDTO();
        Author expectedCreatedAuthor = authorMapper.toModel(expectedAuthorToCreateDTO);

        //when
        when(authorRepository.findByName(expectedAuthorToCreateDTO.getName()))
                .thenReturn(Optional.of(expectedCreatedAuthor));

        assertThrows(AuthorAlreadyExistsException.class,
                () -> authorService.create(expectedAuthorToCreateDTO));
    }

    @Test
    void whenValidIdIsGivenThenAnAuthorShouldBeReturned() {
        AuthorDTO expectedFoundAuthorDTO = authorDTOBuilder.buildAuthorDTO();
        Author expectedFoundAuthor = authorMapper.toModel(expectedFoundAuthorDTO);

        when(authorRepository.findById(expectedFoundAuthorDTO.getId()))
                .thenReturn(Optional.of(expectedFoundAuthor));

        AuthorDTO authorDTO = authorService.findById(expectedFoundAuthorDTO.getId());

        assertThat(authorDTO, is(equalTo(expectedFoundAuthorDTO)));

    }

    @Test
    void whenInvalidIdIsGivenThenAnAuthorShouldBeReturned() {
        AuthorDTO expectedFoundAuthorDTO = authorDTOBuilder.buildAuthorDTO();

        when(authorRepository.findById(expectedFoundAuthorDTO.getId()))
                .thenReturn(Optional.empty());

        assertThrows(AuthorNotFoundException.class,
                () ->  authorService.findById(expectedFoundAuthorDTO.getId()));
    }

    @Test
    void whenListAuthorsIsCalledThenItShouldBeReturned() {
        AuthorDTO expectedFoundAuthorDTO = authorDTOBuilder.buildAuthorDTO();
        Author expectedFoundAuthor = authorMapper.toModel(expectedFoundAuthorDTO);


        when(authorRepository.findAll())
                .thenReturn(Collections.singletonList(expectedFoundAuthor));

        List<AuthorDTO> authorDTOList = authorService.findAll();
        assertThat(authorDTOList.size(), is(1));
        assertThat(authorDTOList.get(0), is(equalTo(expectedFoundAuthorDTO)));
    }

    @Test
    void whenListAuthorsIsCalledThenAnEmptyListShouldBeReturned() {
        when(authorRepository.findAll())
                .thenReturn(Collections.EMPTY_LIST);

        List<AuthorDTO> authorDTOList = authorService.findAll();
        assertThat(authorDTOList.size(), is(0));
    }
}
