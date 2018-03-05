package com.rves.controller;

import com.rves.pojo.TypeRoom;
import com.rves.services.TypeRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class TypeRoomController {
    private TypeRoomService service;

    @Autowired
    public void setService(TypeRoomService service) {
        this.service = service;
    }


    @RequestMapping("/type/list")
    public String list(Model model){
        List<TypeRoom> typeRoomList = service.list();
        model.addAttribute("typeRoomList", typeRoomList);
        return "/type/list";
    }

    @RequestMapping("/type/delete/{id}")
    public String delete(@PathVariable Integer id, Model model){
        service.delete(id);
        return "redirect:/type/list";
    }

    @RequestMapping("/type/edit/{id}")
    public String edit(@PathVariable  Integer id, Model model){
        TypeRoom typeRoom = service.getById(id);
        List<TypeRoom> typeRoomList = service.list();
        model.addAttribute("typeRoomList", typeRoomList);
        model.addAttribute("typeRoom", typeRoom);
        return "/type/form";
    }

    @RequestMapping("/type/new")
    public String newCateg(Model model){
        model.addAttribute("typeRoom", new TypeRoom());
        return "/type/form";
    }

    @RequestMapping(value = "/type/save", method = RequestMethod.POST)
    public String save(TypeRoom typeRoom){
        service.save(typeRoom);
        return "redirect:/type/list";
    }

    @RequestMapping("/type/details/{id}")
    public String details(@PathVariable Integer id, Model model){
        model.addAttribute("types", service.getById(id));
        return "/type/details";
    }
}
