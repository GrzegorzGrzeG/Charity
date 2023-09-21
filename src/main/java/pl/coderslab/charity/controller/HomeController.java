package pl.coderslab.charity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/")
public class HomeController {

    private final InstitutionService institutionService;
    private final DonationService donationService;

    public HomeController(InstitutionService institutionService, DonationService donationService) {
        this.institutionService = institutionService;
        this.donationService = donationService;
    }

    @RequestMapping("/")
    public String homeAction(Model model) {
        List<Institution> institutions = institutionService.listAll();
        List<Institution> evenIdInstitution = new ArrayList<>();
        List<Institution> oddIdInstitution = new ArrayList<>();
        for (Institution i : institutions) {
            if(i.getId() % 2 == 0){
                evenIdInstitution.add(i);
            } else {
                oddIdInstitution.add(i);
            }
        }
        model.addAttribute("evenIdInstitution", evenIdInstitution);
        model.addAttribute("oddIdInstitution", oddIdInstitution);
        model.addAttribute("bags", donationService.sum());
        model.addAttribute("count", donationService.count());
        return "/html/index";
    }
}
