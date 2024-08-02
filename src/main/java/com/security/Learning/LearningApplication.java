package com.security.Learning;

import com.security.Learning.entity.Role;
import com.security.Learning.entity.User;
import com.security.Learning.repo.RoleRepo;
import com.security.Learning.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class LearningApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LearningApplication.class, args);
	}
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepo roleRepo;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		Role roleAdmin = roleRepo.findByName("ROLE_ADMIN");
		Role roleUser = roleRepo.findByName("ROLE_USER");
		if(roleAdmin == null) {
			Role role1 = new Role();
			role1.setId(UUID.randomUUID().toString());
			role1.setName("ROLE_ADMIN");
			roleRepo.save(role1);
		}
		else{
			System.out.println("dont be saved");
		}
		if(roleUser == null) {
			Role role = new Role();
			role.setId(UUID.randomUUID().toString());
			role.setName("ROLE_USER");
			roleRepo.save(role);
		}
		else {
			System.out.println("dont be saved");
		}
//		User user1 = userRepo.findByEmail("admin@gmail.com").orElse(null);
		User user2 = userRepo.findByEmail("user@gmail.com").orElse(null);
//		if(user1 == null) {
//			User user = new User();
//			user.setEmail("admin@gmail.com");
//			user.setUserid(UUID.randomUUID().toString());
//			user.setUsername("RAM");
//			user.setPassword(passwordEncoder.encode("admin"));
//			user.setRoles(List.of(roleAdmin,roleUser));
//			userRepo.save(user);
//		}
		if(user2 == null) {
			User use = new User();
			use.setEmail("user@gmail.com");
			use.setUserid(UUID.randomUUID().toString());
			use.setUsername("SYAM");
			use.setPassword(passwordEncoder.encode("root"));
			use.setRoles(List.of(roleUser));
			userRepo.save(use);
		}


	}
}
