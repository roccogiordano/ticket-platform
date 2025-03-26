package org.lessons.java.ticket_platform.repository;

import org.lessons.java.ticket_platform.model.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatorRepository extends JpaRepository<Operator, Integer> {

}
