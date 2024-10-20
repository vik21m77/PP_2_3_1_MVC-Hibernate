package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.Model.User;
import web.Service.UserService;

import java.util.List;

@Controller
@RequestMapping(value = "/")
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String showAllUsers(Model model) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "all-users.html";
    }

    @GetMapping (value = "/addNewUser")
    public String addNewUser(Model model) {
        model.addAttribute("user", new User());
        return "user-addForm.html";
    }

    @PostMapping()
    public String saveNewUser(@ModelAttribute("user") User user) {
        userService.saveOrUpdateUser(user);
        return "redirect:/";
    }

    @GetMapping(value = "/update")
    public String updateUser(@RequestParam("id") long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "user-addForm.html";
    }

    @GetMapping(value = "/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUser(id);
        return "redirect:/";
    }
}
