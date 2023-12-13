package com.example.demo.service;

import com.example.demo.entity.Todo;
import com.example.demo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    /**
     * 새로운 할 일을 생성하고 저장하되,
     * 동일한 제목의 할 일 허용 안함
     *
     * @param todo 생성하고자 하는 Todo 객체. 이 객체의 제목은 유일해야 함
     * @return 데이터베이스에 저장된 Todo 객체이며, id 포함
     * @throws IllegalStateException 동일한 제목의 Todo가 이미 존재하는 경우
     */
    @Transactional
    public Todo createTodo(Todo todo) {
        Optional<Todo> foundTodo = todoRepository.findByTitle(todo.getTitle());
        validateTodoExist(foundTodo);
        Todo savedTodo = todoRepository.save(todo);
        return savedTodo;
    }

    private static void validateTodoExist(Optional<Todo> foundTodo) {
        foundTodo.ifPresent(t -> {
            throw new IllegalStateException("이미 존재하는 할 일입니다.");
        });
    }

    /**
     * 모든 할 일 조회
     *
     * @return Todo 객체의 리스트. 할 일이 없다면 빈 리스트 반환
     */
    public List<Todo> findTodos() {
        return todoRepository.findAll();
    }

    /**
     * ID 기준 할 일 삭제
     *
     * @param todoId 삭제하고자 하는 Todo의 ID
     */
    @Transactional
    public void deleteTodo(Long todoId) {
        todoRepository.deleteById(todoId);
    }
}

