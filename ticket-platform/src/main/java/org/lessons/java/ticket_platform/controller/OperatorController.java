package org.lessons.java.ticket_platform.controller;

import org.lessons.java.ticket_platform.model.Operator;
import org.lessons.java.ticket_platform.model.TicketStatus;
import org.lessons.java.ticket_platform.repository.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/operators")
public class OperatorController {

    @Autowired
    private OperatorRepository operatorRepository;

    @GetMapping("status/{id}")
    public String editStatus(@PathVariable Integer id, Model model) {
        Operator operator = operatorRepository.findById(id).get();
        model.addAttribute("operator", operator);
        return "/operators/status";
    }

    @PostMapping("status/{id}")
    public String updateStatus(@Valid @PathVariable Integer id, @RequestParam String status, RedirectAttributes redirectAttributes) {

        Operator operator = operatorRepository.findById(id).get();

        if ("INACTIVE".equalsIgnoreCase(status)) {
            boolean hasActiveTickets = operator.getTickets().stream()
                .anyMatch(ticket -> ticket.getStatus() == TicketStatus.IN_PROGRESS || ticket.getStatus() == TicketStatus.OPEN);

            if (hasActiveTickets) {
                redirectAttributes.addFlashAttribute("message", "You cannot set your status to inactive while you have tickets in progress or open.");
                redirectAttributes.addFlashAttribute("messageClass", "alert-danger");
                return "redirect:/tickets";
            }
        }

        operator.setStatus(status);
        operatorRepository.save(operator);

        redirectAttributes.addFlashAttribute("message", String.format("Your status has been succesfully edited!"));
        redirectAttributes.addFlashAttribute("messageClass", "alert-info");

        return "redirect:/tickets";
    }
}
