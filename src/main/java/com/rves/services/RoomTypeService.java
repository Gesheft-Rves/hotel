package com.rves.services;

import com.rves.pojo.RoomType;
import com.rves.repositories.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomTypeService implements PojoService<RoomType> {
    private RoomTypeRepository repository;

    @Autowired
    public void setRepository(RoomTypeRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<RoomType> list() {
        return repository.findAll();
    }

    @Override
    public RoomType getById(Integer id) {
        return repository.getOne(id);
    }

    @Override
    public RoomType save(RoomType obj) {
        return repository.save(obj);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }
}
