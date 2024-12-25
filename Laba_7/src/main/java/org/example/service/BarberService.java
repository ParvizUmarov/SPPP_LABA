package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.BarberDto;
import org.example.dto.ResponseDto;
import org.example.entity.Barber;
import org.example.exception.RestException;
import org.example.mapper.BarberMapper;
import org.example.repository.BarberRepository;
import org.example.restExceptionHandler.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

public interface BarberService {
    List<BarberDto> getAll();
    BarberDto getById(int id);
    ResponseDto add(BarberDto data);
    ResponseDto update(BarberDto data);
    ResponseDto delete(int id);
    BarberDto getByMail(String mail);

    @Slf4j
    @Service
    @RequiredArgsConstructor
    class Base implements BarberService {

        private final IOService ioService;
        private final I18nService i18nService;
        private final BarberRepository repository;
        private final BarberMapper mapper;

        @Override
        public List<BarberDto> getAll() {
            return repository.getAllBarbers()
                    .stream()
                    .map(mapper::mapToDto)
                    .toList();
        }

        @Override
        public BarberDto getById(int id) {
            try {
                var result = repository.findById(id);
                if (result == null) {
                    throw new ResourceNotFoundException(i18nService.getMessage("no-info"));
                } else {
                    return mapper.mapToDto(result.get());
                }
            } catch (Exception e) {
                log.error("barber service error: " + e.getMessage());
                throw new RestException(i18nService.getMessage("no-info"));
            }
        }

        @Override
        public ResponseDto add(BarberDto data) {
            try {
                repository.save(mapper.mapToEntity(data));
                return new ResponseDto(i18nService.getMessage("succesfully-create"));
            } catch (Exception e) {
                throw new RestException(i18nService.getMessage("error"));
            }
        }

        @Override
        public ResponseDto delete(int id) {
            try {
                repository.deleteById(id);
                return new ResponseDto(i18nService.getMessage("succesfully-delete"));
            } catch (Exception e) {
                ioService.println(e.toString());
                throw new RestException(i18nService.getMessage("cannot-delete") + " " + id);
            }
        }

        @Override
        public BarberDto getByMail(String mail) {
            try {
                var result = repository.findByMail(mail);
                if (result == null) {
                    throw new ResourceNotFoundException(i18nService.getMessage("no-info"));
                } else {
                    return mapper.mapToDto(result);
                }
            } catch (Exception e) {
                log.error("barber service error: " + e.getMessage());
                throw new RestException(i18nService.getMessage("no-info"));
            }
        }

        @Override
        public ResponseDto update(BarberDto barberDto) {
            try {
                Barber barber = repository.findById(barberDto.getId()).orElseThrow(() -> new RuntimeException("Barber not found"));
                System.out.println("barber: " + barber);
                if(barberDto.getName() != null){barber.setName(barberDto.getName());}
                if(barberDto.getSurname() != null){barber.setSurname(barberDto.getSurname());}
                if(barberDto.getMail() != null){barber.setMail(barberDto.getMail());}
                if(barberDto.getBirthday() != null){barber.setBirthday(barberDto.getBirthday());}
                if(barberDto.getPhone() != null){barber.setPhone(barberDto.getPhone());}
                if(barberDto.getPassword() != null){barber.setPassword(barberDto.getPassword());}
                if(barberDto.getWorkExperience() != null){barber.setWorkExperience(barberDto.getWorkExperience());}
                if(barberDto.getAuthState() != null){barber.setAuthState(barberDto.getAuthState());}
                repository.save(barber);
                return new ResponseDto("successfully changed barber: " + barber.getName());
            } catch (Exception e) {
                return new ResponseDto(i18nService.getMessage("cannot-changed"));
            }

        }
    }
}