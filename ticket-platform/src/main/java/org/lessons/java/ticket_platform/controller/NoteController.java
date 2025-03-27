package org.lessons.java.ticket_platform.controller;

import java.time.LocalDateTime;

import org.lessons.java.ticket_platform.model.Note;
import org.lessons.java.ticket_platform.model.Operator;
import org.lessons.java.ticket_platform.repository.NoteRepository;
import org.lessons.java.ticket_platform.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private OperatorService operatorService;

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("note") Note note, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Operator loggedOperator = operatorService.getLoggedOperator(username);
        
        String descriptionField = "description";
        if (bindingResult.hasFieldErrors(descriptionField)) {         
            return "/notes/create";
        }

        note.setAuthor(loggedOperator);
        note.setCreatedAt(LocalDateTime.now());

        noteRepository.save(note);

        redirectAttributes.addFlashAttribute("message", String.format("Note has been successfully added to Ticket %s!", note.getTicket().getName()));
        redirectAttributes.addFlashAttribute("messageClass", "alert-success");

        return "redirect:/tickets";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("note", noteRepository.findById(id).get());
        return "/notes/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("note") Note note, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Operator loggedOperator = operatorService.getLoggedOperator(username);
        
        String descriptionField = "description";
        if (bindingResult.hasFieldErrors(descriptionField)) {         
            return "/notes/create";
        }

        note.setAuthor(loggedOperator);
        note.setCreatedAt(LocalDateTime.now());

        noteRepository.save(note);

        redirectAttributes.addFlashAttribute("message", String.format("Note for Ticket %s has been successfully edited!", note.getTicket().getName()));
        redirectAttributes.addFlashAttribute("messageClass", "alert-info");

        return "redirect:/tickets";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        Note note = noteRepository.findById(id).get();
        noteRepository.delete(note);

        redirectAttributes.addFlashAttribute("message", "Note has been successfully deleted!");
        redirectAttributes.addFlashAttribute("messageClass", "alert-danger");

        return "redirect:/tickets";
    }
}
