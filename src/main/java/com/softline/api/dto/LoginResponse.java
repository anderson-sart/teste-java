package com.softline.api.dto;

public record LoginResponse(String token, String username, boolean isAdmin) {}
