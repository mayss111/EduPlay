package org.eduplay.eduplay.auth.dto;



import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Username requis")
    private String username;

    @NotBlank(message = "Password requis")
    private String password;
}