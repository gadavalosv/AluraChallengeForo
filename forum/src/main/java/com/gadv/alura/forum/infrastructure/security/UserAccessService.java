package com.gadv.alura.forum.infrastructure.security;

import com.gadv.alura.forum.domain.model.topic.Topic;
import com.gadv.alura.forum.domain.model.user.User;
import com.gadv.alura.forum.repository.TopicRepository;
import com.gadv.alura.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserAccessService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    public boolean canModifyUser(Long userId) {
        // Obtener usuario autenticado
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentUser = (User) authentication.getPrincipal();

        // Verificar si el usuario es el propietario o tiene rol de ADMIN
        return currentUser.getId().equals(userId) || currentUser.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }

    public boolean canModifyTopic(Long topicId) {
        // Obtener usuario autenticado
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentUser = (User) authentication.getPrincipal();
        var optionalCurrentTopic= topicRepository.findById(topicId);
        Topic currentTopic = null;
        if(optionalCurrentTopic.isPresent()){
            currentTopic = optionalCurrentTopic.get();
        }
        if(currentTopic == null){
            return false;
        }

        // Verificar si el usuario es el propietario o tiene rol de ADMIN
        return currentUser.getId().equals(currentTopic.getAuthor().getId()) || currentUser.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }
}
