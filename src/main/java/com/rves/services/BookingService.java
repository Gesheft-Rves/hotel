package com.rves.services;


import com.rves.Dto.BookingDto;
import com.rves.pojo.Booking;
import com.rves.pojo.Room;
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
    //написать метод в сервисе броней который будет принимать две даты и возвращать объект Room, свободной комнаты
    //нужно найти комнату, которой нет в списке броней на промежуток дат
    public Room freeRoomSearch(java.sql.Date d1, java.sql.Date d2, int type){
        List<Booking> bookings = list();
        List<Room> rooms = roomService.list();

        for (int i = 0; i < rooms.size(); i++) {
            for (int j = 0; j < bookings.size(); j++) {
                if (rooms.get(i).getId().equals(bookings.get(j).getRoom().getId())){
                  if (bookings.get(j).getArrival_date().equals(d1)&&bookings.get(j).getDate_of_departure().equals(d2)){
                      continue;
                  }
                }  else if (rooms.get(i).getType().equals(type)){
                    return rooms.get(i);
                } break;
            }
        }
        return null ;
    }

}
