package com.example.persimmoncocktails.configurations.security;

public enum ApplicationUserPermission{
    READ_ALL("all:read"),
    UPDATE_USER("user:update"),
    UPDATE_MODERATOR("moderator:update"),
    UPDATE_ADMIN("admin:update");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
