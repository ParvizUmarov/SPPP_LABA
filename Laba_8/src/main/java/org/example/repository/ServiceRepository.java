package org.example.repository;

import org.example.entity.Services;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public interface ServiceRepository extends ReactiveCrudRepository<Services, Integer> {

    @Query("SELECT s FROM Services s WHERE s.name = ?1")
    Mono<Services> getServiceByName(String name);

    @Transactional
    @Modifying
    @Query("DELETE FROM Services s WHERE s.name = ?1")
    Mono<Integer> deleteService(String name);

    @Transactional
    @Modifying
    @Query("UPDATE Services s SET s.price = ?1 WHERE s.name = ?2")
    Mono<Integer> updateService(int price, String name);

}
