package com.israelbatista.bookstoremanager.publishers.servive;

import com.israelbatista.bookstoremanager.author.exception.PublisherNotFoundException;
import com.israelbatista.bookstoremanager.publishers.builder.PublisherDTOBuilder;
import com.israelbatista.bookstoremanager.publishers.dto.PublisherDTO;
import com.israelbatista.bookstoremanager.publishers.entity.Publisher;
import com.israelbatista.bookstoremanager.publishers.expection.PublisherAlreadyExistsException;
import com.israelbatista.bookstoremanager.publishers.mapper.PublisherMapper;
import com.israelbatista.bookstoremanager.publishers.repository.PublisherRepository;
import com.israelbatista.bookstoremanager.publishers.service.PublisherService;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PublisherServiceTest {

    private final PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherService publisherService;

    private PublisherDTOBuilder publisherDTOBuilder;

    @BeforeEach
    void setUp() {
        publisherDTOBuilder = PublisherDTOBuilder.builder().build();
    }

    @Test
    void whenNewPublisherIsInformedThenItShouldBeCreated() {
        PublisherDTO expectedPublisherToCreateDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expectedPublisherCreated = publisherMapper.toModel(expectedPublisherToCreateDTO);

        when(publisherRepository.findByNameOrCode(
                expectedPublisherToCreateDTO.getName(),
                expectedPublisherCreated.getCode()
        )).thenReturn(Optional.empty());

        when(publisherRepository.save(expectedPublisherCreated)).thenReturn(expectedPublisherCreated);

        PublisherDTO createdPublisherDTO = publisherService.create(expectedPublisherToCreateDTO);

        assertThat(createdPublisherDTO, is(equalTo(expectedPublisherToCreateDTO)));
    }

    @Test
    void whenNewPublisherIsInformedThenExceptionShouldBeThrown() {
        PublisherDTO expectedPublisherToCreateDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expectedPublisherDuplicated = publisherMapper.toModel(expectedPublisherToCreateDTO);

        when(publisherRepository.findByNameOrCode(
                expectedPublisherToCreateDTO.getName(),
                expectedPublisherToCreateDTO.getCode()
        )).thenReturn(Optional.of(expectedPublisherDuplicated));

        assertThrows(PublisherAlreadyExistsException.class,
                () -> publisherService.create(expectedPublisherToCreateDTO));
    }

    @Test
    void whenValidIdIsGivenThenAPublisherShouldReturned() {
        PublisherDTO expectedPublisherFoundDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expectedPublisherFound = publisherMapper.toModel(expectedPublisherFoundDTO);

        when(publisherRepository.findById(expectedPublisherFoundDTO.getId()))
                .thenReturn(Optional.of(expectedPublisherFound));

        PublisherDTO foundPublisherDTO = publisherService.findById(expectedPublisherFoundDTO.getId());
        assertThat(foundPublisherDTO, is(equalTo(foundPublisherDTO)));
    }

    @Test
    void whenInvalidIdIsGivenThenExceptionShouldThrown() {
        PublisherDTO expectedPublisherFoundDTO = publisherDTOBuilder.buildPublisherDTO();

        when(publisherRepository.findById(expectedPublisherFoundDTO.getId()))
                .thenReturn(Optional.empty());

        assertThrows(PublisherNotFoundException.class,
                () -> publisherService.findById(expectedPublisherFoundDTO.getId()));
    }

    @Test
    void whenListPublishersIsCalledThenItShouldBeReturned() {
        PublisherDTO expectedPublisherFoundDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expectedPublisherFound = publisherMapper.toModel(expectedPublisherFoundDTO);

        when(publisherRepository.findAll()).thenReturn(Collections.singletonList(expectedPublisherFound));

        List<PublisherDTO> foundPublishersDTO = publisherService.findAll();

        assertThat(foundPublishersDTO.size(), is(1));
        assertThat(foundPublishersDTO.get(0), is(equalTo(expectedPublisherFoundDTO)));
    }

    @Test
    void whenListPublishersIsCalledThenAnEmptyListItShouldBeReturned() {

        when(publisherRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        List<PublisherDTO> foundPublishersDTO = publisherService.findAll();

        assertThat(foundPublishersDTO.size(), is(0));
        assertThat(foundPublishersDTO.isEmpty(), is(true));
    }

    @Test
    void whenValidPublisherIdIsGivenThenItShouldDeleted() {
        PublisherDTO expectedPublisherDeletedDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expectedPublisherDeleted = publisherMapper.toModel(expectedPublisherDeletedDTO);

        var expectedDeletedPublisherId = expectedPublisherDeletedDTO.getId();

        doNothing().when(publisherRepository).deleteById(expectedDeletedPublisherId);
        when(publisherRepository.findById(expectedDeletedPublisherId))
                .thenReturn(Optional.of(expectedPublisherDeleted));

        publisherService.delete(expectedDeletedPublisherId);
        verify(publisherRepository, times(1)).deleteById(expectedDeletedPublisherId);
    }

    @Test
    void whenInvalidPublisherIdIsGivenThenItShouldNotBeDeleted() {
        var expectedInvalidPublisherId = 2L;

        when(publisherRepository.findById(expectedInvalidPublisherId))
                .thenReturn(Optional.empty());

        assertThrows(PublisherNotFoundException.class,
                () -> publisherService.delete(expectedInvalidPublisherId));
    }
}
