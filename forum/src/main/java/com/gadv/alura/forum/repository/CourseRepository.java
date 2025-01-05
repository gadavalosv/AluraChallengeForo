package com.gadv.alura.forum.repository;

import com.gadv.alura.forum.domain.model.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
