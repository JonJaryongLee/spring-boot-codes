package com.example.demo.controller;

import com.example.demo.entity.Todo;
import com.example.demo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    /**
     * "POST /todos/delete/{todoId}" 처리. id 에 해당하는 값 삭제
     *
     * @param todoId 삭제할 할 일의 ID
     * @return "redirect:/" 삭제 후 "/" 로 리다이렉트
     */
    @PostMapping("/todos/delete/{todoId}")
    public String delete(@PathVariable(name="todoId") Long todoId) {
        todoService.deleteTodo(todoId);
        return "redirect:/";
    }

    /**
     * "GET /todos/new" 처리. 입력 폼 보여줌
     *
     * @return "todos/createTodoForm" Todo 생성 폼
     */
    @GetMapping("/todos/new")
    public String createForm(Model model) {
        model.addAttribute("todoForm", new TodoForm());
        return "todos/createTodoForm";
    }

    /**
     * "POST /todos/new" 처리. Todo 생성
     *
     * @param todoForm {@link Valid} 어노테이션을 사용하여 유효성 검사가 수행된 todoForm
     * @param result {@link TodoForm} 유효성 검사 결과
     * @return 만약 에러가 있으면 에러 메시지를 표시, 없으면 "/" 으로 리다이렉트
     */
    @PostMapping("/todos/new")
    public String create(@Valid TodoForm todoForm, BindingResult result) {
        if (result.hasErrors()) {
            return "todos/createTodoForm";
        }
        Todo todo = new Todo();
        todo.setTitle(todoForm.getTitle());
        todoService.createTodo(todo);
        return "redirect:/";
    }
}
