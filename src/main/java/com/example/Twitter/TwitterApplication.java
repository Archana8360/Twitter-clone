package com.example.Twitter;

import com.example.Twitter.config.RSAKeyProperties;
import com.example.Twitter.models.ApplicationUser;
import com.example.Twitter.models.Role;
import com.example.Twitter.repositories.RoleRepository;
import com.example.Twitter.repositories.UserRepository;
import com.example.Twitter.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableConfigurationProperties(RSAKeyProperties.class)
public class TwitterApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwitterApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepo, UserRepository userRepository, PasswordEncoder passwordEncoder){
		return args -> {
			Role r = roleRepo.save(new Role(1,"USER"));
			Set<Role> roles = new HashSet<>();
			roles.add(r);
			ApplicationUser u = new ApplicationUser();
			u.setAuthorities(roles);
			u.setFirstName("Archana");
			u.setLastName("Chauhan");
			u.setEmail("wipor61753@elixirsd.com");
			u.setUsername("ArchanaChauhan70187");
			u.setPassword(passwordEncoder.encode("password"));
			u.setEnabled(true);

			userRepository.save(u);
		};
	}
}

