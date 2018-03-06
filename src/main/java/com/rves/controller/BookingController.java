package com.rves.controller;

import com.rves.Dto.BookingDto;
import com.rves.pojo.Booking;
import com.rves.pojo.Room;
import com.rves.pojo.TypeRoom;
import com.rves.services.BookingService;
import com.rves.services.RoomsService;
import com.rves.services.TypeRoomService;
import com.rves.validator.BookingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.ParseException;
import java.util.List;

@Controller
public class BookingController {

    private BookingService bookingService;
    private RoomsService roomsService;
    private BookingValidator bookingValidator;
    private TypeRoomService typeRoomService;

    @Autowired
    public void setTypeRoomService(TypeRoomService typeRoomService) {
        this.typeRoomService = typeRoomService;
    }

    @Autowired
    public void setRoomsService(RoomsService roomsService) {
        this.roomsService = roomsService;
    }

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Autowired
    public void setBookingValidator(BookingValidator bookingValidator) {
        this.bookingValidator = bookingValidator;
    }

    @RequestMapping("/booking/list")
    public String list(Model model){
        List<Booking> bookings = bookingService.list();
        model.addAttribute("bookings", bookings);
        return "/booking/list";
    }

    @RequestMapping("/booking/delete/{id}")
    public String delete(@PathVariable Integer id, Model model){
        bookingService.delete(id);
        return "redirect:/booking/list";
    }

    @RequestMapping("/booking/edit/{id}")
    public String edit(@PathVariable  Integer id, Model model){
        Booking booking = bookingService.getById(id);
        List<Room> rooms = roomsService.list();
        model.addAttribute("roomslist", rooms);
        model.addAttribute("booking", booking);
        return "/booking/form";
    }

    @RequestMapping("/booking/new")
    public String newBooking(Model model){
        List<Room> rooms =  roomsService.list();
        List<TypeRoom> typeRoomList = typeRoomService.list();
        model.addAttribute("typeRoomList",typeRoomList);
        model.addAttribute("roomslist", rooms);
        model.addAttribute("booking", new BookingDto());
        return "/booking/form";
    }

    @RequestMapping(value = "/booking/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("booking") BookingDto bookingDto , BindingResult bindingResult, Model model) throws ParseException {
        Room freeRoom = bookingService.freeRoomSearch(
                bookingDto.getArrival_date(),
                bookingDto.getDate_of_departure(),
                bookingDto.getRoomType()
        );

        bookingDto.setRoom(freeRoom);

        bookingValidator.validate(bookingDto, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("typeRoomList",typeRoomService.list());
            model.addAttribute("roomslist",  roomsService.list());
            return "/booking/form";
        }
        Booking createdBookingEntry = bookingService.saveFromDto(bookingDto);
        return "redirect:/booking/details/" + createdBookingEntry.getId();
    }

    @RequestMapping("/booking/details/{id}")
    public String details(@PathVariable Integer id, Model model){
        model.addAttribute("booking", bookingService.getById(id));
        return "/booking/details";

    }
}
