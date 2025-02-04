package csd230.lab2.controllers;

import csd230.lab2.entities.DiscMag;
import csd230.lab2.respositories.DiscMagRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/discmags")
public class DiscMagController {

    private final DiscMagRepository discMagRepository;

    public DiscMagController(DiscMagRepository discMagRepository) {
        this.discMagRepository = discMagRepository;
    }

    @GetMapping
    public String listDiscMags(Model model) {
        model.addAttribute("discmags", discMagRepository.findAll());
        return "discmags";
    }

    @GetMapping("/add-discmag")
    public String addDiscMagForm(Model model) {
        model.addAttribute("discMag", new DiscMag());
        return "add-discmag";
    }

    @PostMapping("/add-discmag")
    public String addDiscMagSubmit(@ModelAttribute DiscMag discMag) {
        discMagRepository.save(discMag);
        return "redirect:/discmags";
    }

    @GetMapping("/edit-discmag")
    public String editDiscMag(@RequestParam("id") Long id, Model model) {
        DiscMag discMag = discMagRepository.findById(id).orElse(null);
        model.addAttribute("discMag", discMag);
        return "edit-discmag";
    }

    @PostMapping("/edit-discmag")
    public String editDiscMagSubmit(@ModelAttribute DiscMag discMag) {
        discMagRepository.save(discMag);
        return "redirect:/discmags";
    }

    @GetMapping("/delete-discmag")
    public String deleteDiscMag(@RequestParam("id") Long id) {
        discMagRepository.deleteById(id);
        return "redirect:/discmags";
    }
}
