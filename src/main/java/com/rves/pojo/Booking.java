package com.rves.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date_buking")
    private Timestamp date_buking;

    @OneToOne
    @JoinColumn
    private Room room;

    @Column(name = "arrival_date")
    private Date arrival_date;

    @Column(name = "date_of_departure")
    private Date date_of_departure;

    @OneToOne
    @JoinColumn
    private User user;

    @Column
    private boolean canceled;

    public Booking() {
    }
}
