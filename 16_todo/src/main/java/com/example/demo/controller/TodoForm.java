package com.example.demo.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TodoForm {

    @NotEmpty(message = "할 일 이름은 필수입니다.")
    private String title;
}
