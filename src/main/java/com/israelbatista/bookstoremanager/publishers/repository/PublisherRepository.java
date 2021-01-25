package com.israelbatista.bookstoremanager.publishers.repository;

import com.israelbatista.bookstoremanager.publishers.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
