package com.gadv.alura.forum.repository;

import com.gadv.alura.forum.domain.model.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Short> {
    boolean existsByName(String name);
}
