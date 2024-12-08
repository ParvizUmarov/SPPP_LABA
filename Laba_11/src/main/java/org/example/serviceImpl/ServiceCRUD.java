package org.example.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.example.dto.ResponseDto;
import org.example.dto.ServiceDto;
import org.example.entity.Services;
import org.example.repository.ServiceRepository;
import org.example.exception.ResourceNotFoundException;
import org.example.service.CRUDService;
import org.example.service.I18nService;
import org.example.service.IOService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ServiceCRUD implements CRUDService<ServiceDto> {

    private static final Log log = LogFactory.getLog(ServiceCRUD.class);
    private final ServiceRepository repository;
    private final I18nService i18n;
    private final IOService io;

    @Override
    public Collection<ServiceDto> getAll() {
        return repository.findAll()
                .stream()
                .map(ServiceCRUD::mapToDto)
                .toList();
    }

    @Override
    public ServiceDto getById(int id) {
        var result = repository.findById(id);
        if(result == null){
            throw new ResourceNotFoundException(i18n.getMessage("no-info"));
        }
        return mapToDto(result.get());
    }

    @Override
    public ResponseDto add(ServiceDto data) {
        try{
            repository.save(mapToEntity(data));
            return new ResponseDto(i18n.getMessage("service-create"));
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseDto(i18n.getMessage("error"));
        }
    }

    @Override
    public ResponseDto update(ServiceDto data) {
        /*
        io.print("Введите новую цену: ");
        try{
            var price =  Integer.parseInt( io.readLine());
            var result = repository.updateService(price, name);
            return result == 1 ? i18n.getMessage("service-update-success") : i18n.getMessage("service-update-error");
        }catch (Exception e){
            return i18n.getMessage("service-update-error") + "\n" + e.getMessage();
        }
         */
        return null;
    }


    @Override
    public ResponseDto delete(int id) {
        try {
            repository.deleteById(id);
            return new ResponseDto(i18n.getMessage("service-delete"));
        } catch (Exception e) {
            log.info(e.toString());
            return new ResponseDto("cannot-delete" + " " + id);
        }

    }

    @Override
    public ServiceDto getByMail(String mail) {
        return null;
    }

    StringBuilder entityToString(Services services) {
        StringBuilder sb = new StringBuilder();
        return sb.append("id: ").append(" ").append(services.getId()).append("\n")
                .append(i18n.getMessage("service-name")).append(services.getName()).append("\n")
                .append(i18n.getMessage("service-price")).append(services.getPrice());
    }

    public static Services mapToEntity(ServiceDto serviceDto){
        Services services = new Services();
        services.setId(serviceDto.getId());
        services.setName(serviceDto.getName());
        services.setPrice(serviceDto.getPrice());
        return services;
    }

    public static ServiceDto mapToDto(Services services){
        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setId(services.getId());
        serviceDto.setName(services.getName());
        serviceDto.setPrice(services.getPrice());
        return serviceDto;
    }

}
