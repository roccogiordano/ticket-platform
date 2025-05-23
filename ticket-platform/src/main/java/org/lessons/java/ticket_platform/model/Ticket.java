package org.lessons.java.ticket_platform.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is mandatory")
    private TicketStatus status;

    @ManyToOne
    @JoinColumn(name = "operator_id", nullable = false)
    @NotNull(message = "Operator ID is mandatory")
    @JsonIgnore
    private Operator operator;

    @OneToMany(mappedBy = "ticket")
    @Nullable
    private List<Note> notes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TicketStatus getStatus() {
        return status;
    }
    
    public void setStatus(TicketStatus status) {
        this.status = status;
    }
    
    
    public List<Note> getNotes() {
        return notes;
    }
    
    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
    
    public Operator getOperator() {
        return operator;
    }
    
    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getHumanStatus() {
        return status.toString().replace("_", " ").toUpperCase();
    }
    
    @Override
    public String toString() {
        return "Ticket{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", status='" + status + '\'' +
        ", notes='" + notes + '\'' +
        ", operator=" + operator +
        '}';
    }
    
}
