package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.example.dto.ResponseDto;
import org.example.dto.ServiceDto;
import org.example.entity.Services;
import org.example.mapper.ServiceMapper;
import org.example.repository.ServiceRepository;
import org.example.restExceptionHandler.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

public interface ServiceCRUD {

    Collection<ServiceDto> getAll();

    ServiceDto getById(int id);

    ResponseDto add(ServiceDto data);

    ResponseDto update(ServiceDto data);

    ResponseDto delete(int id);

    @Slf4j
    @Service
    @RequiredArgsConstructor
    class Base implements ServiceCRUD {

        private final ServiceRepository repository;
        private final I18nService i18n;
        private final ServiceMapper mapper;

        @Override
        public Collection<ServiceDto> getAll() {
            return repository.findAll()
                    .stream()
                    .map(mapper::mapToDto)
                    .toList();
        }

        @Override
        public ServiceDto getById(int id) {
            var result = repository.findById(id);
            if (result == null) {
                throw new ResourceNotFoundException(i18n.getMessage("no-info"));
            }
            return mapper.mapToDto(result.get());
        }

        @Override
        public ResponseDto add(ServiceDto data) {
            try {
                repository.save(mapper.mapToEntity(data));
                return new ResponseDto(i18n.getMessage("service-create"));
            } catch (Exception e) {
                log.error(e.getMessage());
                return new ResponseDto(i18n.getMessage("error"));
            }
        }

        @Override
        public ResponseDto update(ServiceDto data) {
            try {

                if(getById(data.getId()) == null){
                    throw new ResourceNotFoundException("Такого сервиса нету");
                }

                repository.save(mapper.mapToEntity(data));
                return new ResponseDto(i18n.getMessage("service-update-success"));
            } catch (Exception e) {
                return new ResponseDto(i18n.getMessage("service-update-error") + "\n" + e.getMessage());
            }
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
    }
}

