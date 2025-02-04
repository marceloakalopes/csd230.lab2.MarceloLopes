package csd230.lab2.controllers;

import csd230.lab2.entities.Magazine;
import csd230.lab2.respositories.MagazineRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/magazines")
public class MagazineController {

    private final MagazineRepository magazineRepository;

    public MagazineController(MagazineRepository magazineRepository) {
        this.magazineRepository = magazineRepository;
    }

    // 1) View all
    @GetMapping
    public String listMagazines(Model model) {
        model.addAttribute("magazines", magazineRepository.findAll());
        return "magazines"; // -> templates/magazines.html
    }

    // 2) Add
    @GetMapping("/add-magazine")
    public String addMagazineForm(Model model) {
        model.addAttribute("magazine", new Magazine());
        return "add-magazine";
    }

    @PostMapping("/add-magazine")
    public String addMagazineSubmit(@ModelAttribute Magazine magazine) {
        // set description or anything else
        magazineRepository.save(magazine);
        return "redirect:/magazines";
    }

    // 3) Edit
    @GetMapping("/edit-magazine")
    public String editMagazine(@RequestParam("id") Long id, Model model) {
        Magazine magazine = magazineRepository.findById(id).orElse(null);
        model.addAttribute("magazine", magazine);
        return "edit-magazine";
    }

    @PostMapping("/edit-magazine")
    public String editMagazineSubmit(@ModelAttribute Magazine magazine) {
        magazineRepository.save(magazine);
        return "redirect:/magazines";
    }

    // 4) Delete
    @GetMapping("/delete-magazine")
    public String deleteMagazine(@RequestParam("id") Long id) {
        magazineRepository.deleteById(id);
        return "redirect:/magazines";
    }
}
