package com.NeuralN.VibesRa.dto;

import lombok.Data;

@Data
public class ImageUploadDTO {

    private Long id;
    private String name;
    private String url;
    private String imageId;
    private Long hotelId;
}
