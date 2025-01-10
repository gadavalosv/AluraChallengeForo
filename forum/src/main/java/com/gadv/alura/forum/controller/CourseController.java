package com.gadv.alura.forum.controller;

import com.gadv.alura.forum.domain.model.course.Course;
import com.gadv.alura.forum.domain.model.course.CourseDetailData;
import com.gadv.alura.forum.domain.model.course.CourseRegisterData;
import com.gadv.alura.forum.domain.model.course.CourseUpdateData;
import com.gadv.alura.forum.repository.CourseRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/courses")
@SecurityRequirement(name = "bearer-key")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseDetailData> registerCourse(@RequestBody @Valid CourseRegisterData courseRegisterData, UriComponentsBuilder uriComponentsBuilder) {
        var course = new Course(null, courseRegisterData.name(), courseRegisterData.category());
        courseRepository.save(course);
        URI uri = uriComponentsBuilder.path("/courses/{id}").buildAndExpand(course.getId()).toUri();
        return ResponseEntity.created(uri).body(new CourseDetailData(course));
    }

    @GetMapping
    public ResponseEntity<Page<CourseDetailData>> getAllCourses(@PageableDefault(size = 10) Pageable pagination) {
        var courses = courseRepository
                .findAll(pagination)
                .map(CourseDetailData::new);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDetailData> getCourseById(@PathVariable Long id) {
        var course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new CourseDetailData(course));
    }

    @PutMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseDetailData> updateCourse(@PathVariable Long id, @RequestBody CourseUpdateData courseUpdateData) {
        var course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }
        course.updateData(courseUpdateData);
        return ResponseEntity.ok(new CourseDetailData(course));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        if (!courseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        courseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
