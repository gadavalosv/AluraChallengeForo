package com.gadv.alura.forum.domain.model.response;

import com.gadv.alura.forum.domain.model.topic.Topic;
import com.gadv.alura.forum.domain.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "responses")
@Entity(name = "Response")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Equivalente a BIGSERIAL en PostgreSQL

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(nullable = false)
    private Boolean isSolution = false;

    public Response(String message, Topic topic, User author) {
        this.message = message;
        this.author = author;
        this.topic = topic;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public void updateData(ResponseUpdateData responseUpdateData) {
        if(responseUpdateData.message() != null && !responseUpdateData.message().isEmpty()) {
            this.message = responseUpdateData.message();
        }
        if(responseUpdateData.isSolution() != null) {
            this.isSolution = responseUpdateData.isSolution();
        }
    }
}
