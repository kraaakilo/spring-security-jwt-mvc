package dev.network.socialclub.seeders;

import dev.network.socialclub.models.UserModel;
import dev.network.socialclub.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserSeeder {
    private final UserRepository userRepository;
    public UserSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public void seed() {
        UserModel userModel = new UserModel();
        userModel.setName("Kraaakilo");
        userModel.setEmail("marcelvishnk@gmail.com");
        userModel.setPassword("$2a$12$Fb6oZBPXnjaq9aLz1OAMNeHWrb6lA0jAwV8kmpOCemtsQgGgqctPG"); //password
        this.userRepository.save(userModel);
    }

}
