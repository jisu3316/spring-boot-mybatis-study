package com.example.demo.dao;

import com.example.demo.model.Todo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TodoDAO {
    public List<Todo> list();
}
