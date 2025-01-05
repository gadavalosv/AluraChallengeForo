package com.gadv.alura.forum.domain.model.topic;

import com.gadv.alura.forum.domain.ForumValidationException;
import com.gadv.alura.forum.repository.CourseRepository;
import com.gadv.alura.forum.repository.TopicRepository;
import com.gadv.alura.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicGeneration {
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    public TopicDetailData generate(TopicRegisterData topicRegisterData){
        if(!userRepository.existsById(topicRegisterData.authorId())){
            throw new ForumValidationException("Id de Usuario Invalida");
        }
        if(!courseRepository.existsById(topicRegisterData.courseId())){
            throw new ForumValidationException("Id de Curso Invalida");
        }
        var author = userRepository.findById(topicRegisterData.authorId()).get();
        var course = courseRepository.findById(topicRegisterData.courseId()).get();
        var topic = new Topic(null, topicRegisterData.title(), topicRegisterData.message(), null, topicRegisterData.status(), author, course);
        topicRepository.save(topic);
        return new TopicDetailData(topic);
    }
}
