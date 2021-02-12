package com.israelbatista.bookstoremanager.publishers.service;


import com.israelbatista.bookstoremanager.publishers.dto.PublisherDTO;
import com.israelbatista.bookstoremanager.publishers.entity.Publisher;
import com.israelbatista.bookstoremanager.publishers.expection.PublisherAlreadyExistsException;
import com.israelbatista.bookstoremanager.publishers.mapper.PublisherMapper;
import com.israelbatista.bookstoremanager.publishers.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublisherService {

    private final  static PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    private PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public PublisherDTO create(PublisherDTO publisherDTO) {
        verifyIfExists(publisherDTO.getName(), publisherDTO.getCode());

        Publisher publisherToCreate = publisherMapper.toModel(publisherDTO);
        Publisher publisher = publisherRepository.save(publisherToCreate);
        return publisherMapper.toDTO(publisher);
    }

    private void verifyIfExists(String name, String code) {
        Optional<Publisher> duplicatedPublisher = publisherRepository.findByNameOrCode(name, code);

        if (duplicatedPublisher.isPresent()) {
            throw new PublisherAlreadyExistsException(name, code);
        }
    }
}
