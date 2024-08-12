package com.simple.basic.controller;

import com.simple.basic.command.MemoVO;
import com.simple.basic.memo.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/memo")
public class MemoController {
    @Autowired
    MemoService memoService;

    @GetMapping("/memoWrite")
    public String memoWrite(Model model) {
        model.addAttribute("memoVO", new MemoVO());
        return "memo/memoWrite";
    }

    @PostMapping("/memoForm")
    public String memoForm(@Valid MemoVO memoVO, BindingResult bResult) {
        System.out.println(memoVO.toString());

        if (bResult.hasErrors()) {
            return "memo/memoWrite";
        }
        memoService.inputMemo(memoVO);

        return "redirect:/memo/memoList";
    }

    @RequestMapping("/memoList")
    public String memoList(Model model) {
        model.addAttribute("list", memoService.getMemos());

        return "memo/memoList";
    }
}
