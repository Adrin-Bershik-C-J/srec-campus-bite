package com.srec.fc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.srec.fc.entity.User;
import com.srec.fc.enums.Role;
import com.srec.fc.repository.UserRepository;

@SpringBootApplication
public class FcApplication {

	public static void main(String[] args) {
		SpringApplication.run(FcApplication.class, args);
	}

	@Bean
	CommandLineRunner initAdmin(UserRepository userRepo, PasswordEncoder encoder) {
		return args -> {
			if (userRepo.findByRollNo("admin").isEmpty()) {
				User admin = new User();
				admin.setRollNo("admin");
				admin.setName("Super Admin");
				admin.setPassword(encoder.encode("admin123"));
				admin.setRole(Role.ADMIN);
				userRepo.save(admin);
				System.out.println("✅ Default Admin created: rollNo=admin, password=admin123");
			}
		};
	}
}
