package org.example.repository;

import org.example.entity.Barber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface BarberRepository extends JpaRepository<Barber, Integer> {

    @Query("SELECT b FROM Barber b WHERE b.name = ?1")
    Barber findByName(String name);

    @Transactional
    @Modifying
    @Query("DELETE FROM Barber b WHERE b.name = ?1")
    int deleteBarber(String name);

    @Transactional
    @Modifying
    @Query("UPDATE Barber b SET b.name = ?1 WHERE b.name = ?2")
    void updateBarber(String newName, String oldName);

    @Query(value = "SELECT * FROM barber LIMIT 10", nativeQuery = true)
    Collection<Barber> getAllBarbers();



}
