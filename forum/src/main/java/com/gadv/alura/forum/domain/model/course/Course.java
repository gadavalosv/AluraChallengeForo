package com.gadv.alura.forum.domain.model.course;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "courses")
@Entity(name = "Course")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String category;

    public void updateData(CourseUpdateData courseUpdateData) {
        if(courseUpdateData.name() != null && !courseUpdateData.name().isEmpty()){
            this.name = courseUpdateData.name();
        }
        if(courseUpdateData.category() != null && !courseUpdateData.category().isEmpty()){
            this.category = courseUpdateData.category();
        }
    }
}
