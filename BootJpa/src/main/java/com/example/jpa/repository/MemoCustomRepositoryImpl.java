package com.example.jpa.repository;

import com.example.jpa.entity.*;
import com.example.jpa.util.Criteria;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

public class MemoCustomRepositoryImpl implements MemoCustomRepository {
    @PersistenceContext // 엔티티 매니저 주입 받을때 사용
    private EntityManager entityManager;

    // querydsl 사용 시 엔티티 매니저 받아 jpafactory를 멤버변수에 저장
    private JPAQueryFactory jpaQueryFactory;

    public MemoCustomRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    @Transactional
    public int updateTest(String writer, long mno) {
        String sql = "update Memo m set m.writer=:a where m.mno=:b";
        Query query = entityManager.createQuery(sql);
        query.setParameter("a", writer);
        query.setParameter("b", mno);
        return query.executeUpdate();
    }

    @Override
    public List<Memo> memoJoinMember() {
        TypedQuery<Memo> query = entityManager.createQuery("select m from Memo m inner join m.member", Memo.class);

        return query.getResultList();
    }

    @Override
    public List<Memo> mtoJoin1(long mno) {

        TypedQuery<Memo> result = entityManager.createQuery(
                "select m from Memo m inner join m.member where m.mno <= :mno", //조인의 테이블 위치에 엔티티의 필드명이 들어감
                //"select m from Memo m left join m.member where m.mno <= :mno",
                //"select m from Memo m right join m.member where m.mno <= :mno",
                Memo.class);

        result.setParameter("mno", mno); //파라미터 세팅
        List<Memo> list = result.getResultList(); //리스트 타입으로 반환받음

        return list;
    }

    @Override
    public List<Object[]> mtoJoin2(long mno) {

        TypedQuery<Object[]> result = entityManager.createQuery(
                "select m, x from Memo m left join m.member x where m.mno <= :mno"
                , Object[].class
        );

        result.setParameter("mno", mno);
        List<Object[]> list = result.getResultList();

        return list;
    }

    @Override
    public List<Object[]> mtoJoin3(String writer) {

        TypedQuery<Object[]> result = entityManager.createQuery(
                "select m, x from Memo m inner join Member x on m.writer = x.name where m.writer = :writer"
                , Object[].class
        );

        result.setParameter("writer", writer);
        List<Object[]> list = result.getResultList();

        return list;
    }

    @Override
    public List<Memo> mtoJoin4() {
        TypedQuery<Memo> result = entityManager.createQuery(
                "select m from Memo m left join fetch m.member x"
                , Memo.class
        );
        return result.getResultList();
    }

    @Override
    public Member otmJoin1(String id) {

        TypedQuery<Member> result = entityManager.createQuery(
                "select m from Member m inner join m.list where m.id = :id"
                , Member.class
        );
        result.setParameter("id", id);
        Member m = result.getSingleResult(); //1행인 경우
        return m;
    }

    @Override
    public List<Member> otmJoin2(String id) {

        TypedQuery<Member> result = entityManager.createQuery(
                //"select m from Member m inner join fetch m.list x where m.id = :id"
                "select distinct m from Member m inner join fetch m.list x where m.id = :id"
                , Member.class
        );

        result.setParameter("id", id);
        List<Member> list = result.getResultList();

        return list;
    }

    @Override
    public List<MemberMemoDTO> otmJoin3(String id) {

        //구문의 select절에는 생성자의 맵핑하는 구문이 들어갑니다.
        TypedQuery<MemberMemoDTO> result = entityManager.createQuery(
                "select new com.example.jpa.entity.MemberMemoDTO(m.id, m.name, m.signDate, x.mno, x.writer, x.text ) " +
                        " from Member m inner join m.list x where m.id = :id"
                , MemberMemoDTO.class
        );

        result.setParameter("id", id);
        List<MemberMemoDTO> list = result.getResultList();

        return list;
    }

    //SELECT M.id, M.name, M.signDate, X.mno, X.writer, X.text
    //FROM MEMO M
    //LEFT JOIN MEMBER X
    //ON M.MEMBER_ID = X.ID
    //WHERE M.TEXT LIKE '%1%';
    @Override
    public Page<MemberMemoDTO> joinPage(String text, Pageable pageable) {

        TypedQuery<MemberMemoDTO> result = entityManager.createQuery(
                "select new com.example.jpa.entity.MemberMemoDTO(x.id, x.name, x.signDate, m.mno, m.writer, m.text) " +
                        "from Memo m left join m.member x where m.writer like :writer"
                , MemberMemoDTO.class
        );

        result.setParameter("writer", "%" + text + "%"); //파라미터 셋팅
        result.setFirstResult((int) pageable.getOffset()); //페이지 시작 번호 - PageRequest.of(0, 10) <- 앞에값
        result.setMaxResults(pageable.getPageSize()); //데이터 개수 - PageRequest.of(0, 10) <- 뒤에값
        List<MemberMemoDTO> list = result.getResultList(); //데이터

        //countQuery를 실행
        Query countQuery = entityManager.createQuery(
                "select count(m) from Memo m left join m.member x where m.writer like :writer"
        );
        countQuery.setParameter("writer", "%" + text + "%"); //파라미터 셋팅
        Long count = (Long) countQuery.getSingleResult(); //한 행의 결과가 반환됨

        //실행결과를 Pagealbe객체에 담는다.
        PageImpl<MemberMemoDTO> page = new PageImpl<>(list, pageable, count); //데이터, 페이지객체, 토탈카운트

        return page;
    }

    @Override
    public Memo dslSelect() {
        // 엔티티 대신해 q클래스 사용
        QMemo memo = QMemo.memo;

        // select 값을 선택적으로 받을 때
        Tuple t = jpaQueryFactory.select(memo.mno, memo.writer).from(memo).where(memo.mno.eq(10L)).fetchOne();
        System.out.println(t.get(memo.mno));
        System.out.println(t.get(memo.writer));

        return jpaQueryFactory.select(memo).from(memo).where(memo.mno.eq(10L)).fetchOne();
    }

    @Override
    public List<Memo> dslSelect2() {
        QMemo memo = QMemo.memo;

        return jpaQueryFactory.select(memo).from(memo).where(memo.text.like("%2%")).orderBy(memo.mno.desc()).fetch();
    }

    @Override
    public List<Memo> dslSelect3(String searchType, String searchName) {
        QMemo memo = QMemo.memo;

        // 동적쿼리 구문 만들때 booleanbuilder 클래스 사용한다.
        BooleanBuilder builder = new BooleanBuilder();

        if (searchType.equals("writer")) builder.and(memo.writer.like("%" + searchName + "%"));
        if (searchType.equals("text")) builder.and(memo.writer.like("%" + searchName + "%"));

        return jpaQueryFactory.select(memo).from(memo).where(builder).orderBy(memo.mno.desc()).fetch();
    }

    @Override
    public List<Memo> dslJoin() {
        QMemo memo = QMemo.memo;
        QMember member = QMember.member;

        return jpaQueryFactory.select(memo).from(memo)
//                .join(memo.member, member)
//                .leftJoin(memo.member, member)
                .rightJoin(memo.member, member)
                .fetch();
    }

    @Override
    public Page<MemberMemoDTO> dslJoinPage(Pageable pageable, Criteria cri) {
        // 1. 불린 빌더
        BooleanBuilder builder = new BooleanBuilder();

        QMemo memo = QMemo.memo;
        QMember member = QMember.member;

        String searchType = Optional.ofNullable(cri.getSearchType()).orElse("");
        String searchName = Optional.ofNullable(cri.getSearchName()).orElse("");
        System.out.println(searchType);
        if (searchType.equals("writer")) builder.and(memo.writer.like("%" + searchName + "%"));
        if (searchType.equals("text")) builder.and(memo.text.like("%" + searchName + "%"));

        List<MemberMemoDTO> list = jpaQueryFactory
                .select(Projections.constructor(MemberMemoDTO.class, member.id, member.name, member.signDate, memo.mno, memo.writer, memo.text))
                .from(memo)
                .leftJoin(memo.member, member)
                .where(builder)
                .orderBy(memo.mno.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory.select(memo).from(memo).leftJoin(memo.member, member).where(builder).fetch().size();

        return new PageImpl<>(list, pageable, total);
    }


    //querydsl

}
