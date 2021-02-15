package com.israelbatista.bookstoremanager.users.repository;


import com.israelbatista.bookstoremanager.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
