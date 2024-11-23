package org.example.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.BarberDto;
import org.example.service.CRUDService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@RestController
@RequestMapping("/barber")
@RequiredArgsConstructor
public class BarberController {

    private final CRUDService<BarberDto> service;

    @GetMapping
    public Collection<BarberDto> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable int id){
        try{
            log.info("find barber by id:" + id);
            var barberDto = service.getById(id);
            return ResponseEntity.ok(barberDto);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/mail/{mail}")
    public ResponseEntity getByMail(@PathVariable String mail){
        try{
            log.info("find barber by mail:" + mail);
            var barberDto = service.getByMail(mail);
            return ResponseEntity.ok(barberDto);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody BarberDto barberDto){
        try {
             log.info("creare barber");
             var result = service.add(barberDto);
             return ResponseEntity.ok(result);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PatchMapping("/update")
    public ResponseEntity update(@RequestBody BarberDto barberDto){
        try {
            log.info("update barber");
            var result = service.update(barberDto);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable int id){
        try {
            log.info("delete barber by id: " + id);
            var result = service.delete(id);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

}
