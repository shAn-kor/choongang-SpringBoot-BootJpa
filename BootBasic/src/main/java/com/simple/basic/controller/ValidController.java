package com.simple.basic.controller;

import com.simple.basic.command.ValidVO;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.io.PrintWriter;

@Controller
@RequestMapping("/valid")
public class ValidController {
    @GetMapping("/view")
    public String view() {
        return "valid/view";
    }

    @PostMapping("/actionForm")
    public String actionForm(@Valid ValidVO vo, BindingResult bindingResult) {
        // valid 는 유효성 검사 한다는 뜻
        // 만약 유효성 검사에 통과 못하면 통과 못한 내역이 BindingResult 에 저장된다.

        bindingResult.getFieldErrors().forEach(err -> System.out.println(err.getField() + "\n" + err.getDefaultMessage()));

        if (bindingResult.hasErrors()) {
            System.out.println("유효성 검사 실패");

            return "redirect:/valid/view";
        }

        return "valid/result";
    }
}
