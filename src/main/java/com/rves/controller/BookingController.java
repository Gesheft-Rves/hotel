package com.rves.controller;

import com.rves.pojo.Booking;
import com.rves.pojo.Rooms;
import com.rves.services.BookingService;
import com.rves.services.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookingController {

    private BookingService service;
    private RoomsService roomsService;

    @Autowired
    public void setRoomsService(RoomsService roomsService) {
        this.roomsService = roomsService;
    }

    @Autowired
    public void setService(BookingService service) {
        this.service = service;
    }

    @RequestMapping("/booking/list")
    public String list(Model model){
        List<Booking> bookings = service.list();
        model.addAttribute("bookings", bookings);
//        for (int i = 0; i < bookings.size(); i++) {
//            System.out.println(bookings.get(i).toString());
//        }
        return "/booking/list";
    }

    @RequestMapping("/booking/delete/{id}")
    public String delete(@PathVariable Integer id, Model model){
        service.delete(id);
        return "redirect:/booking/list";
    }

    @RequestMapping("/booking/edit/{id}")
    public String edit(@PathVariable  Integer id, Model model){
        Booking booking = service.getById(id);
        List<Rooms> rooms = roomsService.list();
        model.addAttribute("bookingList", rooms);
        model.addAttribute("booking", booking);
        return "/booking/form";
    }

    @RequestMapping("/booking/new")
    public String newGroup(Model model){
        List<Rooms> rooms = (ArrayList<Rooms>) roomsService.list();
        model.addAttribute("roomslist", rooms);
        model.addAttribute("booking", new Booking());
        return "/booking/form";
    }

    @RequestMapping(value = "/booking/save", method = RequestMethod.POST)
    public String save(Booking booking){
        service.save(booking);
        return "redirect:/booking/list";
    }

    @RequestMapping("/booking/details/{id}")
    public String details(@PathVariable Integer id, Model model){
        model.addAttribute("booking", service.getById(id));
        return "/booking/details";

    }
}
