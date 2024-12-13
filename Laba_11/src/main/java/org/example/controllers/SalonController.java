package org.example.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ResponseDto;
import org.example.dto.SalonDto;
import org.example.service.SalonService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/salon")
@RequiredArgsConstructor
public class SalonController {

    private final SalonService salonService;

    @GetMapping
    @PreAuthorize("hasRole('READER') or hasRole('EDITOR')")
    public ResponseEntity<Collection<SalonDto>> getAllSalons(){
        log.info("get all salons ");
        return ResponseEntity.ok(salonService.getAllSalons());
    }

    @PostMapping
    @PreAuthorize("hasRole('EDITOR')")
    public ResponseEntity<ResponseDto> createSalon(@RequestBody SalonDto salonDto){
        var result = salonService.add(salonDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EDITOR')")
    public ResponseEntity<ResponseDto> deleteSalon(@PathVariable int id){
        log.debug("delete salon id: " + id);
        var result = salonService.delete(id);
        return ResponseEntity.ok(result);
    }

    @PatchMapping
    @PreAuthorize("hasRole('EDITOR')")
    public ResponseEntity<ResponseDto> updateSalon(@RequestBody SalonDto salonDto){
        log.debug("update salon id: " + salonDto.getId());
        var result = salonService.update(salonDto);
        return ResponseEntity.ok(result);
    }

}
