package dev.network.socialclub.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @NotEmpty(message = "Please fill email address.")
    @Email(message = "Please enter a valid email address.")
    private String username;
    @Size(min = 8,message = "You need to provide a password at least at 8 characters")
    private String password;
}
