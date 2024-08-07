package com.simple.basic.controller;

import com.simple.basic.command.TestVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/view")
public class ThymeleafController {
    @GetMapping("/ex01")
    public String ex01() {
        return "view/ex01";
    }

    @GetMapping("/ex02")
    public String ex02(Model model) {
        List<TestVO> list = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            TestVO vo = TestVO.builder().age(20 + i).name("hong" + i).num(i).addr("Seoul" + i).build();
            list.add(vo);
        }

        model.addAttribute("list", list);

        return "view/ex02";
    }

    @GetMapping("/ex03")
    public String ex03(Model model) {
        List<TestVO> list = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            TestVO vo = TestVO.builder().age(20 + i).name("hong" + i).num(i).addr("Seoul" + i).build();
            list.add(vo);
        }

        model.addAttribute("list", list);

        return "view/ex03";
    }

    @GetMapping("/ex03_result")
    public String ex03_result(@ModelAttribute("name") String name) {

        return "view/ex03_result";
    }

    @GetMapping("/ex03_result2/{name}/{age}")
    public String ex03_result2(
            @PathVariable("name") String name,
            @PathVariable("age") int age
    ) {

        System.out.println(name + " - " + age);
        return "view/ex03_result";
    }

    @GetMapping("/ex04")
    public String ex04(Model model) {
        TestVO vo = TestVO.builder().age(20).name("hong").num(20).addr("seoul").build();

        Integer[] arr = new Integer[]{1, 2, 3};

        model.addAttribute("name", "hong");
        model.addAttribute("arr", arr);
        model.addAttribute("list", Arrays.asList(1, 2, 3, 4));
        model.addAttribute("vo", vo);
        model.addAttribute("now", new Date());

        return "view/ex04";
    }

    @GetMapping("/ex05")
    public String ex05(Model model) {

        return "view/ex05";
    }

    @GetMapping("/ex06")
    public String ex06() {

        return "view/ex06";
    }

    @GetMapping("/quiz01")
    public String quiz01(Model model) {
        TestVO vo = TestVO.builder().num(20).name("lee").age(20).build();
        model.addAttribute("vo", vo);

        return "view/quiz01";
    }

    @GetMapping("/quiz01_result/{num}")
    public String quiz01_result(@PathVariable("num") int num) {
        System.out.println(num);
        return "view/quiz01_result";
    }

}
