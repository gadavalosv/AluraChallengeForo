package com.gadv.alura.forum.domain;

public class ForumValidationException extends RuntimeException {
    public ForumValidationException(String message) {
        super(message);
    }
}
