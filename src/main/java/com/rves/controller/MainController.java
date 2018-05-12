package com.rves.controller;

import com.rves.services.BookingService;
import com.rves.services.RoomsService;
import com.rves.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoomsService roomsService;
    @Autowired
    private BookingService bookingService;

    @RequestMapping({"/", "/home"})
    public String mainController(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username
        String role = String.valueOf(userService.loadUserByUsername(name).getAuthorities());

        switch (role) {
            case ("ROLE_SUPER"):
                model.addAttribute("message", "Добро пожаловать в отель, " + name);
                updateModelForAdmin(model);
                return "adminIndex";
            case "[ROLE_HOTEL_CLEANER]":
                model.addAttribute("message", "Добро пожаловать в отель, " + name);
                updateModelForCleaner(model);
                return "cleanerIndex";
            case "[ROLE_HOTEL_ADMIN]":
                updateModelForUser(model);
                model.addAttribute("message", "Добро пожаловать в отель. Создай мне чуточку броней на сегодня, " + name);
                return "userindex";
        }
        return "index";
    }

    private void updateModelForAdmin(Model model){
        model.addAttribute("rooms",roomsService.unclearedRooms());
        model.addAttribute("bookings",bookingService.bookingEndTimeList());
    }

    private void updateModelForCleaner(Model model){
        model.addAttribute("rooms",roomsService.unclearedRooms());
    }

    private void updateModelForUser(Model model){
        model.addAttribute("bookings",bookingService.bookingEndTimeList());
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

}