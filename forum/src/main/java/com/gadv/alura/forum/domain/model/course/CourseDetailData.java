package com.gadv.alura.forum.domain.model.course;

public record CourseDetailData(
        Long id,
        String name,
        String category
) {
    public CourseDetailData(Course course) {
        this(course.getId(), course.getName(), course.getCategory());
    }
}
