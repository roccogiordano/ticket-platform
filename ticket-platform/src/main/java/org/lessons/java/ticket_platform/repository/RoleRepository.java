package org.lessons.java.ticket_platform.repository;

import java.util.Optional;

import org.lessons.java.ticket_platform.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    
    Optional<Role> findByName(String name);

}
