package com.gadv.alura.forum.domain.model.topic;

import jakarta.validation.constraints.NotNull;

public record TopicUpdateData(
        String title,
        String message,
        String status
) {
}
