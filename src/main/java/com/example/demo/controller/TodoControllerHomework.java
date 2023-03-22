package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.TodoDTO;

@RestController
@RequestMapping("todo")

public class TodoControllerHomework {
    @GetMapping("/testTodo")
    public ResponseEntity<?> testTodo() {

        TodoDTO response=TodoDTO.builder().id("123").title("김지혜").done(true).build();
        return ResponseEntity.ok().body(response);
    }
}

