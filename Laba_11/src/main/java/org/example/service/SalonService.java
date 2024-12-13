package org.example.service;
import org.example.dto.ResponseDto;
import org.example.dto.SalonDto;

import java.util.List;

public interface SalonService {

    List<SalonDto> getAllSalons();

    ResponseDto add(SalonDto salonDto);

    ResponseDto update(SalonDto salonDto);

    ResponseDto delete(int id);
}