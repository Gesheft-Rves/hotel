package com.rves.Dto;

import com.rves.pojo.Room;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

public class BookingDto {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Timestamp getDate_buking() {
        return date_buking;
    }

    public void setDate_buking(Timestamp date_buking) {
        this.date_buking = date_buking;
    }

    public Date getArrival_date() {
        return arrival_date;
    }

    public void setArrival_date(Date arrival_date) {
        this.arrival_date = arrival_date;
    }

    public Date getDate_of_departure() {
        return date_of_departure;
    }

    public void setDate_of_departure(Date date_of_departure) {
        this.date_of_departure = date_of_departure;
    }
}
