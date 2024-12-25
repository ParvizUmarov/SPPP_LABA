package org.example.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.BarberDto;
import org.example.dto.ResponseDto;
import org.example.service.BarberService;
import org.example.service.CRUDService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@RestController
@RequestMapping("/barber")
@RequiredArgsConstructor
public class BarberController {

    private final BarberService service;

    @GetMapping
    public Flux<BarberDto> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Mono<BarberDto> getById(@PathVariable int id){
        log.info("find barber by id:" + id);
        return service.getById(id)
                .doOnError(e -> log.error("Error finding barber by id: {}", e.getMessage()));
    }

    @GetMapping("/mail/{mail}")
    public Mono<BarberDto> getByMail(@PathVariable String mail) {
        log.info("Finding barber by mail: {}", mail);
        return service.getByMail(mail)
                .doOnError(e -> log.error("Error finding barber by mail: {}", e.getMessage()));
    }

    @PostMapping("/add")
    public Mono<ResponseDto> add(@RequestBody BarberDto barberDto) {
        log.info("Registering new barber");
        return service.add(barberDto)
                .doOnError(e -> log.error("Error registering barber: {}", e.getMessage()));
    }

    @PatchMapping("/update")
    public Mono<ResponseDto> update(@RequestBody BarberDto barberDto) {
        log.info("Updating barber");
        return service.update(barberDto)
                .doOnError(e -> log.error("Error updating barber: {}", e.getMessage()));
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> delete(@PathVariable int id) {
        log.info("Deleting barber by id: {}", id);
        return service.delete(id)
                .then()
                .doOnError(e -> log.error("Error deleting barber: {}", e.getMessage()));
    }

}
