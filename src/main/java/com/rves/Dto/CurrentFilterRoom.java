package com.rves.Dto;

import com.rves.Utils;
import com.rves.bootstrap.ApplicationContextHolder;
import com.rves.services.RoomTypeService;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

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
                : Utils.parseDateFromStringOrNow(dateFromArrivalFilterStr);

        dateFromDepartureFilter = "".equals(dateFromDepartureFilterStr)
                ? null
                : Utils.parseDateFromStringOrNow(dateFromDepartureFilterStr);

        roomTypeFilter = "".equals(roomTypeFilterStr)
                ? null
                : Integer.parseInt(roomTypeFilterStr);

    }

    public boolean filterIsEmpty(){
        return  dateFromArrivalFilter == null
                && dateFromDepartureFilter == null
                && roomTypeFilter == null;
    }


}