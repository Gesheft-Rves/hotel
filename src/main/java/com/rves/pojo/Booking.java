package com.rves.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date_buking")
    private Timestamp date_buking;

    @OneToOne
    @JoinColumn
    private Rooms room;

    @Column(name = "arrival_date")
    private Date arrival_date;


    @Column(name = "date_of_departure")
    private Date date_of_departure;

    public Booking() {
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", date_buking=" + date_buking +
                ", rooms=" + room +
                ", arrival_date=" + arrival_date +
                ", date_of_departure=" + date_of_departure +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public Rooms getRoom() {
        return room;
    }

    public void setRoom(Rooms room) {
        this.room = room;
    }

    public void setId(Integer id) {
        this.id = id;
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
