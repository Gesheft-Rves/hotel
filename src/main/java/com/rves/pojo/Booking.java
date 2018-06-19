package com.rves.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private Timestamp dateBuking;

    @OneToOne
    @JoinColumn
    private Room room;

    @Column
    private Timestamp arrivalDate;

    @Column
    private Timestamp dateOfDeparture;

    @Column
    private Timestamp lastDayCleaning;

    @OneToOne
    @JoinColumn
    private User user;

    @Column
    private boolean canceled;

    public Booking() {
    }
}
