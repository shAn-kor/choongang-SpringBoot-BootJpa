package com.simple.basic.interceptor;

import com.simple.basic.command.UserVO;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 인터셉터를 설정파일에 bean으로 등록
public class UserAuthHandler implements HandlerInterceptor {
    // 컨트롤러 이전 동작
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("controller prev");

        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("/login");
            return false; // 컨트롤러 동작시키지 않음
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    // 컨트롤러 이후 동작
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("controller after");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
