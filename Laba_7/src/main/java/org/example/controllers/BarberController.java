package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.BarberDto;
import org.example.dto.ResponseDto;
import org.example.service.CRUDService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/barber")
@RequiredArgsConstructor
public class BarberController {

    private final CRUDService<BarberDto> service;

    @GetMapping
    public Collection<BarberDto> getAll() {
        return service.getAll();
    }

    @Operation(
            summary = "Get barber by id",
            description = "Get barber by id. Return all info about barber"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = BarberDto.class), mediaType = "application/json")
                    }
            )
    )
    @GetMapping("/{id}")
    public ResponseEntity<BarberDto> getById(@PathVariable int id) {
        log.info("find barber by id:" + id);
        var barberDto = service.getById(id);
        return ResponseEntity.ok(barberDto);
    }

    @Operation(
            summary = "Get barber by mail",
            description = "Get barber by mail. Return all info about barber"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = BarberDto.class), mediaType = "application/json")
                    }
            )
    )
    @GetMapping("/mail/{mail}")
    public ResponseEntity<BarberDto> getByMail(@PathVariable String mail) {
        log.info("find barber by mail:" + mail);
        var barberDto = service.getByMail(mail);
        return ResponseEntity.ok(barberDto);
    }

    @Operation(
            summary = "Register barber",
            description = "Register barber to db. Save barber info to db"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = ResponseDto.class), mediaType = "application/json")
                    }
            )
    )
    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody BarberDto barberDto) {
        log.info("creare barber");
        var result = service.add(barberDto);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Update barber",
            description = "Update barber in db."
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = ResponseDto.class), mediaType = "application/json")
                    }
            )
    )
    @PatchMapping("/update")
    public ResponseEntity<ResponseDto> update(@RequestBody BarberDto barberDto) {
        log.info("update barber");
        var result = service.update(barberDto);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Delete barber",
            description = "Delete barber from db."
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = ResponseDto.class), mediaType = "application/json")
                    }
            )
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        log.info("delete barber by id: " + id);
        var result = service.delete(id);
        return ResponseEntity.ok(result);
    }

}
