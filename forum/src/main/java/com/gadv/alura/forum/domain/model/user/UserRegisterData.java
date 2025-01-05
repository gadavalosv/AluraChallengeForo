package com.gadv.alura.forum.domain.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRegisterData(
        @NotBlank(message = "{name.required}")
        String name,
        @NotBlank(message = "{email.required}")
        @Email(message = "{email.invalid}")
        String email,
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
                message = "{password.invalid}"
        )
        String password
) {
}
