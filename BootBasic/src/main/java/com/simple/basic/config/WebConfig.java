package com.simple.basic.config;

import com.simple.basic.command.TestVO;
import com.simple.basic.interceptor.UserAuthHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@PropertySource("classpath:/hello.properties") // 특정 properties 파일을 참조받기
public class WebConfig implements WebMvcConfigurer {
    // userAuthHandler 자바 빈으로 등록
    @Bean
    public UserAuthHandler userAuthHandler() {
        return new UserAuthHandler();
    }

    // 인터셉터로 userAuthHandler를 등록 (ctrl + i)

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userAuthHandler()).addPathPatterns("/user/*")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/loginForm")
                .excludePathPatterns("/user/logout");
    }

    /*@Value("${server.port}") // properties 에 있는 키를 찾아 값 받아옴
    String port;

    @Value("${hello}")
    String hello;

    @Value("${bye}")
    String bye;

    @Autowired
    ApplicationContext applicationContext;

    // 자바코드로 빈생성
    @Bean
    public TestVO testVO() {
        return new TestVO(); // bean으로 등록
    }

    @Bean // 스프링이 이 코드를 실행시켜, 리턴에 담기는 값을 bean으로 등록
    public void test() {
//        System.out.println("스프링 설정파일 실행됨");
//        System.out.println(applicationContext.getBean("homeController"));
//        System.out.println(applicationContext.getBeanDefinitionCount());

        System.out.println(applicationContext.getBean(TestVO.class));

        System.out.println(port);
        System.out.println(hello);
        System.out.println(bye);
    }*/
}
