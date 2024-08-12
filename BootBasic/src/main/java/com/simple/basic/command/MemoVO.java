package com.simple.basic.command;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class MemoVO {
    private Integer mno;
    @NotNull
    @Pattern(regexp = ".{5,}")
    private String memo;
    @Pattern(regexp = "[0-9]{3}-[0-9]{4}-[0-9]{4}")
    private String phone;
    @NotBlank
    @Pattern(regexp = "[0-9]{4}")
    private String pw;
    @NotNull
    private Character secret;
}
