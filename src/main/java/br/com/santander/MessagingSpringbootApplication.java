package br.com.santander;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class MessagingSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessagingSpringbootApplication.class, args);
	}
}


