package pl.coderslab.charity.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.model.IModel;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.entity.UserRole;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final DonationService donationService;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, DonationService donationService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.donationService = donationService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "/html/register";
    }

    @PostMapping("/register")
    public String registerProcess(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setUserRole(UserRole.USER);
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "/html/login";
    }

    //metoda pomocnicza
    @GetMapping("/my")
    @ResponseBody
    public String info(Principal principal) {
        return principal.toString();
    }

    @GetMapping("/donations")
    @ResponseBody
    public String donations(Principal principal, Model model) {
        User user = userService.findByEmail(principal.getName());
        List<Donation> donations = donationService.findAllByUserId(user.getId());
        model.addAttribute("donations", donations);
        return "/html/user-donations";
    }

}
