package org.example.serviceInterface;

import org.example.dto.BarberDto;
import org.example.dto.ResponseDto;
import org.example.dto.TokenDto;
import org.example.entity.Token;

public interface BarberService<T> extends CRUDService<T> {

    TokenDto login(Token token);

    TokenDto register(BarberDto token);

    ResponseDto logout(String token);

}
