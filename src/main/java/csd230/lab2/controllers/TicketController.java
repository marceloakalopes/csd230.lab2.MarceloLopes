package csd230.lab2.controllers;

import csd230.lab2.entities.Ticket;
import csd230.lab2.respositories.TicketRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;

    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping
    public String listTickets(Model model) {
        model.addAttribute("tickets", ticketRepository.findAll());
        return "tickets";
    }

    @GetMapping("/add-ticket")
    public String addTicketForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "add-ticket";
    }

    @PostMapping("/add-ticket")
    public String addTicketSubmit(@ModelAttribute Ticket ticket) {
        ticketRepository.save(ticket);
        return "redirect:/tickets";
    }

    @GetMapping("/edit-ticket")
    public String editTicket(@RequestParam("id") Long id, Model model) {
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        model.addAttribute("ticket", ticket);
        return "edit-ticket";
    }

    @PostMapping("/edit-ticket")
    public String editTicketSubmit(@ModelAttribute Ticket ticket) {
        ticketRepository.save(ticket);
        return "redirect:/tickets";
    }

    @GetMapping("/delete-ticket")
    public String deleteTicket(@RequestParam("id") Long id) {
        ticketRepository.deleteById(id);
        return "redirect:/tickets";
    }
}
