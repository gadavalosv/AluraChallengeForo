package com.gadv.alura.forum.domain.model.response;

public record ResponseDetailData(
        Long id,
        String message,
        String topicTitle,
        Long authorId
) {
    public ResponseDetailData(Response response) {
        this(response.getId(), response.getMessage(), response.getTopic().getTitle(), response.getAuthor().getId());
    }
}
