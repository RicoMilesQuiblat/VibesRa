package com.NeuralN.VibesRa.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private UUID id;
    private String username;
}
