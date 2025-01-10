package com.gadv.alura.forum.domain.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDetailData(
        Long id,
        String name,
        @NotBlank(message = "{email.required}")
        @Email(message = "{email.invalid}")
        String email
) {
    public UserDetailData(User user) {
        this(user.getId(), user.getName(), user.getEmail());
    }
}
