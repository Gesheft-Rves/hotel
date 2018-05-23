package com.rves.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Report {
    private Date dateFrom;
    private Date dateTo;
    private Boolean allBookings;
    private Boolean canceledBooking;
    private User user;
}
