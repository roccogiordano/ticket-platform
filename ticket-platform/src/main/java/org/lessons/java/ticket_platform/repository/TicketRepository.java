package org.lessons.java.ticket_platform.repository;

import java.util.List;

import org.lessons.java.ticket_platform.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    public List<Ticket> findByNameContaining(String name);

}
