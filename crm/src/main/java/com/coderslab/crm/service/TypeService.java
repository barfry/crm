package com.coderslab.crm.service;

import com.coderslab.crm.model.Type;
import com.coderslab.crm.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {

    @Autowired
    TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<Type> getAllTypes(){
        return typeRepository.findAll();
    }

    public Type addNewType(Type type){
        return typeRepository.save(type);
    }

    public Type getTypeById(Long typeId){
        return typeRepository.getById(typeId);
    }

    public Type editType(Type type){
        return typeRepository.save(type);
    }
}
