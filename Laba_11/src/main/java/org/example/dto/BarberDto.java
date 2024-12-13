package org.example.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BarberDto {
    private Integer id;
    private String name;
    private String surname;
    private String birthday;
    private String phone;
    private String mail;
    private String password;
    private Boolean authState;
    private Integer workExperience;
    private String token;
    private Integer salonId;
    private String salonAddress;
    private String salonImages;
    private Integer serviceId;
    private String serviceName;
    private Integer servicePrice;
    private String createdBy;

}
