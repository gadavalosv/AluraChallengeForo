package com.gadv.alura.forum.repository;

import com.gadv.alura.forum.domain.model.topic.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    Page<Topic> findByCourse_Id(Long courseId, Pageable pageable);
}
