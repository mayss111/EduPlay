package org.eduplay.eduplay.auth.dto;


import jakarta.validation.constraints.*;
import org.eduplay.eduplay.enums.AppLanguage;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Prénom requis")
    private String firstName;

    @NotBlank(message = "Username requis")
    @Size(min = 3, max = 30)
    private String username;

    @NotBlank(message = "Password requis")
    @Size(min = 6, message = "Minimum 6 caractères")
    private String password;

    @NotNull(message = "Classe requise")
    @Min(1) @Max(6)
    private Integer classLevel;

    @NotNull(message = "Langue requise")
    private AppLanguage language;

    private Integer avatarIndex = 0;
}