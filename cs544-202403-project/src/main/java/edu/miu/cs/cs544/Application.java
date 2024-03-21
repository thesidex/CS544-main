package edu.miu.cs.cs544;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = { "edu.miu.common", "edu.miu.cs.cs544" })
@EnableKafka
@EnableScheduling
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
