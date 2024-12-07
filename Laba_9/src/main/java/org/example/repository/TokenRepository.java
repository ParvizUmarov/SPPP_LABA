package org.example.repository;

import org.example.entity.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TokenRepository extends MongoRepository<Token, String> {
    @Query("{ 'tokenValue': ?0 }")
    Token findByTokenValue(String tokenValue);

    @Query("{ 'email': ?0 }")
    Token findByEmail(String email);

    @Query(value = "{ 'tokenValue': ?0 }", delete = true)
    Token deleteByToken(String token);

}
