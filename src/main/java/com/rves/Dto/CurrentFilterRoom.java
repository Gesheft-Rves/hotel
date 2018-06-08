package com.rves.Dto;

import com.rves.bootstrap.ApplicationContextHolder;
import com.rves.services.RoomTypeService;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Getter
@Setter
public class CurrentFilterRoom {
    private Timestamp dateFromArrivalFilter;
    private Timestamp dateFromDepartureFilter;
    private Integer roomTypeFilter;

    private String dateFromArrivalFilterStr;
    private String dateFromDepartureFilterStr;
    private String roomTypeFilterStr;

    private RoomTypeService roomTypeService;

    public CurrentFilterRoom() {
    }

    public void parse(){
        if (roomTypeService == null) {
            roomTypeService = ApplicationContextHolder.getContext().getBean(RoomTypeService.class);
        }

        dateFromArrivalFilter = "".equals(dateFromArrivalFilterStr)
                ? null
                : parseDate(dateFromArrivalFilterStr);

        dateFromDepartureFilter = "".equals(dateFromDepartureFilterStr)
                ? null
                : parseDate(dateFromDepartureFilterStr);

        roomTypeFilter = "".equals(roomTypeFilterStr)
                ? null
                : Integer.parseInt(roomTypeFilterStr);

    }

    public boolean filterIsEmpty(){
        return  dateFromArrivalFilter == null
                && dateFromDepartureFilter == null
                && roomTypeFilter == null;
    }

    private Timestamp parseDate(String strTimestampDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            return new Timestamp(dateFormat.parse(strTimestampDate.replace("T"," ")).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}