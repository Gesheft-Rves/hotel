package com.rves.controller;


import com.rves.Dto.UserDto;
import com.rves.services.SecurityServiceImpl;
import com.rves.services.UserService;
import com.rves.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizationController {
    private UserService userService;
    private UserValidator userValidator;
    private SecurityServiceImpl securityServiceImpl;

    @Autowired
    public void setSecurityServiceImpl(SecurityServiceImpl securityServiceImpl) {
        this.securityServiceImpl = securityServiceImpl;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserValidator(UserValidator userValidator) {
        this.userValidator = userValidator;
    }

    @RequestMapping("/login")
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model){
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "/login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new UserDto());

        return "/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") UserDto userForm, BindingResult bindingResult, Model model) {

        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {

            return "/register";
        }

        userService.createUser(userForm);

        securityServiceImpl.autologin(userForm.getUsername(), userForm.getMatchingPassword());

        return "redirect:/home";
    }


}
