package ru.flendger.demo.store.demo.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:secured.properties")
public class DemoStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoStoreApplication.class, args);
	}

}
