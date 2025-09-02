package com.example.uade.tpo.ecommerce_grupo10.controllers.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class AuthResponse {
    @JsonProperty("access_token")
    private String token;
}
