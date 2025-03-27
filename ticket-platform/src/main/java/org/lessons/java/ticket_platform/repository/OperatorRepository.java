package org.lessons.java.ticket_platform.repository;

import java.util.Optional;

import org.lessons.java.ticket_platform.model.Operator;
import org.lessons.java.ticket_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatorRepository extends JpaRepository<Operator, Integer> {

    Optional<Operator> findByUser(User user);

}
