package com.simple.basic.command;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class ValidVO {
    /*
        @NotNull - null 제외
        @NotEmpty - null 제외, 공백 제외
        @NotBlank - null 제외, 공백 제외, 화이트스페이스 제외
    */
    @NotBlank
    private String name;

    @NotNull
    private Integer salary;

    @NotNull
    @Pattern(regexp = "[0-9]{3}-[0-9]{4}-[0-9]{4}", message = "전화번호 형식을 지켜주세요")
    private String phone;

    @NotBlank
    @Email // 공백은 통과된다.
    private String email;
}
