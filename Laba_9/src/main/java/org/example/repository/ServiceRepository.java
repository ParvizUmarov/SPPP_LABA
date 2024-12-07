package org.example.repository;

import org.example.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ServiceRepository extends JpaRepository<Services, Integer> {

    @Query("SELECT s FROM Services s WHERE s.name = ?1")
    Services getServiceByName(String name);

    @Transactional
    @Modifying
    @Query("DELETE FROM Services s WHERE s.name = ?1")
    int deleteService(String name);

    @Transactional
    @Modifying
    @Query("UPDATE Services s SET s.price = ?1 WHERE s.name = ?2")
    int updateService(int price, String name);

}
