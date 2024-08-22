package com.example.jpa;

import com.example.jpa.repository.MemoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JPAQueryDslTest06 {
    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testCode01() {
        System.out.println(memoRepository.dslSelect());
    }

    @Test
    public void testCode02() {
        memoRepository.dslSelect2().forEach(System.out::println);
    }

    @Test
    public void testCode03() {
        memoRepository.dslSelect3("writer", "te").forEach(System.out::println);
        memoRepository.dslSelect3("text", "te").forEach(System.out::println);
    }

    @Test
    public void testCode04() {
        memoRepository.dslJoin().forEach(System.out::println);
    }
}
