package com.rves.services;


import com.rves.pojo.Room;
import com.rves.repositories.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomsService implements PojoService<Room> {

    private RoomsRepository roomsRepository;

    @Autowired
    public void setRoomsRepository(RoomsRepository roomsRepository) {
        this.roomsRepository = roomsRepository;
    }

    @Override
    public List<Room> list() {
        return roomsRepository.findAll();
    }

    @Override
    public Room getById(Integer id) {
        return roomsRepository.getOne(id);
    }

    @Override
    public Room save(Room obj) {
        return roomsRepository.save(obj);
    }

    @Override
    public void delete(Integer id) {
        roomsRepository.delete(id);
    }

    // список грязных комнат
    public List<Room> unclearedRooms(){
        List<Room> rooms = list();
        List<Room> dirtyRooms = new ArrayList<>();

        for (Room room : rooms) {
            if (room.isCleaningRequired()){
                dirtyRooms.add(room);
            }
        }
        return dirtyRooms;
    }
}
