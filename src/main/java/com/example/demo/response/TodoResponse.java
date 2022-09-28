package com.example.demo.response;

import com.example.demo.model.Todo;
import lombok.Data;

@Data
public class TodoResponse {

    private Long id;

    private boolean completed;

    private String todoName;

    public TodoResponse(Long id, boolean completed, String todoName) {
        this.id = id;
        this.completed = completed;
        this.todoName = todoName;
    }

    public static TodoResponse from(Todo todo) {
        return new TodoResponse(todo.getId(), todo.isCompleted(), todo.getTodoName());
    }
}
