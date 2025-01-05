package com.gadv.alura.forum.controller;

import com.gadv.alura.forum.domain.model.topic.*;
import com.gadv.alura.forum.repository.TopicRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topics")
@SecurityRequirement(name = "bearer-key")
public class TopicController {
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private TopicGeneration topicGeneration;

    @PostMapping
    @Transactional
    public ResponseEntity<TopicDetailData> addTopic(@RequestBody @Valid TopicRegisterData topicRegisterData, UriComponentsBuilder uriComponentsBuilder){
        var topicDetailData = topicGeneration.generate(topicRegisterData);
        URI url = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topicDetailData.id()).toUri();
        return ResponseEntity.created(url).body(topicDetailData);
    }

    @GetMapping
    public ResponseEntity<Page<Topic>> getAllTopics(Pageable pageable) {
        var topics = topicRepository.findAll(pageable);
        return ResponseEntity.ok(topics);
    }

    @GetMapping("/course/{courseName}")
    public ResponseEntity<Page<Topic>> getTopicsByCourseName(@PathVariable String courseName, Pageable pageable) {
        var topics = topicRepository.findByCourse_Name(courseName, pageable);
        return ResponseEntity.ok(topics);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicResponseData> updateTopic(@PathVariable Long id, @RequestBody @Valid TopicUpdateData topicUpdateData) {
        var topic = topicRepository.findById(id).orElse(null);
        if (topic == null) {
            return ResponseEntity.notFound().build();
        }
        topic.updateData(topicUpdateData);
        return ResponseEntity.ok(new TopicResponseData(topic));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        if (!topicRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        topicRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
