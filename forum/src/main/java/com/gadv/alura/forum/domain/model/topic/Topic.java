package com.gadv.alura.forum.domain.model.topic;

import com.gadv.alura.forum.domain.model.course.Course;
import com.gadv.alura.forum.domain.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topics")
@Entity(name = "Topic")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(nullable = false, length = 50)
    private String status;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public void updateData(TopicUpdateData topicUpdateData) {
        this.title = (topicUpdateData.title() != null && !topicUpdateData.title().isEmpty()) ? topicUpdateData.title() : this.title;
        this.message = (topicUpdateData.message() != null && !topicUpdateData.message().isEmpty()) ? topicUpdateData.message() : this.message;
        this.status = (topicUpdateData.status() != null && !topicUpdateData.status().isEmpty()) ? topicUpdateData.status() : this.status;
    }
}
