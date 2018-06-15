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
    private Timestamp dateBuking;
    private String dateBukingStr;
    @NotNull
    private Room room;

    @NotNull
    private Timestamp arrivalDate;
    private String arrivalDateStr;

    @NotNull
    private Timestamp dateOfDeparture;
    private String dateOfDepartureStr;

    private RoomType roomType;

    private User user;

    private boolean canceled;

    public Timestamp getDateBuking() {
        java.util.Date date = new java.util.Date();
        return new Timestamp(date.getTime());
    }

    public void parseDates() {
        this.arrivalDate = Utils.parseDateFromStringOrNow(arrivalDateStr);
        this.dateOfDeparture = Utils.parseDateFromStringOrNow(dateOfDepartureStr);
        this.dateBuking = Utils.parseDateFromStringOrNow(dateBukingStr);
    }

}
