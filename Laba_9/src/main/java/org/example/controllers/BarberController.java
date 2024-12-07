package org.example.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aop.CheckAuth;
import org.example.dto.BarberDto;
import org.example.dto.ResponseDto;
import org.example.dto.TokenDto;
import org.example.entity.Token;
import org.example.serviceInterface.BarberService;
import org.example.serviceInterface.CRUDService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/barber")
@RequiredArgsConstructor
public class BarberController {

    private final BarberService<BarberDto> service;

    @CheckAuth
    @GetMapping
    public ResponseEntity<Collection<BarberDto>> getAll(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(service.getAll());
    }

    @CheckAuth
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@RequestHeader("Authorization") String token, @PathVariable int id) {
        log.info("find barber by id:" + id);
        return ResponseEntity.ok(service.getById(id));
    }

    @CheckAuth
    @GetMapping("/mail/{mail}")
    public ResponseEntity<?> getByMail(@RequestHeader("Authorization") String token, @PathVariable String mail) {
        log.info("find barber by mail:" + mail);
        return ResponseEntity.ok(service.getByMail(mail));
    }

    @PostMapping("/register")
    public TokenDto register(@RequestBody BarberDto barberDto) {
        log.info("creare barber");
        return service.register(barberDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Token body){
        return ResponseEntity.ok(service.login(body));
    }

    @CheckAuth
    @PatchMapping("/update")
    public ResponseEntity<?> update(@RequestHeader("Authorization") String token, @RequestBody BarberDto barberDto) {
        log.info("update barber");
        var result = service.update(barberDto);
        return ResponseEntity.ok(result);
    }

    @CheckAuth
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String token, @PathVariable int id) {
        log.info("delete barber by id: " + id);
        return ResponseEntity.ok(service.delete(id));
    }

    @CheckAuth
    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        log.info("logout barber ");
        return ResponseEntity.ok(service.logout(token));
    }

}
