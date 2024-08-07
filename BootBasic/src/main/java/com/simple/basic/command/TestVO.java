package com.simple.basic.command;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class TestVO {
    private int num;
    private String name;
    private int age;
    private String addr;
}
