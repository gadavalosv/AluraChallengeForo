package com.gadv.alura.forum.domain.model.user;

import com.gadv.alura.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserAccessService {
    @Autowired
    private UserRepository userRepository;

    public boolean canModifyUser(Long userId) {
        // Obtener usuario autenticado
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentUser = (User) authentication.getPrincipal();

        // Verificar si el usuario es el propietario o tiene rol de ADMIN
        return currentUser.getId().equals(userId) || currentUser.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }
}
