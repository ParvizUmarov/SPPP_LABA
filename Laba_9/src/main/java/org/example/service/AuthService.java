package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.TokenDto;
import org.example.entity.Token;
import org.example.jwt.JwtUtil;
import org.example.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

public interface AuthService {

    TokenDto register(Token token);

    boolean checkAuth(String token);

    TokenDto login(Token token);

    void logout(String token);

    @Slf4j
    @Service
    @RequiredArgsConstructor
    class Base implements AuthService {

        private final TokenRepository repository;
        private final JwtUtil jwtUtil;

        @Override
        public TokenDto register(Token token) {
            log.debug("register body: " + token);
            var generateToken = jwtUtil.generateToken(token.getEmail());
            token.setTokenValue(generateToken);
            token.setExpirationData(Instant.now().plusMillis(jwtUtil.getValidityInMs()));
            repository.save(token);
            return new TokenDto(generateToken);
        }

        @Override
        public boolean checkAuth(String token) {
            try {
                var validatedToken = jwtUtil.validateToken(token);
                if (validatedToken != null) {
                    var storedToken = repository.findByTokenValue(token);
                    if (storedToken != null) {
                        return true;
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            return false;
        }

        @Override
        public TokenDto login(Token token) {
            log.debug("login user: " + token);

            var findUser = repository.findByEmail(token.getEmail());
            var generateToken = jwtUtil.generateToken(token.getEmail());

            if (findUser != null) {
                findUser.setTokenValue(generateToken);
                findUser.setExpirationData(Instant.now().plusMillis(jwtUtil.getValidityInMs()));
                repository.save(findUser);
                return new TokenDto(generateToken);
            } else {
                return register(token);
            }
        }

        @Override
        public void logout(String token) {
            repository.deleteByToken(token);
        }
    }
}
