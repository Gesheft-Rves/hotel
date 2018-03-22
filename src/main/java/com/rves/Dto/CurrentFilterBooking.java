package com.rves.Dto;

import com.rves.bootstrap.ApplicationContextHolder;
import com.rves.pojo.Booking;
import com.rves.services.BookingService;
import com.rves.services.RoomsService;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Getter
@Setter
public class CurrentFilterBooking {
    private Booking bookingFilter;
    private Date dateFromFilter;
    private Date dateToFilter;
    private Integer roomFilter;
    private Date dateArrivalFilter;
    private Date dateDepartureFilter;
    private Date dateArrivalFilter2;
    private Date dateDepartureFilter2;

    private String dateFromFilterStr;
    private String dateToFilterStr;
    private String roomFilterStr;
    private String dateArrivalFilterStr;
    private String dateDepartureFilterStr;
    private String dateArrivalFilterStr2;
    private String dateDepartureFilterStr2;

    private BookingService bookingService;
    private RoomsService roomsService;

    public CurrentFilterBooking() {
    }

    public void parse(){
        if (bookingService == null) {
            bookingService =  ApplicationContextHolder.getContext().getBean(BookingService.class);
        }
        if (roomsService == null) {
            roomsService = ApplicationContextHolder.getContext().getBean(RoomsService.class);
        }
        dateFromFilter = "".equals(dateFromFilterStr)
                ? null
                : parseDate(dateFromFilterStr);

        dateToFilter = "".equals(dateToFilterStr)
                ? null
                : parseDate(dateToFilterStr);

        roomFilter = "".equals(roomFilterStr)
                ? null
                : Integer.parseInt(roomFilterStr);

        dateArrivalFilter = "".equals(dateArrivalFilterStr)
                ? null
                : parseDate(dateArrivalFilterStr);

        dateDepartureFilter = "".equals(dateDepartureFilterStr)
                ? null
                : parseDate(dateDepartureFilterStr);

        dateArrivalFilter2 = "".equals(dateArrivalFilterStr2)
                ? null
                : parseDate(dateArrivalFilterStr2);

        dateDepartureFilter2 = "".equals(dateDepartureFilterStr2)
                ? null
                : parseDate(dateDepartureFilterStr2);

    }

    public boolean filterIsEmpty(){
        return dateFromFilter == null
                && dateToFilter   == null
                && roomFilter   == null
                && dateArrivalFilter   == null
                && dateDepartureFilter   == null
                && dateArrivalFilter2   == null
                && dateDepartureFilter2   == null;
    }

    private Date parseDate(String strDate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsed = null;
        try {
            parsed = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new java.sql.Date(parsed.getTime());
    }
}
