package com.rves.bootstrap;


import com.rves.pojo.Role;
import com.rves.pojo.User;
import com.rves.repositories.BookingRepository;
import com.rves.repositories.RoomTypeRepository;
import com.rves.repositories.RoomsRepository;
import com.rves.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Arrays;
import java.util.GregorianCalendar;

@Component
public class Loader implements ApplicationListener<ContextRefreshedEvent> {

    private RoomsRepository roomsRepository;
    private RoomTypeRepository roomTypeRepo;
    private BookingRepository bookingRepository;
    private UserRepository userRepo;
    private static int userCount;

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
        for (int j = 0; j < 3; j++) {
            User user = createUser();
        }
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

    private static Date getRandDate(){
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(2010, 2017);
        gc.set(gc.YEAR, year);

        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        return new Date(gc.getTimeInMillis());

    }

    private static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

}
