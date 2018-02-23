package com.rves.controller;

import com.rves.pojo.Rooms;
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
public class RoomsController {

    private RoomsService service;

    @Autowired
    public void setService(RoomsService service) {
        this.service = service;
    }

    @RequestMapping("/rooms/list")
    public String list(Model model){
        List<Rooms> rooms = service.list();
        model.addAttribute("rooms", rooms);
        return "/rooms/list";
    }

    @RequestMapping("/rooms/delete/{id}")
    public String delete(@PathVariable Integer id, Model model){
        service.delete(id);
        return "redirect:/rooms/list";
    }

    @RequestMapping("/rooms/edit/{id}")
    public String edit(@PathVariable  Integer id, Model model){
        Rooms room = service.getById(id);
        List<Rooms> rooms = service.list();
        model.addAttribute("rooms", rooms);
        model.addAttribute("room", room);
        return "/rooms/form";
    }

    @RequestMapping("/rooms/new")
    public String newGroup(Model model){
        ArrayList<Rooms> rooms = (ArrayList<Rooms>) service.list();
        model.addAttribute("room", new Rooms());
        return "/rooms/form";
    }

    @RequestMapping(value = "/rooms/save", method = RequestMethod.POST)
    public String save(Rooms rooms){
        service.save(rooms);
        return "redirect:/rooms/list";
    }

    @RequestMapping("/rooms/details/{id}")
    public String details(@PathVariable Integer id, Model model){
        model.addAttribute("room", service.getById(id));
        return "/rooms/details";

    }

}
