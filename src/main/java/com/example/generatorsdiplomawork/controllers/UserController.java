package com.example.generatorsdiplomawork.controllers;

import com.example.generatorsdiplomawork.config.UserDetailsImpl;
import com.example.generatorsdiplomawork.entities.ElectricCurrentType;
import com.example.generatorsdiplomawork.entities.Unit;
import com.example.generatorsdiplomawork.entities.User;
import com.example.generatorsdiplomawork.entities.UserRoles;
import com.example.generatorsdiplomawork.repositories.UnitRepository;
import com.example.generatorsdiplomawork.repositories.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UnitRepository unitRepository;
    private final UserRepository userRepository;

    public UserController(UserRepository repository, UnitRepository unitRepository, UserRepository userRepository) {
        this.unitRepository = unitRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String users(Model model) {
        List<User> all1 = userRepository.findAll();
        model.addAttribute("users", all1);
        List<Unit> all = unitRepository.findAll();
        model.addAttribute("units", all);
        model.addAttribute("roles", Arrays.asList(UserRoles.values()));
        return "usersPage";
    }

    @GetMapping("/user")
    public String userEdit(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        model.addAttribute("actionPath", "/users/user");
        model.addAttribute("isAdmin", userDetails.getUser().getRole().equals(UserRoles.ADMIN));
        model.addAttribute("user", userDetails.getUser());
        model.addAttribute("units", unitRepository.findAll());

        List<UserRoles> userRoles = new ArrayList<>(Arrays.stream(UserRoles.values()).toList());
        userRoles.remove(userDetails.getUser().getRole());
        List<UserRoles> userRolesTmp = new ArrayList<>();
        userRolesTmp.add(userDetails.getUser().getRole());
        userRolesTmp.addAll(userRoles);
        model.addAttribute("roles", userRolesTmp);
        return "userPage";
    }

    @GetMapping("/user/{id}")
    public String userEditById(@AuthenticationPrincipal UserDetailsImpl userDetails,
                               @PathVariable Long id, Model model) {
        model.addAttribute("actionPath", "/users/user");
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        model.addAttribute("units", unitRepository.findAll());

        List<UserRoles> userRoles = new ArrayList<>(Arrays.stream(UserRoles.values()).toList());
        userRoles.remove(user.getRole());
        List<UserRoles> userRolesTmp = new ArrayList<>();
        userRolesTmp.add(user.getRole());
        userRolesTmp.addAll(userRoles);
        model.addAttribute("isAdmin", userDetails.getUser().getRole().equals(UserRoles.ADMIN));
        model.addAttribute("roles", userRolesTmp);
        return "userPage";
    }

    @GetMapping("/user/create")
    public String userCreate(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        model.addAttribute("isAdmin", userDetails.getUser().getRole().equals(UserRoles.ADMIN));
        model.addAttribute("actionPath", "/users/user/create");
        model.addAttribute("user", new User());
        model.addAttribute("units", unitRepository.findAll());
        model.addAttribute("roles", UserRoles.values());
        return "userPage";
    }

    @PostMapping("/user/create")
    public String userCreatePost(String login, String password, String unit, String role) {
        password = new BCryptPasswordEncoder(5).encode(password);
        Unit unitByName = unitRepository.findById(Long.valueOf(unit)).get();
        userRepository.save(new User(login, password, unitByName, UserRoles.valueOf(role)));
        return "redirect:/users";
    }

    @PostMapping("/user")
    public String userEditPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                               String login, String password, String unit, String role) {
        password = new BCryptPasswordEncoder(5).encode(password);
        Unit unitByName = unitRepository.findById(Long.valueOf(unit)).get();
        User user = userRepository.findByLogin(login);
        user.setLogin(login);
        user.setPassword(password);
        if (userDetails.getUser().getRole() == UserRoles.ADMIN) {
            user.setRole(UserRoles.valueOf(role));
        }
        user.setUnit(unitByName);
        userRepository.save(user);
        if (login.equals(userDetails.getUser().getLogin())){
            userDetails.setUser(user);
        }
        return "redirect:/users/user";
    }
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }
}
