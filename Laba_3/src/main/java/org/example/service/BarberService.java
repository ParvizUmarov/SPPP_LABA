package org.example.service;

import org.example.repository.BarberRepository;
import org.springframework.stereotype.Service;

@Service
public class BarberService implements CRUDService<String> {

    private final BarberRepository repository;

    public BarberService(BarberRepository repository) {
        this.repository = repository;
    }

    @Override
    public String getAll() {
        return repository.getAllBarber();
    }

    @Override
    public String get(String name) {
        return repository.getUserByName(name);
    }
}
