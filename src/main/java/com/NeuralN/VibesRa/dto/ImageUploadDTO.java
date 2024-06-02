package com.NeuralN.VibesRa.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ImageUploadDTO {

    private UUID id;
    private String name;
    private String url;
    private String imageId;
    private Long hotelId;
}
