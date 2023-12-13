package com.example.demo.service;

import com.example.demo.entity.Todo;
import com.example.demo.repository.TodoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TodoServiceTest {

    @Autowired TodoService todoService;
    @Autowired TodoRepository todoRepository;

    @Test
    void createTodoTest() {
        // given
        Todo todo = new Todo();
        todo.setTitle("밥먹기");
        Todo savedTodo = todoService.createTodo(todo);
        Long savedId = savedTodo.getId();

        // when
        Todo foundTodo = todoRepository.findById(savedId).get();

        // then
        Assertions.assertThat(foundTodo.getId()).isEqualTo(savedId);
        Assertions.assertThat(foundTodo.getTitle()).isEqualTo(savedTodo.getTitle());
    }

    @Test
    void createTodoDuplicateTest() {
        // given
        Todo todo = new Todo();
        todo.setTitle("밥먹기");
        todoService.createTodo(todo);

        // when
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> todoService.createTodo(todo));

        // then
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 할 일입니다.");
    }

    @Test
    void findTodosTest() {
        // given
        Todo todo1 = new Todo();
        todo1.setTitle("밥먹기");
        todoService.createTodo(todo1);
        Todo todo2 = new Todo();
        todo2.setTitle("영화보기");
        todoService.createTodo(todo2);

        // when
        List<Todo> foundTodos = todoService.findTodos();

        // then
        Assertions.assertThat(foundTodos.size()).isEqualTo(2);
    }

    @Test
    void findTodosEmptyTest() {
        // given

        // when
        List<Todo> foundTodos = todoService.findTodos();

        // then
        Assertions.assertThat(foundTodos.size()).isEqualTo(0);
    }

    @Test
    void deleteTodoTest() {
        // given
        Todo todo = new Todo();
        todo.setTitle("밥먹기");
        Todo savedTodo = todoService.createTodo(todo);
        Long savedTodoId = savedTodo.getId();

        // when
        todoService.deleteTodo(savedTodoId);
        Optional<Todo> foundTodo = todoRepository.findById(savedTodoId);

        // then
        Assertions.assertThat(foundTodo).isEqualTo(Optional.empty());
    }
}