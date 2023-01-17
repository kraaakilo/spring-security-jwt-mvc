package dev.network.socialclub.services;

import dev.network.socialclub.models.UserModel;
import dev.network.socialclub.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public boolean addUser(UserModel user) {
        if (this.userRepository.findUserModelByEmail(user.getEmail()).isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            this.userRepository.save(user);
            return true;
        }
        return false;
    }

}
