package org.example.service;

import org.example.dto.BarberDto;

public interface BarberService extends CRUDService<BarberDto>{

    boolean isOwner(int barberId);

}
