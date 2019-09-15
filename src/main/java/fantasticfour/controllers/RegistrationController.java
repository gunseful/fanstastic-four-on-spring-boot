package fantasticfour.controllers;

import fantasticfour.controllers.dao.UserDao;
import fantasticfour.entity.Role;
import fantasticfour.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {

    private final UserDao userDao;

    public RegistrationController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {
        var userFromDb = userDao.findByUsername(user.getUsername());
        if (userFromDb != null) {
            model.addAttribute("message", "User exist!");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userDao.save(user);
        return "redirect:/login";
    }
}
