package com.example.jpa;

import com.example.jpa.entity.Memo;
import com.example.jpa.repository.MemoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@SpringBootTest
public class JPAJpqlTest04 {
    @Autowired
    MemoRepository MemoRepository;
    @Autowired
    private MemoRepository memoRepository;

    @Test
    public void testCode01() {
        List<Memo> list = memoRepository.getListDesc();
        list.forEach(System.out::println);
    }

    @Test
    public void testCode02() {
        List<Memo> list = memoRepository.getListDesc2(50L);
        list.forEach(System.out::println);
    }

    @Test
    public void testCode03() {
        List<Object[]> list = memoRepository.getListDesc3(50L);
        list.forEach(arr -> System.out.println(arr[0] + "\n" + arr[1]));
    }

    @Test
    public void testCode04() {
        int num = memoRepository.updateMemo("jpql update", 55L);
        Assertions.assertThat(num > 0).isEqualTo(true);
    }

    @Test
    public void testCode05() {
        int num = memoRepository.updateMemo(Memo.builder().mno(10L).text("jpql obj param update").writer("jpql obj param update").build());
        Assertions.assertThat(num > 0).isEqualTo(true);
    }

    @Test
    public void testCode06() {
//        memoRepository.deleteMemo(11L);
        Assertions.assertThat(memoRepository.findById(11L)).isEmpty();
    }

    @Test
    public void testCode07() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Memo> page = memoRepository.getListJPQL(100L, pageable);
        page.forEach(System.out::println);
    }

    @Test
    public void testCode08() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Object[]> page = memoRepository.getListJPQL2(100L, pageable);
        page.forEach(arr -> System.out.println("mno : " + arr[0] + ", writer : " + arr[1] + ", text : " + arr[2] + ", col : " + arr[3] + ", time : " + arr[4]));
    }
}
