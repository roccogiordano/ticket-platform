package org.lessons.java.ticket_platform.controller;

import java.util.List;

import org.lessons.java.ticket_platform.model.Note;
import org.lessons.java.ticket_platform.model.Ticket;
import org.lessons.java.ticket_platform.repository.NoteRepository;
import org.lessons.java.ticket_platform.repository.OperatorRepository;
import org.lessons.java.ticket_platform.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private NoteRepository noteRepository;

    @GetMapping
    public String view(Model model) {
        List<Ticket> tickets = ticketRepository.findAll();
        model.addAttribute("tickets", tickets);
        return("/tickets/index");
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("ticket", ticketRepository.findById(id).get());
        return "/tickets/show";
    }  

    @GetMapping("/search")
    public String search(@RequestParam(name = "name") String name, Model model) {
        List<Ticket> tickets = ticketRepository.findByNameContaining(name);
        model.addAttribute("tickets", tickets);
        return "/tickets/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("operators", operatorRepository.findAll());
        return "/tickets/create";
    }
 
    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("ticket") Ticket ticket, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("operators", operatorRepository.findAll());
            return "/tickets/create";
        }
        
        ticketRepository.save(ticket);

        redirectAttributes.addFlashAttribute("message", String.format("Ticket %s has been succesfully added to the menu!", ticket.getName()));
        redirectAttributes.addFlashAttribute("messageClass", "alert-success");

        return "redirect:/tickets";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("ticket", ticketRepository.findById(id).get());
        model.addAttribute("operators", operatorRepository.findAll());
        return "/tickets/edit";
    }
 
    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("ticket") Ticket ticket, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("operators", operatorRepository.findAll());
            return "/tickets/edit";
        }
        
        ticketRepository.save(ticket);

        redirectAttributes.addFlashAttribute("message", String.format("Ticket %s has been succesfully edited!", ticket.getName()));
        redirectAttributes.addFlashAttribute("messageClass", "alert-info");

        return "redirect:/tickets";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        Ticket ticket = ticketRepository.findById(id).get();

        for (Note note : ticket.getNotes()) {
            noteRepository.delete(note);
        }

        ticketRepository.delete(ticket);

        redirectAttributes.addFlashAttribute("message", String.format("Ticket %s has been succesfully deleted!", ticket.getName()));
        redirectAttributes.addFlashAttribute("messageClass", "alert-danger");

        return "redirect:/tickets";
    }

}
