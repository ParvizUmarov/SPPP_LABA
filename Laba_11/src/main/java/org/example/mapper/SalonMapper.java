package org.example.mapper;

import org.example.dto.SalonDto;
import org.example.entity.Salon;
import org.springframework.stereotype.Component;

@Component
public class SalonMapper {

    public Salon mapToEntity(SalonDto salonDto){
        var salon = new Salon();
        salon.setId(salonDto.getId());
        salon.setImages(salonDto.getImages());
        salon.setAddress(salonDto.getAddress());
        salon.setLatitude(salonDto.getLatitude());
        salon.setLongitude(salonDto.getLongitude());
        return salon;
    }

    public SalonDto mapToDto(Salon salon){
        var salonDto = new SalonDto();
        salonDto.setId(salon.getId());
        salonDto.setAddress(salon.getAddress());
        salonDto.setImages(salon.getImages());
        salonDto.setLongitude(salon.getLongitude());
        salonDto.setLatitude(salon.getLatitude());
        salonDto.setOwnerId(salon.getBarber() == null ? 0 : salon.getBarber().getId());
        return salonDto;
    }

}
