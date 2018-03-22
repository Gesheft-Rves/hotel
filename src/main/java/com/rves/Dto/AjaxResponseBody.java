package com.rves.Dto;

import com.rves.pojo.Booking;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AjaxResponseBody {
    private String msg;
    private List<Booking> bookings;
}
