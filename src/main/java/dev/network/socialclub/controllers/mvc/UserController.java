package dev.network.socialclub.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/auth")
public class UserController {
    @GetMapping("/account")
    public String account(Principal principal) {
        System.out.println(principal.getName());
        return "auth/account";
    }
}
