package com.gadv.alura.forum.controller;

import com.gadv.alura.forum.domain.model.response.*;
import com.gadv.alura.forum.repository.ResponseRepository;
import com.gadv.alura.forum.repository.TopicRepository;
import com.gadv.alura.forum.repository.UserRepository;
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
@RequestMapping("/responses")
@SecurityRequirement(name = "bearer-key")
public class ResponseController {
    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<ResponseDetailData> addResponse(@RequestBody @Valid ResponseRegisterData responseRegisterData, UriComponentsBuilder uriComponentsBuilder) {
        var topic = topicRepository.findById(responseRegisterData.topicId()).orElse(null);
        var author = userRepository.findById(responseRegisterData.authorId()).orElse(null);
        if (topic == null || author == null) {
            return ResponseEntity.badRequest().build();
        }
        var response = new Response(responseRegisterData.message(), topic, author);
        responseRepository.save(response);

        URI uri = uriComponentsBuilder.path("/responses/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponseDetailData(response));
    }

    @GetMapping("/topic/{topicId}")
    public ResponseEntity<Page<ResponseResponseData>> getResponsesByTopic(@PathVariable Long topicId, Pageable pageable) {
        var topicExists = topicRepository.existsById(topicId);
        if (!topicExists) {
            return ResponseEntity.notFound().build();
        }

        var responses = responseRepository.findByTopic_Id(topicId, pageable).map(ResponseResponseData::new);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseDetailData> updateResponse(@PathVariable Long id, @RequestBody @Valid ResponseUpdateData responseUpdateData) {
        var response = responseRepository.findById(id).orElse(null);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        response.updateData(responseUpdateData);
        return ResponseEntity.ok(new ResponseDetailData(response));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteResponse(@PathVariable Long id) {
        if (!responseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        responseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
