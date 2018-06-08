package com.rves.Dto;

import com.rves.pojo.Room;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AjaxRoomsResponseBody {
    private String msg;
    private List<Room> rooms;
}

