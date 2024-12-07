package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@ToString
@Document(collection = "token")
public class Token {

    @Id
    private String id;
    private String email;
    private String tokenValue;
    private Instant expirationData;

}
