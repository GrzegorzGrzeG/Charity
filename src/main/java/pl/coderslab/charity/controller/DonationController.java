package pl.coderslab.charity.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.service.CategoryService;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;

import java.util.List;

@Controller
public class DonationController {
    private final DonationService donationService;
    private final CategoryService categoryService;
    private final InstitutionService institutionService;

    public DonationController(DonationService donationService, CategoryService categoryService, InstitutionService institutionService) {
        this.donationService = donationService;
        this.categoryService = categoryService;
        this.institutionService = institutionService;
    }

    @RequestMapping("/form")
    public String form(Model model) {
        Donation donation = new Donation();
        model.addAttribute("institutionList", institutionService.listAll());
        model.addAttribute("categoryList", categoryService.categoryList());
        model.addAttribute("donation", donation);
        return "/html/form";
    }

    @PostMapping("/form")
    public String postForm(@RequestParam("categories") List<Long> categories,
                           @RequestParam("bags") Integer bags,
                           @RequestParam("institutionId")Long institutionId,
                           Donation donation) {
        List<Category> categoryList = categoryService.findByIds(categories);
        Institution institution = institutionService.findById(institutionId).orElseThrow();
        donation.setCategories(categoryList);
        donation.setQuantity(bags);
        donation.setInstitution(institution);
        donationService.save(donation);

        return "/html/index";
    }
}
