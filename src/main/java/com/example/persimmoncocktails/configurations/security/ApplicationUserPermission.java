package com.example.persimmoncocktails.configurations.security;

public enum ApplicationUserPermission{
    READ_ALL("all:read"),
    UPDATE_CONTENT("content:update"),
    UPDATE_MODERATOR("moderator:update"), // permission to update moderator info
    UPDATE_ADMIN("admin:update"),
    READ_MODERATOR("moderator:read"); // permission to read info about moderator


    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
