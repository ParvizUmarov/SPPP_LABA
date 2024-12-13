package org.example.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.BarberDto;
import org.example.dto.ResponseDto;
import org.example.dto.ServiceDto;
import org.example.service.BarberService;
import org.example.service.CRUDService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@RestController
@RequestMapping("/barber")
@RequiredArgsConstructor
public class BarberController {

    private final BarberService service;

    @GetMapping
    public Collection<BarberDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BarberDto> getById(@PathVariable int id) {
        log.info("find barber by id:" + id);
        var barberDto = service.getById(id);
        return ResponseEntity.ok(barberDto);
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> add(@RequestBody BarberDto barberDto) {
        log.info("create barber: " + barberDto);
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        barberDto.setCreatedBy(currentUsername);
        var result = service.add(barberDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/mail/{mail}")
    public ResponseEntity<BarberDto> getByMail(@PathVariable String mail) {
        log.info("find barber by mail:" + mail);
        var barberDto = service.getByMail(mail);
        return ResponseEntity.ok(barberDto);
    }

    @PatchMapping("/update")
    public ResponseEntity<ResponseDto> update(@RequestBody BarberDto barberDto) {
        log.info("update barber");
        var result = service.update(barberDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto> delete(@PathVariable int id) {
        log.info("delete barber by id: " + id);
        var result = service.delete(id);
        return ResponseEntity.ok(result);
    }
}
