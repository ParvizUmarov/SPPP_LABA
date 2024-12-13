package org.example.service;

import org.example.dto.BarberDto;
import org.example.dto.ResponseDto;
import org.example.dto.UserDto;

public interface BarberService extends CRUDService<BarberDto>{

    boolean isOwner(int barberId);

    ResponseDto login(UserDto userDto);

    ResponseDto register(BarberDto userDto);

}
