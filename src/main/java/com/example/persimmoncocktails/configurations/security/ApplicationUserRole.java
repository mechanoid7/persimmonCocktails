package com.example.persimmoncocktails.configurations.security;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.persimmoncocktails.configurations.security.ApplicationUserPermission;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum ApplicationUserRole {
    ADMIN(adminPermissions()),
    MODERATOR(moderatorPermissions()),
    CLIENT(clientPermissions());

    private static HashSet<ApplicationUserPermission> adminPermissions() {
        HashSet<ApplicationUserPermission> res = new HashSet<>();
        res.add(ApplicationUserPermission.READ_ALL);
        res.add(ApplicationUserPermission.UPDATE_MODERATOR);
        res.add(ApplicationUserPermission.UPDATE_ADMIN);
        res.add(ApplicationUserPermission.READ_MODERATOR);
        return res;
    }

    private static HashSet<ApplicationUserPermission> moderatorPermissions() {
        HashSet<ApplicationUserPermission> res = new HashSet<>();
        res.add(ApplicationUserPermission.READ_ALL);
        res.add(ApplicationUserPermission.READ_MODERATOR);
        //
        return res;
    }

    private static HashSet<ApplicationUserPermission> clientPermissions() {
        HashSet<ApplicationUserPermission> res = new HashSet<>();
        res.add(ApplicationUserPermission.READ_ALL);
        return res;
    }

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
