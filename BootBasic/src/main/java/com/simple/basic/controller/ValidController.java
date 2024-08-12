package com.simple.basic.controller;

import com.simple.basic.command.ValidVO;
import com.simple.basic.command.MemberVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.io.PrintWriter;

@Controller
@RequestMapping("/valid")
public class ValidController {
    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("vo", new ValidVO());
        return "valid/view";
    }

    @PostMapping("/actionForm")
    public String actionForm(@Valid @ModelAttribute("vo") ValidVO vo, BindingResult bindingResult) {
        // valid 는 유효성 검사 한다는 뜻
        // 만약 유효성 검사에 통과 못하면 통과 못한 내역이 BindingResult 에 저장된다.

        bindingResult.getFieldErrors().forEach(err -> System.out.println(err.getField() + "\n" + err.getDefaultMessage()));

        if (bindingResult.hasErrors()) {
            System.out.println("유효성 검사 실패");

            return "valid/view";
        }

        return "valid/result";
    }

    @GetMapping("/quiz01")
    public String quiz01(Model model) {
        model.addAttribute("memberVO", new MemberVO());
        return "valid/quiz01";
    }

    @PostMapping("/quizForm")
    public String quiz01_result(@Valid MemberVO memberVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("valid fail");
            return "valid/quiz01";
        }

        return "valid/quiz01_result";
    }
}
