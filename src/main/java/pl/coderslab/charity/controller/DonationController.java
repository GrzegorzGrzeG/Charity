package pl.coderslab.charity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.service.CategoryService;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;
import pl.coderslab.charity.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class DonationController {
    private final DonationService donationService;
    private final CategoryService categoryService;
    private final InstitutionService institutionService;
    private final UserService userService;

    public DonationController(DonationService donationService, CategoryService categoryService, InstitutionService institutionService, UserService userService) {
        this.donationService = donationService;
        this.categoryService = categoryService;
        this.institutionService = institutionService;
        this.userService = userService;
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
                           @RequestParam("institutionId") Long institutionId,
                           Donation donation,
                           Model model,
                           Principal principal) {
        List<Category> categoryList = categoryService.findByIds(categories);
        Institution institution = institutionService.findById(institutionId).orElseThrow();
        User user = userService.findByEmail(principal.getName());
        donation.setCategories(categoryList);
        donation.setQuantity(bags);
        donation.setInstitution(institution);
        donation.setUser(user);
        donation.setRecived(false);
        donationService.save(donation);
        model.addAttribute("result", donation);
        return "/html/form-confirmation";
    }
}
