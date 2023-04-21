package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.molel.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public AdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getAdminPage(Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        return "admin/admin";
    }

    @GetMapping("/user/page")
    public String getAddUserPage(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", new User());
        return "admin/save";
    }

    @PostMapping("/user")
    public String addUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin/admin";
    }

    @GetMapping("/user/{id}")
    public String getUserEditPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("roles", roleService.getAllRoles());
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "admin/edit";
    }

    @PutMapping("/user")
    public String editUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin/admin";
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/admin";
    }
}
