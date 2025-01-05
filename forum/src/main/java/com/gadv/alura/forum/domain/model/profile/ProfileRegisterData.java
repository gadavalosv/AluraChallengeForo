package com.gadv.alura.forum.domain.model.profile;

import jakarta.validation.constraints.NotBlank;

public record ProfileRegisterData(
        @NotBlank(message = "{name.required}")
        String name
) {
}
