package com.gadv.alura.forum.repository;

import com.gadv.alura.forum.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    //UserDetails findByEmail(String email);
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.profiles WHERE u.email = :email")
    User findByEmailWithProfiles(@Param("email") String email);

    boolean existsByEmail(String email);
}
