package pl.coderslab.charity.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.charity.entity.User;
import pl.coderslab.charity.entity.UserRole;
import pl.coderslab.charity.service.UserService;

import java.security.Principal;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "/html/register";
    }

    @PostMapping("/register")
    public String registerProcess(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setUserRole(UserRole.USER);
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model){
        return "/html/login";
    }

    @GetMapping("/info")
    @ResponseBody
    public String info(Principal principal) {
        return principal.toString();
    }


}
