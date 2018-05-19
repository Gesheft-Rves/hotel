package com.rves.Dto;

import com.rves.pojo.Room;
import com.rves.pojo.RoomType;
import com.rves.pojo.User;
import com.rves.services.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
public class BookingDto {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @NotNull
    private Integer id;

    @FutureOrPresent
    @NotNull
    private Timestamp date_buking;

    @NotNull
    private Room room;

    @FutureOrPresent
    @NotNull
    private Date arrival_date;

    @Future
    @NotNull
    private Date date_of_departure;

    private RoomType roomType;

    private User user;

    private boolean canceled;

    public Timestamp getDate_buking() {
        java.util.Date date = new java.util.Date();
        return new Timestamp(date.getTime());
    }
}
