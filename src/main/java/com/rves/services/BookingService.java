package com.rves.services;


import com.rves.Dto.BookingDto;
import com.rves.pojo.Booking;
import com.rves.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService implements PojoService<Booking> {

    private BookingRepository repository;
    private RoomsService roomService;

    @Autowired
    public void setRepository(BookingRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Booking> list() {
        return repository.findAll();
    }

    @Override
    public Booking getById(Integer id) {
        return repository.getOne(id);
    }

    @Override
    public Booking save(Booking obj) {
        return repository.save(obj);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    public void saveFromDto (BookingDto bookingDto) {
        Booking booking = new Booking();
        booking.setDate_buking(bookingDto.getDate_buking());
        booking.setRoom(bookingDto.getRoom());
        booking.setArrival_date(bookingDto.getArrival_date());
        booking.setDate_of_departure(bookingDto.getDate_of_departure());
        repository.save(booking);
    }
}
