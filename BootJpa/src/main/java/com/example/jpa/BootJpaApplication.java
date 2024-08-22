package com.example.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing  // jpa 오디팅 기능 활성화 (jpa가 자동으로 변수 입력)
public class BootJpaApplication {
	public static void main(String[] args) {
		SpringApplication.run(BootJpaApplication.class, args);
	}

}
