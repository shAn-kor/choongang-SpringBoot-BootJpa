package com.example.jpa;

import com.example.jpa.entity.Memo;
import com.example.jpa.repository.MemoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class JPABasicTest01 {
    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testCode01() {
        for (int i = 1; i <= 100; i++) {
            memoRepository.save(Memo.builder().text("test" + i).writer("test" + i).build());
        }
    }

    @Test
    public void testCode02() {
        Optional<Memo> result = memoRepository.findById(55L);

        if (result.isPresent()) {
            Memo memo = result.get();
        }
    }

    @Test
    public void testCode03() {
        List<Memo> list = memoRepository.findAll();
        for (Memo memo : list) {
            System.out.println(memo.toString());
        }
    }

    @Test
    public void testCode04() {
        memoRepository.save(Memo.builder().mno(50L).writer("update success").text("update success").build());
        System.out.println(memoRepository.findById(50L));
    }

    @Test
    public void testCode05() {
//        memoRepository.deleteById(50L);

        Assertions.assertThat(memoRepository.findById(50L)).isEmpty();
    }
}
