package com.example.demo.controller;

import com.example.demo.entity.Todo;
import com.example.demo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final TodoService todoService;

    /**
     * "GET /" 처리. 첫 페이지 반환.
     *
     * @param model 스프링 Model 객체. 페이지에 데이터 전달.
     * @return "home" 반환할 HTML 파일 이름
     */
    @GetMapping("/")
    public String home(Model model) {
        List<Todo> todos = todoService.findTodos();
        model.addAttribute("todos", todos);
        return "home";
    }
}
