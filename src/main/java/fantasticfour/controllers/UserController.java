package fantasticfour.controllers;

import fantasticfour.controllers.service.UserService;
import fantasticfour.entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    private static Logger logger = LogManager.getLogger();


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("userlist", userService.findAll());

        return "userList";
    }

    @GetMapping("{userId}")
    public String userEditForm(@PathVariable int userId, Model model) {
        model.addAttribute("user", userService.findById(userId));
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @GetMapping("block/{userId}")
    public String blockUser(@PathVariable int userId) {
        userService.blockUser(userId);
        logger.info("User id={} was blocked", userId);
        return "redirect:/orders";
    }

    @PostMapping
    public String userSave(@RequestParam("userId") int userId,
                           @RequestParam Map<String, String> form,
                           @RequestParam String username) {
        userService.saveUser(userId, username, form);
        return "redirect:/user";
    }
}
