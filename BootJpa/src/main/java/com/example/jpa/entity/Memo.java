package com.example.jpa.entity;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;

@Entity // jpa 가 엔티티로 관리한다는 의미
@Table(name = "MEMO") // 메모 테이블
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
public class Memo {
    // 엔티티 정의하면, 하이버네이트가 ddl 대신 실행해준다. spring.jpa.hibernate.ddl-auto=update 옵션

    @Id // pk
// oracle
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "이름")
//    @SequenceGenerator(name = "이름", sequenceName = "시퀀스명", initialValue = 1, allocationSize = 1)
    // MySQL
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private long mno;

    @Column(length = 200, nullable = false)
    private String writer;

    @Column(columnDefinition = "varchar(200) default 'y'") // 만들고 싶은 제약 직접 명시
    private String text;

    @ManyToOne
    @JoinColumn(name = "member_id") // 컬럼명 명시 않아면 member_주키 로 자동 생성
    private Member member;
}
