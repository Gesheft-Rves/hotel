package com.rves.controller;

import com.rves.Dto.AjaxResponseBody;
import com.rves.Dto.BookingDto;
import com.rves.Dto.CurrentFilterBooking;
import com.rves.pojo.Booking;
import com.rves.pojo.Room;
import com.rves.services.BookingService;
import com.rves.services.RoomTypeService;
import com.rves.services.RoomsService;
import com.rves.services.UserService;
import com.rves.validator.BookingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookingController {

    private BookingService bookingService;
    private RoomsService roomsService;
    private BookingValidator bookingValidator;
    private RoomTypeService roomTypeService;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoomTypeService(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
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
        model.addAttribute("bookings", bookingService.list());
        model.addAttribute("users",userService.list());
        return "/booking/list";
    }

    @RequestMapping("/booking/delete/{id}")
    public String delete(@PathVariable Integer id, Model model){
        bookingService.delete(id);
        return "redirect:/booking/list";
    }

    @RequestMapping("/booking/edit/{id}")
    public String edit(@PathVariable  Integer id, Model model){
        model.addAttribute("roomTypes", roomTypeService.list());
        model.addAttribute("roomslist", roomsService.list());
        model.addAttribute("booking", bookingService.getById(id));
        return "/booking/forEdit";
    }

    @RequestMapping("/booking/new")
    public String newBooking(Model model){
        model.addAttribute("roomTypes", roomTypeService.list());
        model.addAttribute("roomslist", roomsService.list());
        model.addAttribute("booking", new BookingDto());
        return "/booking/createbooking";
    }

    @RequestMapping(value = "/booking/saveEditedRoom", method = RequestMethod.POST)
    public String saveEditedRoom(@ModelAttribute("booking") Booking booking, Integer id, BookingDto bookingDto , BindingResult bindingResult, Model model){
            booking = bookingService.getById(id);
            booking.setCanceled(bookingDto.isCanceled());
            booking.setUser(bookingDto.getUser());
            booking.setRoom(bookingDto.getRoom());
            booking.setArrival_date(bookingDto.getArrival_date());
            booking.setDate_of_departure(bookingDto.getDate_of_departure());
            booking.setDate_buking(bookingDto.getDate_buking());
            bookingService.save(booking);
            return "redirect:/booking/details/" + booking.getId();
    }


    @RequestMapping(value = "/booking/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("booking") BookingDto bookingDto , BindingResult bindingResult, Model model){
        Room freeRoom = bookingService.freeRoomSearch(
                bookingDto.getArrival_date(),
                bookingDto.getDate_of_departure(),
                bookingDto.getRoomType().getId()
        );

        bookingDto.setRoom(freeRoom);
        bookingDto.setUser(userService.getCurrentLoggedInUser());

        bookingValidator.validate(bookingDto, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("roomTypes", roomTypeService.list());
            model.addAttribute("roomslist",  roomsService.list());
            return "/booking/createbooking";
        }
        Booking createdBookingEntry = bookingService.saveFromDto(bookingDto);
        return "redirect:/booking/details/" + createdBookingEntry.getId();
    }

    @RequestMapping("/booking/details/{id}")
    public String details(@PathVariable Integer id, Model model){
        model.addAttribute("booking", bookingService.getById(id));
        return "/booking/details";

    }

    @RequestMapping("/booking/api/filter")
    public ResponseEntity<?> filter(@Valid @RequestBody CurrentFilterBooking filter, Errors errors){
        AjaxResponseBody result = new AjaxResponseBody();
        if (errors.hasErrors()){
            result.setMsg(errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(",")));
            return ResponseEntity.badRequest().body(result);
        }
        List<Booking> bookings = bookingService.findAllMatchingCriteria(filter);
        result.setBookings(bookings);

        if (bookings.isEmpty()) {
            result.setMsg("no bookings found!");
        } else {
            result.setMsg("success");
        }

        return ResponseEntity.ok(result);
    }

    @RequestMapping("/booking/cancellation/{id}")
    public String reverseStatusRoom(@PathVariable Integer id, Model model){
        Booking booking = bookingService.getById(id);
        if (!booking.isCanceled()) {
            booking.setCanceled(true);
        } else if (booking.isCanceled()){
            booking.setCanceled(false);
        }
        bookingService.save(booking);
        model.addAttribute("booking", booking);
        return "/booking/details";
    }
}
