package com.rves.controller;

import com.rves.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    private UserService service;

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }

    @RequestMapping("/users/list")
    public String list(Model model){
        model.addAttribute("users", service.list());
        return "/users/list";
    }

//    @RequestMapping("/users/delete/{id}")
//    public String delete(@PathVariable Integer id, Model model){
//        service.delete(id);
//        return "redirect:/users/list";
//    }
//
//    @RequestMapping("/users/edit/{id}")
//    public String edit(@PathVariable Integer id, Model model){
//        model.addAttribute("usersList", service.list());
//        model.addAttribute("user", service.getById(id));
//        return "/users/form";
//    }
//
//    @RequestMapping("/users/new")
//    public String newUser(Model model){
//        model.addAttribute("user", new User());
//        return "/users/form";
//    }
//
//    @RequestMapping(value = "/users/save", method = RequestMethod.POST)
//    public String save(User user){
//        service.save(user);
//        return "redirect:/users/list";
//    }

    @RequestMapping("/users/details/{id}")
    public String details(@PathVariable Integer id, Model model){
        model.addAttribute("user", service.getById(id));
        return "/users/details";
    }
}
