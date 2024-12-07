package org.example.service;


import org.example.dto.ResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface CRUDService<T> {
    Flux<T> getAll();
    Mono<T> getById(int id);
    Mono<ResponseDto> add(T data);
    Mono<ResponseDto> update(T data);
    Mono<ResponseDto> delete(int id);
    Mono<T> getByMail(String mail);
}
