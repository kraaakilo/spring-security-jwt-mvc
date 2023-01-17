package dev.network.socialclub.services;

import dev.network.socialclub.models.UserModel;
import dev.network.socialclub.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class UserRegisterValidationService {
    private final UserRepository userRepository;

    @Autowired
    public UserRegisterValidationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<String> validate(UserModel userModel) {
        List<String> errors = new ArrayList<>();
        if(this.userRepository.findUserModelByEmail(userModel.getEmail()).isPresent()){
            errors.add("Email already in database");
        }
        return errors;
    }
}
