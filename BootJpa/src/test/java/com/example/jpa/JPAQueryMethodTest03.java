package com.example.jpa;

import com.example.jpa.entity.Memo;
import com.example.jpa.repository.MemoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class JPAQueryMethodTest03 {
    // 쿼리메서드 JPA 인터페이스 메서드 모형 보고 SQL을 자동 생성

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testCode01() {
        Memo memo = memoRepository.findByWriterAndText("test20", "test20");
        System.out.println(memo);
        List<Memo> memos = memoRepository.findByMnoLessThan(20L);
        memos.forEach(System.out::println);

        List<Memo> between = memoRepository.findByMnoBetween(10L, 20L);
        between.forEach(System.out::println);

        List<Memo> like = memoRepository.findByWriterLike("%10%");
        like.forEach(System.out::println);

        List<Memo> desc = memoRepository.findByWriterOrderByWriterDesc("test1");
        desc.forEach(System.out::println);

        List<Memo> in = memoRepository.findByMnoIn(Arrays.asList(10L, 20L, 30L, 40L, 50L));
        in.forEach(System.out::println);


    }

    // 쿼리메서드 마지막 매개변수에 Pageable 주면, 페이징 처리 된다.
    @Test
    public void testCode02() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Memo> page = memoRepository.findByMnoLessThanEqual(50L, pageable);
        page.forEach(System.out::println);
    }
}
