package com.rves.controller;

import com.rves.Dto.UserDto;
import com.rves.pojo.User;
import com.rves.services.SecurityServiceImpl;
import com.rves.services.UserService;
import com.rves.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityServiceImpl securityServiceImpl;

    @RequestMapping("/users/list")
    public String list(Model model){
        model.addAttribute("users", userService.list());
        return "users/list";
    }

    @RequestMapping("/users/delete/{id}")
    public String delete(@PathVariable Integer id, Model model){
        userService.delete(id);
        return "redirect:/users/list";
    }

    @RequestMapping("/users/edit/{id}")
    public String editUser(@PathVariable Integer id, Model model){
        model.addAttribute("usersList", userService.list());
        model.addAttribute("user", userService.getById(id));
        return "users/form";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registrationNewUser(Model model) {
        model.addAttribute("userForm", new UserDto());

        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registrationNewUser(@ModelAttribute("userForm") UserDto userForm, BindingResult bindingResult, Model model) {

        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {

            return "register";
        }

        userService.createUser(userForm);

        securityServiceImpl.autologin(userForm.getUsername(), userForm.getMatchingPassword());

        return "redirect:/home";
    }

    @RequestMapping(value = "/users/save", method = RequestMethod.POST)
    public String saveUser(User user, UserDto userDto , Integer id, BindingResult bindingResult, Model model){
        user = userService.getById(id);
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEnabled(userDto.isEnabled());
        user.setAuthorities(userDto.getAuthorities());
        userService.save(user);
        return "redirect:/users/list";
    }

    @RequestMapping("/users/details/{id}")
    public String details(@PathVariable Integer id, Model model){
        model.addAttribute("user", userService.getById(id));
        return "users/details";
    }
}
