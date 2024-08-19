package com.simple.basic.controller;

import com.simple.basic.command.UserVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class SessionController {
    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @PostMapping("/loginForm")
    public String loginForm(UserVO userVO, HttpSession session) {
        UserVO result = userVO;

        if (result == null) { // 로그인 실패
            return "redirect:/user/login";
        }
        session.setAttribute("user", result);
        return "redirect:/user/success";
    }

    @GetMapping("/mypage")
    public String mypage() {
        return "user/mypage";
    }

    @GetMapping("/success")
    public String success() {
        return "user/success";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/user/login";
    }
}
