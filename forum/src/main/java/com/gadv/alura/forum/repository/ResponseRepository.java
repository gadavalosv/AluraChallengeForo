package com.gadv.alura.forum.repository;

import com.gadv.alura.forum.domain.model.response.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    Page<Response> findByTopic_Id(Long topicId, Pageable pageable);
}
