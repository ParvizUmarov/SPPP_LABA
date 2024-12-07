package org.example.repository;

import org.example.entity.Barber;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BarberRepository extends ReactiveCrudRepository<Barber, Integer> {

    @Query("SELECT b FROM Barber b WHERE b.mail = ?1")
    Mono<Barber> findByMail(String mail);

    @Transactional
    @Modifying
    @Query("DELETE FROM Barber b WHERE b.name = ?1")
    Mono<Integer> deleteBarber(String name);

    @Transactional
    @Modifying
    @Query("UPDATE Barber b SET b.name = ?1 WHERE b.name = ?2")
    Mono<Void> updateBarber(String newName, String oldName);

    @Query(value = "SELECT * FROM barber LIMIT 10")
    Flux<Barber> getAllBarbers();

}
