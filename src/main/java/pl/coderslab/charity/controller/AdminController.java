package pl.coderslab.charity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.entity.UserRole;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;
import pl.coderslab.charity.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final InstitutionService institutionService;
    private final UserService userService;
    private final DonationService donationService;

    public AdminController(InstitutionService institutionService, UserService userService, DonationService donationService) {
        this.institutionService = institutionService;
        this.userService = userService;
        this.donationService = donationService;
    }

    @RequestMapping("")
    public String menu() {
        return "/html/admin-menu";
    }

    @GetMapping("/institutions")
    public String institutionList(Model model) {
        List<Institution> institutions = institutionService.listAll();
        model.addAttribute("institutions", institutions);
        return "/html/institution-list";
    }

    @GetMapping("/new")
    public String newInstitution(Model model) {
        Institution institution = new Institution();
        model.addAttribute("institution", institution);
        return "/html/institution-new";
    }

    @PostMapping("/new")
    public String newInstitutionProcess(Institution institution) {
        institutionService.save(institution);
        return "redirect:/admin/institutions";
    }

    @PostMapping("/institutions")
    public String institutionEdit(@RequestParam("edit") Long editId) {

        return "redirect:/admin/institution/" + editId;
    }

    @GetMapping("/institution/{id}")
    public String institutionDetailsShow(@PathVariable("id") Long id, Model model) {
        Institution institution = institutionService.findById(id).orElseThrow();
        model.addAttribute("institution", institution);
        return "/html/institution-details";
    }

    @PostMapping("/institution/{id}")
    public String institutionDetailsProcess(@RequestParam("name") String name,
                                            @RequestParam("description") String description,
                                            @RequestParam("id") Long id) {
        Institution institution = institutionService.findById(id).orElseThrow();
        institution.setName(name);
        institution.setDescription(description);
        institutionService.save(institution);
        return "redirect:/admin/institutions";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "/html/user-list";
    }

    @PostMapping("/users")
    public String userToAdmin(@RequestParam("admin") Long id) {
        User user = userService.findById(id).orElseThrow();
        user.setUserRole(UserRole.ADMIN);
        userService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/donations")
    public String donationsList(Model model){
        List<Donation> donations = donationService.findAll();
        model.addAttribute("donations", donations);

        return "/html/donations";
    }

    @PostMapping("/donations")
    public String donationsRecive(@RequestParam("id")Long id) {
        Donation donation = donationService.findById(id).orElseThrow();
        donation.setRecived(true);
        donationService.save(donation);
        return "redirect:/admin/donations";
    }
}
