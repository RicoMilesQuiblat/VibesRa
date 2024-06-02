package com.NeuralN.VibesRa.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {
    private UUID userId;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
}
