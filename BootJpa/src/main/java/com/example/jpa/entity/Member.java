package com.example.jpa.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MEMBER")
@Getter
@Setter
//@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class) //JPA엔티티를 관리는 영속성영역에서 변화를 감지해서 자동으로 변수에 적용해주는 역할
//BootMywebApplication 클래스에 @EnableJpaAuditing 를 추가해야합니다.
public class Member {
    @Id
    private String id;

    @Column(length = 50, nullable = false)
    private String name;

    @CreatedDate // jpa 통해서 insert 시 날짜 자동 입력
    @Column(updatable = false) // 이 컬럼 jpa에 의해서 자동 변경 방지
    private LocalDateTime signDate; //양방향 맵핑
    //toString 한쪽에서 지워~
    @OneToMany(mappedBy = "member") //mapperdBy는 1쪽에서 지정하고, 연관관계의 주인을 나타내는 키워드 (N쪽의 필드명)
    private final List<Memo> list = new ArrayList<>();
}
