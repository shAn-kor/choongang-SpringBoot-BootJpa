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
    @NotBlank(message = "이름은 필수 입니다.")
    private String name;

    @NotNull(message = "급여는 필수 입니다.")
    private Integer salary;

    @NotNull(message = "전화번호는 필수 입니다.")
    @Pattern(regexp = "[0-9]{3}-[0-9]{4}-[0-9]{4}", message = "전화번호 형식을 지켜주세요")
    private String phone;

    @NotBlank
    @Email(message = "이메일 형식 이어야 합니다.") // 공백은 통과된다.
    private String email;
}
