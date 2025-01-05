package com.gadv.alura.forum.domain.model.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicRegisterData(
        @NotBlank(message = "{title.required}")
        String title,
        @NotBlank(message = "{message.required}")
        String message,
        @NotBlank(message = "{status.required}")
        String status,
        @NotNull(message = "{authorId.required}")
        Long authorId,
        @NotNull(message = "{courseId.required}")
        Long courseId
) {
}
