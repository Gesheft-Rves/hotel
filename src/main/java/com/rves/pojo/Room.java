package com.rves.pojo;

import javax.persistence.*;

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

    private boolean cleaningRequired;

    public Room() {
    }

    public Room(Integer no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", no=" + no +
                ", type=" + type +
                '}';
    }

    public boolean isCleaning_required() {
        return cleaningRequired;
    }

    public void setCleaning_required(boolean cleaningRequired) {
        this.cleaningRequired = cleaningRequired;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

}
