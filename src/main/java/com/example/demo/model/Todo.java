package com.example.demo.model;

import lombok.*;

@Data
public class Todo {

    private Long id;

    private boolean completed;

    private String todoName;

    @Builder
    public Todo(Long id, boolean completed, String todoName) {
        this.id = id;
        this.completed = completed;
        this.todoName = todoName;
    }
}
