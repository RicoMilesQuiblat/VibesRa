package com.NeuralN.VibesRa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("message")
    private String message;

    private User user;

    public AuthResponse(String accessToken, String refreshToken, String message, User user) {
        this.accessToken = accessToken;
        this.message = message;
        this.refreshToken = refreshToken;
        this.user = user;
    }

}