package dev.network.socialclub.controllers.rest;

import dev.network.socialclub.models.UserModel;
import dev.network.socialclub.services.UserService;
import dev.network.socialclub.utils.AppResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/user")
public class RestUserController {
    private final UserService userService;
    @Autowired

    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> account(@RequestBody UserModel userModel) throws Exception {
        if(userModel.isValidForRegistration() && this.userService.addUser(userModel)){
            return new AppResponseEntity("User Created", HttpStatus.CREATED).buildResponse();
        }else{
            return new AppResponseEntity("Error Processing your data", HttpStatus.BAD_REQUEST).buildResponse();
        }

    }
}
