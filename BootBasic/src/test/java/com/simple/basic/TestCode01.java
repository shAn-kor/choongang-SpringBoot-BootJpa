package com.simple.basic;

import com.simple.basic.command.BuilderVO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCode01 {
    /*@Test
    public void testCode01() {
        System.out.println("test code running");
    }

    @Test
    @Order(0)
    public void testCode02() {
        System.out.println("test code2 running");
    }*/

    @Test
    public void testCode03() {
        BuilderVO vo = BuilderVO.builder().name("홍길동").age(10).build();
        System.out.println(vo.toString());
    }

}
