package com.gadv.alura.forum.domain.model.course;

import jakarta.validation.constraints.NotBlank;

public record CourseRegisterData(
        @NotBlank(message = "{name.required}")
        String name,
        @NotBlank(message = "{category.required}")
        String category
) {
}
