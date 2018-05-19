package com.rves.services;


import com.rves.Dto.BookingDto;
import com.rves.Dto.CurrentFilterBooking;
import com.rves.pojo.Booking;
import com.rves.pojo.Room;
import com.rves.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService implements PojoService<Booking> {

    private BookingRepository repository;
    private RoomsService roomService;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoomService(RoomsService roomService) {
        this.roomService = roomService;
    }

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

    //booking time is coming to an end
    public List<Booking> bookingEndTimeList (){
        Calendar currentCalendar = Calendar.getInstance();
        Calendar departureCalendar = Calendar.getInstance();

        currentCalendar.setTime(new java.util.Date());
        long today = currentCalendar.get(Calendar.DAY_OF_MONTH);

        List<Booking> bookings = list();
        List<Booking> arrayInvalidBooking = new ArrayList<>();

        for (Booking booking : bookings) {
            departureCalendar.setTime(booking.getDate_of_departure());
            long bookingEndDay = departureCalendar.get(Calendar.DAY_OF_MONTH);

            if (today == bookingEndDay){
                arrayInvalidBooking.add(booking);
            }
        }
        return arrayInvalidBooking;
    }

    public Booking saveFromDto (BookingDto bookingDto) {
        Booking booking = new Booking();
        booking.setDate_buking(bookingDto.getDate_buking());
        booking.setRoom(bookingDto.getRoom());
        booking.setArrival_date(bookingDto.getArrival_date());
        booking.setDate_of_departure(bookingDto.getDate_of_departure());
        booking.setUser(userService.getCurrentLoggedInUser());
        booking.setCanceled(false);
        return repository.save(booking);
    }

    /**
     * @param fromDate
     * @param toDate
     * @param type
     * @return freeRoom
     */

    public Room freeRoomSearch(java.sql.Date fromDate , java.sql.Date toDate, int type){

        List<Booking> bookings = list();
        List<Room> rooms = roomService.list();

        outer:  for (Room currentRoom:rooms) {
            if (!currentRoom.getType().getId().equals(type)&& currentRoom.isCleaningRequired()) {continue;}
            for (Booking currentBooking:bookings) {
                if (!currentBooking.isCanceled()){
                    if (currentRoom.getId().equals(currentBooking.getRoom().getId())) {
                        if (  !((fromDate .after(currentBooking.getDate_of_departure()))||(toDate.before(currentBooking.getArrival_date())))  ){
                            continue outer;
                        }
                    }
                }
            }
            return currentRoom;
        }
        return null;
    }

    public List<Booking> findAllMatchingCriteria(CurrentFilterBooking filterBooking){

        filterBooking.parse();
        List<Booking> result = new ArrayList<>();

        ///* получаю все записи
        result.addAll(list());
        if (filterBooking.filterIsEmpty())
            return result;

        Date dateFrom = filterBooking.getDateFromFilter();
        Date dateTo = filterBooking.getDateToFilter();
        Integer roomFilter = filterBooking.getRoomFilter();
        Date dateFromArrivalFilter = filterBooking.getDateFromArrivalFilter();
        Date dateFromDepartureFilter = filterBooking.getDateFromDepartureFilter();
        Date dateToArrivalFilter = filterBooking.getDateToArrivalFilter();
        Date dateToDepartureFilter = filterBooking.getDateToDepartureFilter();
        Integer adminFilter = filterBooking.getAdminFilter();


        // Filter by FromDate
        if (dateFrom != null){
            result = result.stream()
                    .filter(e -> e.getDate_buking().compareTo(dateFrom) >= 0)
                    .collect(Collectors.toList());
        }

        /* Filter by ToDate*/
        if (dateTo != null){
            result = result.stream()
                    .filter(e -> e.getDate_buking().compareTo(dateTo) <= 0)
                    .collect(Collectors.toList());
        }

        /* Filter by Room*/
        if (roomFilter != null){
            result = result.stream()
                    .filter(e -> e.getRoom().getNo().equals(roomFilter))
                    .collect(Collectors.toList());
        }

        /* Filter by dateArrival*/
        if (dateFromArrivalFilter != null){
            result = result.stream()
                    .filter(e -> e.getArrival_date().compareTo(dateFromArrivalFilter) >= 0)
                    .collect(Collectors.toList());
        }

        if (dateToArrivalFilter != null){
            result = result.stream()
                    .filter(e -> e.getArrival_date().compareTo(dateToArrivalFilter) <= 0)
                    .collect(Collectors.toList());
        }

        /* Filter by dateDeparture*/
        if (dateFromDepartureFilter != null){
            result = result.stream()
                    .filter(e -> e.getDate_of_departure().compareTo(dateFromDepartureFilter) >= 0)
                    .collect(Collectors.toList());
        }

        if (dateToDepartureFilter != null){
            result = result.stream()
                    .filter(e -> e.getDate_of_departure().compareTo(dateToDepartureFilter) <= 0)
                    .collect(Collectors.toList());
        }

        if (adminFilter != null){
            result = result.stream()
                    .filter(e -> e.getUser().getId().equals(adminFilter))
                    .collect(Collectors.toList());
        }

        return result;

    }

}
