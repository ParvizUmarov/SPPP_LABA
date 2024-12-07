package org.example.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.example.dto.ResponseDto;
import org.example.dto.ServiceDto;
import org.example.entity.Services;
import org.example.repository.ServiceRepository;
import org.example.restExceptionHandler.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ServiceCRUD implements CRUDService<ServiceDto> {

    private static final Log log = LogFactory.getLog(ServiceCRUD.class);
    private final ServiceRepository repository;
    private final I18nService i18n;
    private final IOService io;

    @Override
    public Flux<ServiceDto> getAll() {
        return repository.findAll()
                .map(ServiceCRUD::mapToDto)
                .doOnError(e -> log.error("Error fetching services: {}" + e.getMessage()));
    }

    @Override
    public Mono<ServiceDto> getById(int id) {
        log.info("Fetching service by id: " + id);
        return repository.findById(id)
                .map(ServiceCRUD::mapToDto)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(i18n.getMessage("no-info"))))
                .doOnError(e -> log.error("Error fetching barber by id:" + e.getMessage()));
    }

    @Override
    public Mono<ResponseDto> add(ServiceDto data) {
        return repository.save(mapToEntity(data))
                .map(saved -> new ResponseDto(i18n.getMessage("succesfully-create")));
    }

    @Override
    public Mono<ResponseDto> update(ServiceDto data) {
//        io.print("Введите новую цену: ");
//        try{
//            var price =  Integer.parseInt(io.readLine());
//            var result = repository.updateService(price, name);
//            return result == 1 ? i18n.getMessage("service-update-success") : i18n.getMessage("service-update-error");
//        }catch (Exception e){
//            return i18n.getMessage("service-update-error") + "\n" + e.getMessage();
//        }

        return null;
    }


    @Override
    public Mono<ResponseDto> delete(int id) {

        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(i18n.getMessage("no-info"))))
                .flatMap(existing -> repository.delete(existing)
                        .then(Mono.just(new ResponseDto(i18n.getMessage("service-delete")))))
                .doOnError(e -> log.error("Error deleting barber: {}" + e.getMessage()))
                .onErrorResume(e -> Mono.just(new ResponseDto("cannot-delete" + " " + id)));
    }

    @Override
    public Mono<ServiceDto> getByMail(String mail) {
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
