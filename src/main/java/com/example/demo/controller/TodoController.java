package com.example.demo.controller;

import com.example.demo.service.TodoService;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.dto.TodoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {
    @Autowired
    private TodoService service;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo() {
        String str=service.testService();
        List<String> list=new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response=ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
        try {
            // test
            String temporaryUserId="temporary-user";

            // TodoDTO -> TodoEntity
            TodoEntity entity=TodoDTO.toEntity(dto);

            // entity 생성 시 id는 null값
            entity.setId(null);

            // 임시 userId
            entity.setUserId(temporaryUserId);

            // 서비스 레이어 create()-서비스 계층에 저장
            List<TodoEntity> entities=service.create(entity);

            List<TodoDTO> dtos=entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            ResponseDTO<TodoDTO> response=ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);

        } catch(Exception e) {
            String error=e.getMessage();
            ResponseDTO<TodoDTO> response=ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList() {
        String temporaryUserId="temporary-user";

        List<TodoEntity> entities=service.retrieve(temporaryUserId);

        List<TodoDTO> dtos=entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        ResponseDTO<TodoDTO> response=ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }
}
