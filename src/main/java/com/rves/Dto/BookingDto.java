package com.rves.Dto;

import com.rves.Utils;
import com.rves.pojo.Room;
import com.rves.pojo.RoomType;
import com.rves.pojo.User;
import com.rves.services.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
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

    @NotNull
    private Timestamp date_buking;
    private String date_buking_str;

    @NotNull
    private Room room;

    @NotNull
    private Timestamp arrival_date;//arrival_date
    private String arrival_date_str;//arrival_date

    @NotNull
    private Timestamp date_of_departure; //date_of_departure
    private String date_of_departure_str; //date_of_departure

    private RoomType roomType; // setRoomType

    private User user;

    private boolean canceled;

    public Timestamp getDate_buking() {
        java.util.Date date = new java.util.Date();
        return new Timestamp(date.getTime());
    }

    public void parseDates() {
        this.arrival_date = Utils.parseDateFromStringOrNow(arrival_date_str);
        this.date_of_departure = Utils.parseDateFromStringOrNow(date_of_departure_str);
        this.date_buking = Utils.parseDateFromStringOrNow(date_buking_str);
    }

}
