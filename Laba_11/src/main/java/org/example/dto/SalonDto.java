package org.example.dto;

import lombok.Data;

@Data
public class SalonDto {
    private Integer id;
    private String address;
    private String images;
    private String longitude;
    private String latitude;
    private Integer ownerId;
}
