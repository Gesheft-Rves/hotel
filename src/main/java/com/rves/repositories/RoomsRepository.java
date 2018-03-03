package com.rves.repositories;

import com.rves.pojo.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomsRepository extends JpaRepository<Room, Integer> {
}
