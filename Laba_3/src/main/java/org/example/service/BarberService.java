package org.example.service;

import org.example.repository.BarberRepository;
import org.example.repository.Repo;
import org.springframework.stereotype.Service;

@Service
public class BarberService implements CRUDService<String> {

    private final Repo<String> repository;

    public BarberService(BarberRepository repository) {
        this.repository = repository;
    }

    @Override
    public String getAll() {
        return repository.getAll();
    }

    @Override
    public String get(String name) {
        return repository.getByArg(name);
    }
}
