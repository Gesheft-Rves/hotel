package com.rves.services;


import com.rves.Dto.BookingDto;
import com.rves.Dto.CurrentFilterBooking;
import com.rves.Dto.CurrentFilterRoom;
import com.rves.pojo.Booking;
import com.rves.pojo.Room;
import com.rves.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
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

    //generation of a list for report of input parameters
    public List<Booking> generationForReport (Timestamp dateFromReport, Timestamp dateToReport, Integer userId, Boolean canceledBooking){

        List<Booking> result = new ArrayList<>();
        result.addAll(list());

        final long dateFromTime = dateFromReport.getTime();
        if (dateFromReport != null){
            result = result.stream()
                    .filter(booking -> {
                        long bookingTime = booking.getDateBuking().getTime();
                        return bookingTime > dateFromTime;
                    })
                    .collect(Collectors.toList());
        }

        final long dateToTime = dateToReport.getTime();
        if (dateToReport != null){
            result = result.stream()
                    .filter(booking -> {
                        long bookingTime = booking.getDateBuking().getTime();
                        return bookingTime < dateToTime;
                    })
                    .collect(Collectors.toList());
        }

        if (userId!= null){
            result = result.stream()
                    .filter(e -> e.getUser().getId().equals(userId))
                    .collect(Collectors.toList());
        }

        if (canceledBooking){
            result = result.stream()
                    .filter(Booking::isCanceled)
                    .collect(Collectors.toList());
        }

        return result;
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
            departureCalendar.setTime(booking.getDateOfDeparture());
            long bookingEndDay = departureCalendar.get(Calendar.DAY_OF_MONTH);

            if (today == bookingEndDay){
                arrayInvalidBooking.add(booking);
            }
        }
        return arrayInvalidBooking;
    }

    public Booking saveFromDto (BookingDto bookingDto) {
        Booking booking = new Booking();
        booking.setDateBuking(bookingDto.getDateBuking());
        booking.setRoom(bookingDto.getRoom());
        booking.setArrivalDate(bookingDto.getArrivalDate());
        booking.setDateOfDeparture(bookingDto.getDateOfDeparture());
        booking.setUser(userService.getCurrentLoggedInUser());
        booking.setCanceled(false);
        return repository.save(booking);
    }

    public BookingDto saveDtoToBooking (Booking booking) {
        BookingDto tepmBookingDTO = new BookingDto();

        tepmBookingDTO.setId(booking.getId());
        tepmBookingDTO.setDateBuking(booking.getDateBuking());
        tepmBookingDTO.setArrivalDate(booking.getArrivalDate());
        tepmBookingDTO.setDateOfDeparture(booking.getDateOfDeparture());
        tepmBookingDTO.setCanceled(booking.isCanceled());
        tepmBookingDTO.setUser(booking.getUser());
        tepmBookingDTO.setRoom(booking.getRoom());

        return tepmBookingDTO;
    }

    public List<Room> findAllMatchingCriteria(CurrentFilterRoom filterRoom){

        filterRoom.parse();
        List<Booking> bookings = list();
        List<Room> rooms = roomService.list();
        List<Room> result = new ArrayList<>();

        Timestamp arrivalDate = filterRoom.getDateFromArrivalFilter();
        Timestamp departureDate = filterRoom.getDateFromDepartureFilter();
        Integer roomType = filterRoom.getRoomTypeFilter();

        outer:
        for (Room currentRoom : rooms) {
            if (currentRoom.getType().getId().equals(roomType) && (currentRoom.isCleaningRequired())) {
                continue;
            }
            for (Booking currentBooking : bookings) {
                if (!currentBooking.isCanceled()) {
                    if (currentRoom.getId().equals(currentBooking.getRoom().getId())) {
                        if (!((arrivalDate.after(currentBooking.getDateOfDeparture()))||(departureDate.before(currentBooking.getArrivalDate())))  ){
                            continue outer;
                        }
                    }
                }
            }
            result.add(currentRoom);
        }

        result = result.stream()
                .filter(e -> e.getType().getId().equals(roomType))
                .collect(Collectors.toList());

        return result;
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
                    .filter(e -> e.getDateBuking().compareTo(dateFrom) >= 0)
                    .collect(Collectors.toList());
        }

        /* Filter by ToDate*/
        if (dateTo != null){
            result = result.stream()
                    .filter(e -> e.getDateBuking().compareTo(dateTo) <= 0)
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
                    .filter(e -> e.getArrivalDate().compareTo(dateFromArrivalFilter) >= 0)
                    .collect(Collectors.toList());
        }

        if (dateToArrivalFilter != null){
            result = result.stream()
                    .filter(e -> e.getArrivalDate().compareTo(dateToArrivalFilter) <= 0)
                    .collect(Collectors.toList());
        }

        /* Filter by dateDeparture*/
        if (dateFromDepartureFilter != null){
            result = result.stream()
                    .filter(e -> e.getDateOfDeparture().compareTo(dateFromDepartureFilter) >= 0)
                    .collect(Collectors.toList());
        }

        if (dateToDepartureFilter != null){
            result = result.stream()
                    .filter(e -> e.getDateOfDeparture().compareTo(dateToDepartureFilter) <= 0)
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
