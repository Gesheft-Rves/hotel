package com.rves.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "no")
    private Integer no;

    @Column(name = "type")
    private Integer type;

    public Room() {
    }

    public Room(Integer no, Integer type) {
        this.no = no;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", no=" + no +
                ", type=" + type +
                '}';
    }
}
