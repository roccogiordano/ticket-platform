package org.lessons.java.ticket_platform.repository;

import java.util.Optional;

import org.lessons.java.ticket_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
    
    Optional<User> findByUsername(String username);

}
