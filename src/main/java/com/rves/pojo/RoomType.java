package com.rves.pojo;

public enum RoomType {
    STANDART_SINGLE(1, "Станндартный 1-но местный"),
    STANDART_DOUBLE(2, "Станндартный 2-х местный"),
    FAMILY(3, "Семейный"),
    JUNIOR_SUITE(4, "Полулюкс"),
    LUX(5, "Люкс");

    private int db_id;
    private String description;

    RoomType(int db_id, String description) {
        this.db_id = db_id;
        this.description = description;
    }
}
