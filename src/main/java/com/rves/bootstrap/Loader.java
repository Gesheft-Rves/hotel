package com.rves.bootstrap;


import com.rves.pojo.*;
import com.rves.repositories.BookingRepository;
import com.rves.repositories.RoomTypeRepository;
import com.rves.repositories.RoomsRepository;
import com.rves.repositories.UserRepository;
import com.rves.services.BookingService;
import com.rves.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class Loader implements ApplicationListener<ContextRefreshedEvent> {

    private RoomsRepository roomsRepository;
    private RoomTypeRepository roomTypeRepo;
    private BookingRepository bookingRepository;
    private BookingService bookingService;
    private UserService userService;
    private UserRepository userRepo;
    private static int userCount;
    private static int roomCount;
    private static int roomTypeCount;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Autowired
    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    public void setRoomsRepository(RoomsRepository roomsRepository) {
        this.roomsRepository = roomsRepository;
    }

    @Autowired
    public void setRoomTypeRepo(RoomTypeRepository roomTypeRepo) {
        this.roomTypeRepo = roomTypeRepo;
    }

    @Autowired
    public void setBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (1==1) {
            List<User> users =  userService.list();
            String role;
            for (User user : users) {
                role =  String.valueOf(user.getAuthorities());
                if(role.equals("[ROLE_SUPER]")){
                    return;
                }
            } createSuperUser();
            return;
        }

        for (int t = 0; t < 5; t++ ){
            RoomType roomType = createRoomType();
        }

        for (int i = 0; i < 10; i++){
            Room room = createRoom();
        }

        for (int j = 0; j < 5; j++) {
            User user = createUser();
            for (int i = 0; i < 5; i++) {
                createBooking(user);
            }
        }
    }

    private User createSuperUser() {
        User user = new User();

        user.setAuthorities(Arrays.asList(Role.ROLE_SUPER));
        user.setUsername("admin");
        user.setPassword("admin");
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        userRepo.save(user);
        return user;
    }

    private User createUser(){
        User user = new User();

        user.setAuthorities(Arrays.asList(Role.values()));
        user.setUsername(String.format("User_%d", ++userCount));
        user.setPassword("pass" + userCount);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        userRepo.save(user);
        return user;
    }

    private RoomType createRoomType(){
        roomTypeCount++;
        RoomType roomType = new RoomType();
        roomType.setName(roomTypeCount + " room");
        roomTypeRepo.save(roomType);
        return roomType;
    }

    private Room createRoom(){
        int randomType = ThreadLocalRandom.current().nextInt(roomTypeRepo.findAll().size());
        List<RoomType> roomTypeList = roomTypeRepo.findAll();
        RoomType value = roomTypeList.get(randomType);
        roomCount ++;
        Room room = new Room();
        room.setNo(roomCount);
        room.setType(value);
        room.setCleaningRequired(true);
        roomsRepository.save(room);
        return room;
    }

    private Booking createBooking(User user){
        java.util.Date d = new java.util.Date();
        long date = d.getTime();
        int rand = ThreadLocalRandom.current().nextInt(1, 9 + 1);

        Booking booking = new Booking();
        booking.setDateBuking(new Timestamp(date));
        // рандом дата
        booking.setArrivalDate(new Timestamp(new java.util.Date().getTime()));
        //  +24 часа
        booking.setDateOfDeparture(
               new Timestamp( booking.getArrivalDate().getTime() + 1 * 24 * 60 * 60 * 1000));

        // rand - (1-10) гетает комнату с рандомным индексом
        booking.setRoom(roomsRepository.getOne(rand));

        booking.setUser(user);

        bookingRepository.save(booking);
        return booking;
    }

}
