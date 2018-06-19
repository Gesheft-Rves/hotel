package com.rves.Dto;

import com.rves.pojo.User;
import lombok.Getter;
import java.sql.Timestamp;

@Getter
public class ReportDTO {
    private String dateFromRep;
    private Timestamp dateFrom;

    private String dateToRep;
    private Timestamp dateTo;

    private Boolean canceledBooking;
    private User user;
}
