package com.example.jpa.repository;

import com.example.jpa.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface MemoRepository extends JpaRepository<Memo, Long>, MemoCustomRepository, QuerydslPredicateExecutor<Memo> {
    Memo findByWriterAndText(String writer, String text);

    List<Memo> findByMnoLessThan(Long mno);

    List<Memo> findByMnoBetween(Long mno1, Long mno2);

    List<Memo> findByWriterLike(String writer);

    List<Memo> findByWriterOrderByWriterDesc(String writer);

    List<Memo> findByMnoIn(List<Long> list);

    Page<Memo> findByMnoLessThanEqual(Long mno, Pageable pageable);

    // JPQL - SQL과 비슷하나, 엔티티를 사용해서 SQL문 작성
    // select, update, delete 는 제공되는데, insert는 제공하지 않는다.

    // 1. 테이블명이 아니라 엔티티명 사용
    // 2. 속성(필드) 는 대소문자 전부 구분
    // 3. 별핑 필수
    // 4. sql 키워드 구분 x
    @Query("select m from Memo m order by m.mno desc")
    List<Memo> getListDesc();

    // JPQL 파라미터 전달 @Param(이름), : 이름
    @Query("select m from Memo m where m.mno > :mno order by m.mno desc")
    List<Memo> getListDesc2(@Param("mno") Long mno);

    // JPQL select 문의 실행 결과를 선별적으로 받으려면 Object[] 사용
    @Query("select m.writer, m.text from Memo m where m.mno > :num order by m.mno desc")
    List<Object[]> getListDesc3(@Param("num") Long mno);

    // JPQL 업데이트
    @Transactional // 트랜잭션 반영
    @Modifying // update 다 라고 알려줌
    @Query("update Memo m set m.writer = :writer where m.mno = :mno")
    int updateMemo(@Param("writer") String writer, @Param("mno") Long mno);

    // JPQL 업데이트 - 객체 파라미터 넘기기
    @Transactional
    @Modifying
    @Query("update Memo m set m.writer = :#{#a.writer}, m.text = :#{#a.text} where m.mno = :#{#a.mno}")
    int updateMemo(@Param("a") Memo memo);

    @Transactional
    @Modifying
    @Query("delete from Memo m where m.mno = :mno")
    void deleteMemo(@Param("mno") Long mno);

    // JPQL 마지막 매개변수에 Pageable을 주면, 페이지 처리 된다.
    // pageable은 countQuery가 필요하며 직접 작성할 수 있다.
    @Query(value = "select m from Memo m where m.mno <= :a", countQuery = "select count(m) from Memo m where m.mno <= :a")
    Page<Memo> getListJPQL(@Param("a") Long a, Pageable pageable);

    @Query(
            value = "select m.mno, m.writer, m.text, concat(m.writer, m.text) as col, current_timestamp from Memo m where m.mno <= :mno"
    )
    Page<Object[]> getListJPQL2(@Param("mno") Long mno, Pageable pageable);
}
