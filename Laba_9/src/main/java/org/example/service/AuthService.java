package org.example.serviceInterface;

import org.example.dto.TokenDto;
import org.example.entity.Token;

public interface AuthService {

    TokenDto register(Token token);

    boolean checkAuth(String token);

    TokenDto login(Token token);

    void logout(String token);

}
