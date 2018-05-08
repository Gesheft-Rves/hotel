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

    @OneToOne
    @JoinColumn
    private RoomType type;

    @Column
    private boolean cleaningRequired;

    public Room() {
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
