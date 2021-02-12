package com.israelbatista.bookstoremanager.publishers.servive;

import com.israelbatista.bookstoremanager.publishers.builder.PublisherDTOBuilder;
import com.israelbatista.bookstoremanager.publishers.mapper.PublisherMapper;
import com.israelbatista.bookstoremanager.publishers.repository.PublisherRepository;
import com.israelbatista.bookstoremanager.publishers.service.PublisherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}
