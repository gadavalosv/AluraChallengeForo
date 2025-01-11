package com.gadv.alura.forum.domain.model.response;

import java.time.LocalDateTime;

public record ResponseResponseData(
        String message,
        String authorName,
        LocalDateTime createdAt
) {
    public ResponseResponseData(Response response){
        this(response.getMessage(), response.getAuthor().getName(), response.getCreatedAt());
    }
}
