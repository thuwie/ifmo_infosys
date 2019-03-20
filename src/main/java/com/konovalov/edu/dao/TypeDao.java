package com.konovalov.edu.dao;

import com.konovalov.edu.entity.Type;

import java.util.List;

public interface TypeDao {

    List<Type> getAllTypes();

    Type getTypeById(int typeId);

    void addType(Type type);

    void updateType(Type type);

    void deleteType(int typeId);

    boolean isTypeExists(Type type);
}
