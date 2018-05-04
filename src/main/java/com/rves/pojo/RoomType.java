package com.rves.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    public RoomType() {
    }

    public RoomType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RoomType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
