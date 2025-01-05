package com.gadv.alura.forum.domain.model.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResponseRegisterData(
        @NotBlank(message = "{message.required}")
        String message,
        @NotNull(message = "{topicId.required}")
        Long topicId,
        @NotNull(message = "{authorId.required}")
        Long authorId
) {
}
