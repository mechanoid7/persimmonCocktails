package com.example.persimmoncocktails.models;

import com.example.persimmoncocktails.configurations.security.ApplicationUserPermission;
import com.example.persimmoncocktails.configurations.security.ApplicationUserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person implements UserDetails {
    private Long personId;
    private String name;
    private String email;
    private String password;
    private Long photoId;
    private Long blogId;
    private Integer roleId;
//    private boolean isActive;

    @Override
    public Collection<? extends SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> permissions = new HashSet<>();
        switch (roleId) {
            case 1 -> {
                permissions.addAll(mapToSimpleGrantedAuthority(ApplicationUserRole.ADMIN.getPermissions()));
                permissions.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
            case 2 -> {
                permissions.addAll(mapToSimpleGrantedAuthority(ApplicationUserRole.MODERATOR.getPermissions()));
                permissions.add(new SimpleGrantedAuthority("ROLE_MODERATOR"));
            }
            case 3 -> {
                permissions.addAll(mapToSimpleGrantedAuthority(ApplicationUserRole.CLIENT.getPermissions()));
                permissions.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
            }
        }

        return permissions;
    }

    private static Set<SimpleGrantedAuthority> mapToSimpleGrantedAuthority(Set<ApplicationUserPermission> permissions) {
        return permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermission()))
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
//        return isActive;
        return true;
    }
}