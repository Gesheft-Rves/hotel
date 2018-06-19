package com.rves.controller;

import com.rves.pojo.RoomType;
import com.rves.services.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RoomTypeController {
    private RoomTypeService service;

    @Autowired
    public void setService(RoomTypeService service) {
        this.service = service;
    }


    @RequestMapping("/type/list")
    public String list(Model model){
        model.addAttribute("typeRoomList", service.list());
        return "type/list";
    }

    @RequestMapping("/type/delete/{id}")
    public String delete(@PathVariable Integer id, Model model){
        service.delete(id);
        return "redirect:/type/list";
    }

    @RequestMapping("/type/edit/{id}")
    public String edit(@PathVariable  Integer id, Model model){
        model.addAttribute("typeRoomList", service.list());
        model.addAttribute("typeRoom", service.getById(id));
        return "type/form";
    }

    @RequestMapping("/type/new")
    public String newCateg(Model model){
        model.addAttribute("typeRoom", new RoomType());
        return "type/form";
    }

    @RequestMapping(value = "/type/save", method = RequestMethod.POST)
    public String save(RoomType roomType){
        service.save(roomType);
        return "redirect:/type/list";
    }

    @RequestMapping("/type/details/{id}")
    public String details(@PathVariable Integer id, Model model){
        model.addAttribute("types", service.getById(id));
        return "type/details";
    }
}
