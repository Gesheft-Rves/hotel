package com.rves.services;

import com.rves.pojo.TypeRoom;
import com.rves.repositories.TypeRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeRoomService implements PojoService<TypeRoom> {
    private TypeRoomRepository repository;

    @Autowired
    public void setRepository(TypeRoomRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<TypeRoom> list() {
        return repository.findAll();
    }

    @Override
    public TypeRoom getById(Integer id) {
        return repository.getOne(id);
    }

    @Override
    public TypeRoom save(TypeRoom obj) {
        return repository.save(obj);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }
}
