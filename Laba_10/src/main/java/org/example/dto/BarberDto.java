package org.example.dto;

import lombok.Data;

@Data
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


    @Override
    public String toString() {
        return "Barber info:" +
                "\n id=" + id +
                "\n name='" + name + '\'' +
                "\n surname='" + surname + '\'' +
                "\n birthday='" + birthday + '\'' +
                "\n phone='" + phone + '\'' +
                "\n mail='" + mail + '\'' +
                "\n password='" + password + '\'' +
                "\n authState=" + authState +
                "\n workExperience=" + workExperience +
                "\n token='" + token + '\'' +
                "\n salonId=" + salonId +
                "\n salonAddress='" + salonAddress + '\'' +
                "\n salonImages='" + salonImages + '\'' +
                "\n serviceId=" + serviceId +
                "\n serviceName='" + serviceName + '\'' +
                "\n servicePrice=" + servicePrice;
    }
}
