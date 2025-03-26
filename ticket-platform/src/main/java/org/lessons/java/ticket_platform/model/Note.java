package org.lessons.java.ticket_platform.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "notes")
public class Note {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "author")
    @NotBlank(message = "Author is mandatory")
    private String author;

    @Column(name = "creation_date")
    @NotNull(message = "Creation date is mandatory")
    private LocalDate creationDate;

    @Column(name = "text")
    @NotBlank(message = "Text is mandatory")
    private String text;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    @NotNull(message = "Ticket ID is mandatory")
    @JsonIgnore
    private Ticket ticket;

}
