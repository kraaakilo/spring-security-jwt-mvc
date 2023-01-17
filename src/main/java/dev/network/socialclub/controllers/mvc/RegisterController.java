package dev.network.socialclub.controllers.mvc;

import dev.network.socialclub.models.UserModel;
import dev.network.socialclub.services.UserRegisterValidationService;
import dev.network.socialclub.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final UserService userService;
    private final UserRegisterValidationService userRegisterValidationService;

    @Autowired
    public RegisterController(UserService userService, UserRegisterValidationService userRegisterValidationService) {
        this.userService = userService;
        this.userRegisterValidationService = userRegisterValidationService;
    }

    @GetMapping
    public String home(UserModel userModel) {
        return "register";
    }

    @PostMapping
    public String create(@Valid UserModel userModel, BindingResult bindingResult, Model model) throws Exception {
        List<String> errors = this.userRegisterValidationService.validate(userModel);
        for (String error : errors) {
            bindingResult.addError(new FieldError("user","email", error));
        }
        if (bindingResult.hasErrors()) {
            return "register";
        } else {
            this.userService.addUser(userModel);
            return "success";
        }
    }
}
