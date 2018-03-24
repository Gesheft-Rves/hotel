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
    private Date dateFromArrivalFilter;
    private Date dateFromDepartureFilter;
    private Date dateToArrivalFilter;
    private Date dateToDepartureFilter;

    private String dateFromFilterStr;
    private String dateToFilterStr;
    private String roomFilterStr;
    private String dateFromArrivalFilterStr;
    private String dateFromDepartureFilterStr;
    private String dateToArrivalFilterStr;
    private String dateToDepartureFilterStr;

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

        dateFromArrivalFilter = "".equals(dateFromArrivalFilterStr)
                ? null
                : parseDate(dateFromArrivalFilterStr);

        dateFromDepartureFilter = "".equals(dateFromDepartureFilterStr)
                ? null
                : parseDate(dateFromDepartureFilterStr);

        dateToArrivalFilter = "".equals(dateToArrivalFilterStr)
                ? null
                : parseDate(dateToArrivalFilterStr);

        dateToDepartureFilter = "".equals(dateToDepartureFilterStr)
                ? null
                : parseDate(dateToDepartureFilterStr);

    }

    public boolean filterIsEmpty(){
        return dateFromFilter == null
                && dateToFilter   == null
                && roomFilter   == null
                && dateFromArrivalFilter == null
                && dateFromDepartureFilter == null
                && dateToArrivalFilter == null
                && dateToDepartureFilter == null;
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
