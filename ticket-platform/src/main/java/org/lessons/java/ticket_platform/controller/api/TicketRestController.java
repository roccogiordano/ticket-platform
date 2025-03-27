package org.lessons.java.ticket_platform.controller.api;

import java.util.List;
import java.util.Optional;

import org.lessons.java.ticket_platform.model.Ticket;
import org.lessons.java.ticket_platform.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
public class TicketRestController {
    
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    public List<Ticket> index() {
        return ticketRepository.findAll();
    }

    @GetMapping("/search")
    public List<Ticket> search(@RequestParam(name = "name") String name) {
        return ticketRepository.findByNameContaining(name);
    }

    @GetMapping("{id}")
    public ResponseEntity<Ticket> show(@PathVariable Integer id) {
        Optional<Ticket> ticketAttempt = ticketRepository.findById(id);

        if (ticketAttempt.isPresent()) {
            return new ResponseEntity<Ticket>(ticketAttempt.get(), HttpStatus.OK);
        }

        return new ResponseEntity<Ticket>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Ticket> store(@Valid @RequestBody Ticket Ticket) {
        return new ResponseEntity<Ticket>(ticketRepository.save(Ticket), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Ticket> update(@PathVariable Integer id, @Valid @RequestBody Ticket Ticket) {
        Optional<Ticket> ticketAttempt = ticketRepository.findById(id);

        if (ticketAttempt.isPresent()) {
            return new ResponseEntity<Ticket>(ticketRepository.save(Ticket), HttpStatus.OK);
        }

        return new ResponseEntity<Ticket>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Ticket> delete(@PathVariable Integer id) {
        Optional<Ticket> ticketAttempt = ticketRepository.findById(id);

        if (ticketAttempt.isPresent()) {
            ticketRepository.delete(ticketAttempt.get());
            return new ResponseEntity<Ticket>(HttpStatus.OK);
        }

        return new ResponseEntity<Ticket>(HttpStatus.NOT_FOUND);
    }

}
