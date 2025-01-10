package com.gadv.alura.forum.domain.model.user;

import jakarta.validation.constraints.Pattern;

public record UserUpdateData(
        String name,
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
                message = "{password.invalid}"
        )
        String password
) {
}
