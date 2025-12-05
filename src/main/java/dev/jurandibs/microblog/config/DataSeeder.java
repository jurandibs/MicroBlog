package dev.jurandibs.microblog.config;

import dev.jurandibs.microblog.enums.RoleEnum;
import dev.jurandibs.microblog.models.User;
import dev.jurandibs.microblog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("teste") == null) {
            User user = new User();
            user.setName("Teste User");
            user.setEmail("teste@example.com");
            user.setUsername("teste");
            user.setPassword(passwordEncoder.encode("123")); // Hashed password
            user.setRole(RoleEnum.USER);

            userRepository.save(user);
            System.out.println("User 'teste' created with password '123'");
        }
    }
}
