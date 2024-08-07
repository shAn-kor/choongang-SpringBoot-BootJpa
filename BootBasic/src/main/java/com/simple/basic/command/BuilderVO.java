package com.simple.basic.command;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class BuilderVO {
    // 1. 멤버변수
    private String name;
    private int age;

//    // 3. 외부 클래스 생성자는 내부클래스를 생성자 매개변수로 받음
//    private BuilderVO(Builder builder) {
//        this.name = builder.name;
//        this.age = builder.age;
//    }
//
//    // 4. 외부에서 객체 생성을 요구하면, 내부클래스를 생성해서 반환
//    public static Builder builder() {
//        return new Builder();
//    }
//
//    // 2. 내부 클래스
//    public static class Builder {
//        private String name;
//        private int age;
//
//        // 내부 클래스 생성자 제한
//        private Builder() {}
//
//        // 5. setter 메서드 (멤버변수 값을 받아 나 자신 반환)
//        public Builder name(String name) {
//            this.name = name;
//            return this;
//        }
//
//        public Builder age(int age) {
//            this.age = age;
//            return this;
//        }
//
//        // 6. build 실행 하면, 외부 클래스 BuilderVO 객체 생성해 반환
//        public BuilderVO build() {
//            return new BuilderVO(this);
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "BuilderVO{" +
//                "name='" + name + '\'' +
//                ", age=" + age +
//                '}';
//    }
}
