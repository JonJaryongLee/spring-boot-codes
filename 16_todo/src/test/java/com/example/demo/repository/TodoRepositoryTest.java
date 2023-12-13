package com.example.demo.repository;

import com.example.demo.entity.Todo;
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
class TodoRepositoryTest {

    @Autowired TodoRepository todoRepository;

    @Test
    void findByIdTest() {
        // given
        Todo todo = new Todo();
        todo.setTitle("밥먹기");
        Todo savedTodo = todoRepository.save(todo);

        // when
        Todo foundTodo = todoRepository.findById(savedTodo.getId()).get();

        // then
        Assertions.assertThat(foundTodo.getId()).isEqualTo(savedTodo.getId());
        Assertions.assertThat(foundTodo.getTitle()).isEqualTo(savedTodo.getTitle());
        // Assertions.assertThat(foundTodo.getTitle()).isEqualTo("영화보기");
        // TestTransaction.flagForCommit();
    }

    @Test
    void findByIdNotFoundTest() {
        // given

        // when
        Optional<Todo> foundTodo = todoRepository.findById(99L);

        // then
        Assertions.assertThat(foundTodo).isEqualTo(Optional.empty());
    }

    @Test
    void findAllTest() {
        // given
        Todo todo1 = new Todo();
        todo1.setTitle("밥먹기");
        todoRepository.save(todo1);

        Todo todo2 = new Todo();
        todo2.setTitle("밥먹기");
        todoRepository.save(todo2);

        // when
        List<Todo> todos = todoRepository.findAll();

        // then
        Assertions.assertThat(todos.size()).isEqualTo(2);
    }

    @Test
    void findAllEmptyTest() {
        // given

        // when
        List<Todo> todos = todoRepository.findAll();

        // then
        Assertions.assertThat(todos.size()).isEqualTo(0);
    }

    @Test
    void findByTitleTest() {
        // given
        Todo todo = new Todo();
        todo.setTitle("밥먹기");
        todoRepository.save(todo);

        // when
        Todo foundTodo = todoRepository.findByTitle("밥먹기").get();

        // then
        Assertions.assertThat(todo.getId()).isEqualTo(foundTodo.getId());
        Assertions.assertThat(todo.getTitle()).isEqualTo(foundTodo.getTitle());
    }

    @Test
    void findByTitleNotFoundTest() {
        // given

        // when
        Optional<Todo> foundTodo = todoRepository.findByTitle("밥먹기");

        // then
        Assertions.assertThat(foundTodo).isEqualTo(Optional.empty());
    }

    @Test
    void saveForUpdateTest() {
        // given
        Todo todo = new Todo();
        todo.setTitle("밥먹기");
        Todo savedTodo = todoRepository.save(todo);

        // when
        savedTodo.setTitle("영화보기");
        Todo updatedTodo = todoRepository.save(savedTodo);
        Todo foundTodo = todoRepository.findById(updatedTodo.getId()).get();

        // then
        Assertions.assertThat(foundTodo.getId()).isEqualTo(updatedTodo.getId());
        Assertions.assertThat(foundTodo.getTitle()).isEqualTo(updatedTodo.getTitle());
    }

    @Test
    void deleteByIdTest() {
        // given
        Todo todo = new Todo();
        todo.setTitle("밥먹기");
        Todo savedTodo = todoRepository.save(todo);
        Long savedTodoId = savedTodo.getId();

        // when
        todoRepository.deleteById(savedTodoId);
        Optional<Todo> foundTodo = todoRepository.findById(savedTodoId);

        // then
        Assertions.assertThat(foundTodo).isEqualTo(Optional.empty());
    }
}