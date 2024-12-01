package org.dishantpetition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.ArrayList;


@Controller
public class PetitionController {

    @Autowired
    private PetitionService petitionService;

    @GetMapping("/create-petition")
    public String createPetitionPage() {
        return "create-petition";
    }

    @PostMapping("/create-petition")
    public String createPetition(@RequestParam String title, @RequestParam String description) {
        petitionService.addPetition(new Petition(title, description));
        return "redirect:/view-all-petitions";
    }

    @GetMapping("/view-all-petitions")
    public String viewAllPetitions(Model model) {
        model.addAttribute("petitions", petitionService.getAllPetitions());
        return "view-all-petitions";
    }

    @GetMapping("/search-petition")
    public String searchPetitionPage() {
        return "search-petition";
    }

    @PostMapping("/search-petition")
    public String searchPetition(@RequestParam String query, Model model) {
        Petition petition = petitionService.getPetitionByTitle(query);
        List<Petition> petitionList = new ArrayList<>();
        if (petition!=null)
            petitionList.add(petition);
        model.addAttribute("petitions", petitionList);
        return "search-results";
    }

    @GetMapping("/view-petition/{title}")
    public String viewPetition(@PathVariable String title, Model model) {
        Petition petition = petitionService.getPetitionByTitle(title);
        model.addAttribute("petition", petition);
        return "view-petition";
    }

    @PostMapping("/sign-petition/{title}")
    public String signPetition(@PathVariable String title, @RequestParam String name, @RequestParam String email) {
        petitionService.addSignature(title, new Signature(name, email));
        return "redirect:/view-petition/" + title;
    }
}
