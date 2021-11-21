package com.example.persimmoncocktails.models;

import com.example.persimmoncocktails.configurations.PersonRolesConfig;
import com.example.persimmoncocktails.configurations.security.ApplicationUserPermission;
import com.example.persimmoncocktails.configurations.security.ApplicationUserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@PropertySource("classpath:var/general.properties")
public class Person implements UserDetails {
    private Long personId;
    private String name;
    private String email;
    private String password;
    private Long photoId;
    private Long blogId;
    private Integer roleId;
    private Boolean isActive;

    private static Set<SimpleGrantedAuthority> mapToSimpleGrantedAuthority(Set<ApplicationUserPermission> permissions) {
        return permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermission()))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> permissions = new HashSet<>();
        if (Objects.equals(roleId, PersonRolesConfig.adminRoleId)) {
            permissions.addAll(mapToSimpleGrantedAuthority(ApplicationUserRole.ADMIN.getPermissions()));
            permissions.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (Objects.equals(roleId, PersonRolesConfig.moderatorRoleId)) {
            permissions.addAll(mapToSimpleGrantedAuthority(ApplicationUserRole.MODERATOR.getPermissions()));
            permissions.add(new SimpleGrantedAuthority("ROLE_MODERATOR"));
        } else if (Objects.equals(roleId, PersonRolesConfig.clientRoleId)) {
            permissions.addAll(mapToSimpleGrantedAuthority(ApplicationUserRole.CLIENT.getPermissions()));
            permissions.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
        }

        return permissions;
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
        return isActive;
    }
}