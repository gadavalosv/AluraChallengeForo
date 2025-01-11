package com.gadv.alura.forum.domain.model.user;

import com.gadv.alura.forum.domain.model.profile.Profile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Table(name = "users")
@Entity(name = "User")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_profile",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    private Set<Profile> profiles = new HashSet<>();

    public User(UserRegisterData userRegisterData, PasswordEncoder passwordEncoder) {
        this.email = userRegisterData.email();
        this.password = passwordEncoder.encode(userRegisterData.password());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return profiles.stream()
                .map(profile -> (GrantedAuthority) () -> "ROLE_" + profile.getName().toUpperCase())
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void updateData(UserUpdateData userUpdateData, PasswordEncoder passwordEncoder) {
        if(userUpdateData.name() != null && !userUpdateData.name().isEmpty()) {
            this.name =  userUpdateData.name();
        }
        if(userUpdateData.password() != null && !userUpdateData.password().isEmpty()) {
            this.password = passwordEncoder.encode(userUpdateData.password());
        }
    }
}
