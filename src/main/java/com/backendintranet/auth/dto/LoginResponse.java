package com.backendintranet.auth.dto;

public record LoginResponse(boolean authenticated, String message) {
}
