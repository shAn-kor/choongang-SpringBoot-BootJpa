package com.example.jpa;

import com.example.jpa.entity.Member;
import com.example.jpa.entity.MemberMemoDTO;
import com.example.jpa.entity.Memo;
import com.example.jpa.repository.MemberRepository;
import com.example.jpa.repository.MemoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class JPACustomTest05 {
    @Autowired
    MemoRepository memoRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testCode01() {
        int num = memoRepository.updateTest("custom repo update", 20L);
        System.out.println(num);
        Assertions.assertThat(num > 0).isEqualTo(true);
    }

    @Test
    public void testCode02() {
        for (int i = 1; i <= 5; i++) {
            memberRepository.save(Member.builder().id("abc" + i).name("admin" + i).build());
        }
    }
    //조인테스트1
    @Test
    public void testCode03() {
        List<Memo> list = memoRepository.mtoJoin1(10L);
        for(Memo m : list ) {
            System.out.println(m.toString());
        }
    }

    //두개이상의 엔티티 동시 조회
    @Test
    public void testcode04() {
        List<Object[]> list = memoRepository.mtoJoin2(10L);

        for(Object[] arr : list) {
            System.out.println(Arrays.toString(arr) );
        }

    }

    //연관 관계없는 엔티티 조인
    @Test
    public void testcode05(){
        List<Object[]> list = memoRepository.mtoJoin3("admin1");

        for(Object[]  arr :list ) {
            System.out.println( Arrays.toString(arr));
        }

    }

    //eager조인 vs lazy조인
    //manyToOne의 조인 default는 EAGER입니다.
    @Test
    @Transactional //Lazy을 쓰려면 트랜잭션으로 묶어줘야 함.
    public void testCode06() {
        List<Memo> list = memoRepository.mtoJoin1(10L);

        Memo m = list.get(0); //실제 데이터를 조회할때 뒤늦게 select가 동작함
        System.out.println(m);
    }

    //성능향상 fetch조인 - 단 한번의 select가 실행됨(로딩 전략보다 우선시됨)
    @Test
    public void testCode07() {
        List<Memo> list = memoRepository.mtoJoin4();
        for(Memo m: list) {
            System.out.println(m.toString());
        }
    }

    ////////////////////////////////////////////////
    //원투매니 방식
    @Test
    public void testcode08() {
        Member m = memoRepository.otmJoin1("abc1");
        System.out.println(m.toString());
    }

    //원투매니 fetch조인
    @Test
    public void testCode09() {
        List<Member> list = memoRepository.otmJoin2("abc1");
        for(Member m : list ) {
            System.out.println(m.toString());
        }
    }

    /////////////////////////////////////////////////
    //DTO로 반환받기
    @Test
    public void testCode10() {
        List<MemberMemoDTO> list = memoRepository.otmJoin3("abc1");

        for(MemberMemoDTO m : list) {
            System.out.println(m.toString());
        }

    }

    /*@Test
    public void testCode11() {

        Page<MemberMemoDTO> list =
                memoRepository.joinPage("1", PageRequest.of(0, 10) );

        for( MemberMemoDTO m : list.getContent()) {
            System.out.println(m.toString()); //데이터
        }

        System.out.println( "전체게시글수:" +  list.getTotalElements()  );




    }*/



}
