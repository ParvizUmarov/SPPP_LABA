package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.BarberDto;
import org.example.dto.ResponseDto;
import org.example.entity.Barber;
import org.example.mapper.BarberMapper;
import org.example.repository.BarberRepository;
import org.example.restExceptionHandler.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BarberService {

    Flux<BarberDto> getAll();
    Mono<BarberDto> getById(int id);
    Mono<ResponseDto> add(BarberDto data);
    Mono<ResponseDto> update(BarberDto data);
    Mono<ResponseDto> delete(int id);
    Mono<BarberDto> getByMail(String mail);

    @Slf4j
    @Service
    @RequiredArgsConstructor
    class Base implements BarberService {

        private final IOService io;
        private final I18nService i18nService;
        private final BarberRepository repository;
        private final BarberMapper mapper;

        @Override
        public Flux<BarberDto> getAll() {
            return repository.getAllBarbers()
                    .map(mapper::mapToDto)
                    .doOnError(e -> log.error("Error fetching barbers: {}", e.getMessage()));
        }

        @Override
        public Mono<BarberDto> getById(int id) {
            log.info("Fetching barber by id: {}", id);
            return repository.findById(id) // Репозиторий должен возвращать Mono
                    .map(mapper::mapToDto)
                    .switchIfEmpty(Mono.error(new ResourceNotFoundException(i18nService.getMessage("no-info"))))
                    .doOnError(e -> log.error("Error fetching barber by id: {}", e.getMessage()));
        }

        @Override
        public Mono<ResponseDto> add(BarberDto data) {
            log.info("Adding new barber");
            return repository.save(mapper.mapToEntity(data))
                    .map(saved -> new ResponseDto(i18nService.getMessage("succesfully-create")))
                    .doOnError(e -> log.error("Error adding barber: {}", e.getMessage()))
                    .onErrorResume(e -> Mono.just(new ResponseDto(i18nService.getMessage("error"))));
        }

        @Override
        public Mono<ResponseDto> update(BarberDto barberDto) {
            log.info("Updating barber with id: {}", barberDto.getId());
            return repository.findById(barberDto.getId())
                    .switchIfEmpty(Mono.error(new ResourceNotFoundException("Barber not found")))
                    .flatMap(existingBarber -> {
                        updateFields(existingBarber, barberDto);
                        return repository.save(existingBarber);
                    })
                    .map(updated -> new ResponseDto("Successfully changed barber: " + updated.getName()))
                    .doOnError(e -> log.error("Error updating barber: {}", e.getMessage()))
                    .onErrorResume(e -> Mono.just(new ResponseDto(i18nService.getMessage("cannot-changed"))));
        }

        @Override
        public Mono<ResponseDto> delete(int id) {
            log.info("Deleting barber with id: {}", id);
            return repository.findById(id)
                    .switchIfEmpty(Mono.error(new ResourceNotFoundException(i18nService.getMessage("no-info"))))
                    .flatMap(existing -> repository.delete(existing)
                            .then(Mono.just(new ResponseDto(i18nService.getMessage("succesfully-delete")))))
                    .doOnError(e -> log.error("Error deleting barber: {}", e.getMessage()))
                    .onErrorResume(e -> Mono.just(new ResponseDto(i18nService.getMessage("cannot-delete") + " " + id)));
        }

        @Override
        public Mono<BarberDto> getByMail(String mail) {
            log.info("Fetching barber by mail: {}", mail);
            return repository.findByMail(mail) // Метод в репозитории должен возвращать Mono
                    .map(mapper::mapToDto)
                    .switchIfEmpty(Mono.error(new ResourceNotFoundException(i18nService.getMessage("no-info"))))
                    .doOnError(e -> log.error("Error fetching barber by mail: {}", e.getMessage()));
        }

        private static void updateFields(Barber existingBarber, BarberDto barberDto) {
            if (barberDto.getName() != null) existingBarber.setName(barberDto.getName());
            if (barberDto.getSurname() != null) existingBarber.setSurname(barberDto.getSurname());
            if (barberDto.getMail() != null) existingBarber.setMail(barberDto.getMail());
            if (barberDto.getBirthday() != null) existingBarber.setBirthday(barberDto.getBirthday());
            if (barberDto.getPhone() != null) existingBarber.setPhone(barberDto.getPhone());
            if (barberDto.getPassword() != null) existingBarber.setPassword(barberDto.getPassword());
            if (barberDto.getWorkExperience() != null) existingBarber.setWorkExperience(barberDto.getWorkExperience());
            if (barberDto.getAuthState() != null) existingBarber.setAuthState(barberDto.getAuthState());
        }
    }
}
