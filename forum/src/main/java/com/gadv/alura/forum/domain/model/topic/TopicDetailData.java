package com.gadv.alura.forum.domain.model.topic;

import java.time.LocalDateTime;

public record TopicDetailData(
        Long id,
        String title,
        String message,
        LocalDateTime createdAt,
        String authorName,
        String courseName
) {
    public TopicDetailData(Topic topic) {
        this(topic.getId(), topic.getTitle(), topic.getMessage(), topic.getCreatedAt(), topic.getAuthor().getName(), topic.getCourse().getName());
    }
}
