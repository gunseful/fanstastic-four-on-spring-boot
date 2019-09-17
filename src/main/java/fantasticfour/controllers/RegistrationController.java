package fantasticfour.controllers;

import fantasticfour.controllers.service.UserService;
import fantasticfour.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute User user, Model model) {
        var userFromDb = userService.findByUsername(user.getUsername());
        if (userFromDb != null) {
            model.addAttribute("message", "User exist!");
            return "registration";
        }
        userService.addUser(user);
        return "redirect:/login";
    }
}
