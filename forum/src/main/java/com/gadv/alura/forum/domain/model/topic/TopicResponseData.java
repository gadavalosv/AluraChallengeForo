package com.gadv.alura.forum.domain.model.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicResponseData(
        @NotNull
        Long id,
        @NotBlank(message = "{title.required}")
        String title,
        @NotBlank(message = "{message.required}")
        String message,
        String status,
        Long authorId,
        String courseName
) {
        public TopicResponseData(Topic topic) {
                this(topic.getId(), topic.getTitle(), topic.getMessage(), topic.getStatus(), topic.getAuthor().getId(), topic.getCourse().getName());
        }
}
