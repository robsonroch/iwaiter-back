package br.com.robson.waiterback;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.robson.waiterback.entities.User;

@SpringBootApplication
public class IwaterApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(IwaterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User teste = new User();
	}

}
