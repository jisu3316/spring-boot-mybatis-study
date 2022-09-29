package com.example.demo.dao;

import com.example.demo.model.Todo;
import com.example.demo.response.TodoResponse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Mapper
public interface TodoDAO {
    List<Todo> list();

    void modify(Long todoId);

    Optional<Todo> selectOne(Long todoId);

    Integer count();
}
