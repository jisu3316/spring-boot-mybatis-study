package com.example.demo.service;

import com.example.demo.dao.TodoDAO;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.SnsApplicationException;
import com.example.demo.model.Todo;
import com.example.demo.response.TodoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoDAO todoDAO;

    public List<TodoResponse> getTodo() {
        List<TodoResponse> list = todoDAO.list().stream().map(TodoResponse::from).collect(Collectors.toList());
       
        return list;
    }
    public Integer totalCount() {

        Integer count = (int)Math.ceil((double)todoDAO.count()/10);
        return count;
    }
    public Todo modify(Long todoId) {
        return todoDAO.selectOne(todoId).orElseThrow(() -> new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not found", todoId)));
//        todoDAO.modify(todoId);
    }
}
