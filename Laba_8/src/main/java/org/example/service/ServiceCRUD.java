package org.example.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.example.dto.ResponseDto;
import org.example.dto.ServiceDto;
import org.example.entity.Services;
import org.example.mapper.ServiceMapper;
import org.example.repository.ServiceRepository;
import org.example.restExceptionHandler.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface ServiceCRUD {

    Flux<ServiceDto> getAll();
    Mono<ServiceDto> getById(int id);
    Mono<ResponseDto> add(ServiceDto data);
    Mono<ResponseDto> update(ServiceDto data);
    Mono<ResponseDto> delete(int id);

    @Service
    @RequiredArgsConstructor
    class Base implements ServiceCRUD {

        private static final Log log = LogFactory.getLog(ServiceCRUD.class);
        private final ServiceRepository repository;
        private final I18nService i18n;
        private final ServiceMapper mapper;

        @Override
        public Flux<ServiceDto> getAll() {
            return repository.findAll()
                    .map(mapper::mapToDto)
                    .doOnError(e -> log.error("Error fetching services: {}" + e.getMessage()));
        }

        @Override
        public Mono<ServiceDto> getById(int id) {
            log.info("Fetching service by id: " + id);
            return repository.findById(id)
                    .map(mapper::mapToDto)
                    .switchIfEmpty(Mono.error(new ResourceNotFoundException(i18n.getMessage("no-info"))))
                    .doOnError(e -> log.error("Error fetching barber by id:" + e.getMessage()));
        }

        @Override
        public Mono<ResponseDto> add(ServiceDto data) {
            return repository.save(mapper.mapToEntity(data))
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
    }
}