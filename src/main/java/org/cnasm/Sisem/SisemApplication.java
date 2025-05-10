package org.cnasm.Sisem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class SisemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SisemApplication.class, args);

		// Solo para generar una contraseña encriptada y copiarla en tu base de datos
		String rawPassword = "admin"; // o la que quieras
		String hash = new BCryptPasswordEncoder().encode(rawPassword);
		System.out.println("Hashed password: " + hash);
	}



}
