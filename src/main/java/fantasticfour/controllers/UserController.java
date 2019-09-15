package fantasticfour.controllers;

import fantasticfour.controllers.dao.UserDao;
import fantasticfour.entity.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("userlist", userDao.findAll());

        return "userList";
    }

    @GetMapping("{userId}")
    public String userEditForm(@PathVariable int userId, Model model) {
        model.addAttribute("user", userDao.findById(userId).orElse(null));
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @GetMapping("block/{userId}")
    public String blockUser(@PathVariable int userId) {
        var user = userDao.findById(userId).orElse(null);
        if (user != null) {
            user.getRoles().clear();
            user.getRoles().add(Role.BLOCKED);
            userDao.save(user);
        }
        return "redirect:/orders";
    }

    @PostMapping
    public String userSave(@RequestParam("userId") int userId,
                           @RequestParam Map<String, String> form,
                           @RequestParam String username) {
        var user = userDao.findById(userId).orElse(null);
        if (user != null) {
            user.setUsername(username);
            user.getRoles().clear();
            user.getRoles().add(Role.valueOf(form.get("role")));
            userDao.save(user);
        }
        return "redirect:/user";
    }




}
