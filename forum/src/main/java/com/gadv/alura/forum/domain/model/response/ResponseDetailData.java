package com.gadv.alura.forum.domain.model.response;

public record ResponseDetailData(
        String message,
        String topicTitle,
        Long authorId
) {
    public ResponseDetailData(Response response) {
        this(response.getMessage(), response.getTopic().getTitle(), response.getAuthor().getId());
    }
}
