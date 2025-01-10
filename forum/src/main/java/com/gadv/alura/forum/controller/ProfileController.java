package com.gadv.alura.forum.controller;

import com.gadv.alura.forum.domain.model.profile.Profile;
import com.gadv.alura.forum.domain.model.profile.ProfileDetailData;
import com.gadv.alura.forum.domain.model.profile.ProfileRegisterData;
import com.gadv.alura.forum.domain.model.profile.ProfileUpdateData;
import com.gadv.alura.forum.repository.ProfileRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/profiles")
@SecurityRequirement(name = "bearer-key")
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfileDetailData> registerProfile(@RequestBody @Valid ProfileRegisterData profileRegisterData, UriComponentsBuilder uriComponentsBuilder) {
        if (profileRepository.existsByName(profileRegisterData.name())) {
            return ResponseEntity.badRequest().body(null);
        }
        var profile = new Profile(null, profileRegisterData.name());
        profileRepository.save(profile);
        URI uri = uriComponentsBuilder.path("/profiles/{id}").buildAndExpand(profile.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProfileDetailData(profile));
    }

    @GetMapping
    public ResponseEntity<Page<ProfileDetailData>> getAllProfiles(@PageableDefault(size = 10) Pageable pagination) {
        var profiles = profileRepository
                .findAll(pagination)
                .map(ProfileDetailData::new);
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDetailData> getProfileById(@PathVariable Short id) {
        var profile = profileRepository.findById(id).orElse(null);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ProfileDetailData(profile));
    }

    @PutMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfileDetailData> updateProfile(@PathVariable Short id, @RequestBody ProfileUpdateData profileUpdateData) {
        var profile = profileRepository.findById(id).orElse(null);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
        profile.updateData(profileUpdateData);
        return ResponseEntity.ok(new ProfileDetailData(profile));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProfile(@PathVariable Short id) {
        if (!profileRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        profileRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
