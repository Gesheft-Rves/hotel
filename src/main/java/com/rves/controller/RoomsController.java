package com.rves.controller;

import com.rves.pojo.Room;
import com.rves.services.RoomsService;
import com.rves.services.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RoomsController {

    private RoomsService service;
    private RoomTypeService roomTypeService;

    @Autowired
    public void setRoomTypeService(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    @Autowired
    public void setService(RoomsService service) {
        this.service = service;
    }

    @RequestMapping("/rooms/list")
    public String list(Model model){
        model.addAttribute("rooms", service.list());
        return "/rooms/list";
    }

    @RequestMapping("/rooms/delete/{id}")
    public String delete(@PathVariable Integer id, Model model){
        service.delete(id);
        return "redirect:/rooms/list";
    }

    @RequestMapping("/rooms/edit/{id}")
    public String edit(@PathVariable  Integer id, Model model){
        model.addAttribute("rooms", service.list());
        model.addAttribute("room", service.getById(id));
        model.addAttribute("roomTypes", roomTypeService.list());
        return "/rooms/form";
    }

    @RequestMapping("/rooms/new")
    public String newGroup(Model model){
        model.addAttribute("roomTypes", roomTypeService.list());
        model.addAttribute("room", new Room());
        return "/rooms/form";
    }

    @RequestMapping(value = "/rooms/save", method = RequestMethod.POST)
    public String save(Room room){
        service.save(room);
        return "redirect:/rooms/list";
    }

    @RequestMapping("/rooms/details/{id}")
    public String details(@PathVariable Integer id, Model model){
        model.addAttribute("room", service.getById(id));
        model.addAttribute("roomTypes", roomTypeService.list());
        return "/rooms/details";

    }

}
