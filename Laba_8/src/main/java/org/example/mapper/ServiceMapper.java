package org.example.mapper;

import org.example.dto.ServiceDto;
import org.example.entity.Services;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {

    public Services mapToEntity(ServiceDto serviceDto){
        Services services = new Services();
        services.setId(serviceDto.getId());
        services.setName(serviceDto.getName());
        services.setPrice(serviceDto.getPrice());
        return services;
    }

    public ServiceDto mapToDto(Services services){
        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setId(services.getId());
        serviceDto.setName(services.getName());
        serviceDto.setPrice(services.getPrice());
        return serviceDto;
    }
}
