package com.gadv.alura.forum.controller;

import com.gadv.alura.forum.domain.model.user.User;
import com.gadv.alura.forum.domain.model.user.UserDetailData;
import com.gadv.alura.forum.domain.model.user.UserRegisterData;
import com.gadv.alura.forum.domain.model.user.UserUpdateData;
import com.gadv.alura.forum.repository.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearer-key")
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<UserDetailData> registerUser(@RequestBody @Valid UserRegisterData userRegisterData, UriComponentsBuilder uriComponentsBuilder) {
        if (userRepository.existsByEmail(userRegisterData.email())) {
            return ResponseEntity.badRequest().body(null);
        }
        var user = new User(userRegisterData, passwordEncoder);
        userRepository.save(user);
        URI uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserDetailData(user));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDetailData>> getAllUsers(@PageableDefault(size = 10) Pageable pagination) {
        var users = userRepository
                .findAll(pagination)
                .map(UserDetailData::new);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    @Transactional
    @PreAuthorize("@userAccessService.canModifyUser(#id)")
    public ResponseEntity<UserDetailData> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateData userUpdateData) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.updateData(userUpdateData, passwordEncoder);
        return ResponseEntity.ok(new UserDetailData(user));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("@userAccessService.canModifyUser(#id)")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
