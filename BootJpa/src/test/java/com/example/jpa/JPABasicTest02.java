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

import java.util.List;

@SpringBootTest
public class JPABasicTest02 {
    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testCode01() {
//        Sort sort = Sort.by("mno").descending();
        Sort sort1 = Sort.by("writer").descending();
        Sort sort2 = Sort.by("text").ascending();
        Sort sort = sort1.and(sort2);

        List<Memo> list = memoRepository.findAll(sort);

        list.forEach(System.out::println);
    }

    // 페이지 클래스
    @Test
    public void testCode02() {
//        Pageable pageable = PageRequest.of(0, 10);// offset (페이지번호), count (데이터 개수)
//        Pageable pageable = PageRequest.of(1, 10);// offset (페이지번호), count (데이터 개수)
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());// offset (페이지번호), count (데이터 개수)
        Page<Memo> page = memoRepository.findAll(pageable);
        // 페이지 타입 결과 안에는 데이터, 페이지 정보 있다.
        page.forEach(System.out::println);

        List<Memo> list = page.getContent(); // 조회된 데이터
        long totalElements = page.getTotalElements();// 전체 데이터 수
        int totalPage = page.getTotalPages(); // 전체 페이지 수
        int current = page.getNumber(); // 현재 페이지
        boolean prev = page.hasPrevious();// 이전 페이지 여부
        boolean next = page.hasNext(); // 다음 페이지 여부
        boolean last = page.isLast();// 현재 페이지가 마지막인지 여부
        boolean first = page.isFirst();// 현재 페이지가 처음인지 여부

        System.out.println(list);
        System.out.println(totalElements);
        System.out.println(totalPage);
        System.out.println(current);
        System.out.println(prev);
        System.out.println(next);
        System.out.println(last);
        System.out.println(first);
    }
}
