package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Services;
import org.example.repository.ServiceRepo;
import org.example.service.CRUDService;
import org.example.service.I18nService;
import org.example.service.IOService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceCRUD implements CRUDService<String> {

    private final ServiceRepo repository;
    private final I18nService i18n;
    private final IOService io;

    @Override
    public String getAll() {
        try {
            var result = repository.getAll();
            StringBuilder sb = new StringBuilder();
            for (Services service : result) {
                sb.append(entityToString(service)).append("\n\n");
            }
            return sb.toString();
        } catch (Exception e) {
            return i18n.getMessage("error") + "\n" + e.getMessage();
        }
    }

    @Override
    public String get(String name) {
        try {
            var result = repository.getByArg(name);
            if (result == null) {
                return i18n.getMessage("no-info");
            }
            return entityToString(result).toString();
        } catch (Exception e) {
            return i18n.getMessage("no-info");
        }

    }

    @Override
    public String add(String data) {
        io.print(i18n.getMessage("enter-service-price"));
        try {
            var price = io.readLine();
            var service = new Services();
            service.setName(data);
            service.setPrice(Integer.parseInt(price));
            repository.add(service);
            return i18n.getMessage("service-create");
        } catch (Exception e) {
            return i18n.getMessage("error") + "\n" + e.getMessage();
        }

    }

    @Override
    public String update(String name) {
        io.print("Введите новую цену: ");
        try {
            var price = Integer.parseInt(io.readLine());
            var result = repository.update(price, name);
            return result ? i18n.getMessage("service-update-success") : i18n.getMessage("service-update-error");
        } catch (Exception e) {
            return i18n.getMessage("service-update-error") + "\n" + e.getMessage();
        }

    }

    @Override
    public String delete(String data) {
        try {
            var result = repository.delete(data);
            return result ? i18n.getMessage("service-delete") : i18n.getMessage("error");
        } catch (Exception e) {
            return i18n.getMessage("error") + "\n" + e.getMessage();
        }
    }

    StringBuilder entityToString(Services services) {
        StringBuilder sb = new StringBuilder();
        return sb.append("id: ").append(" ").append(services.getId()).append("\n")
                .append(i18n.getMessage("service-name")).append(services.getName()).append("\n")
                .append(i18n.getMessage("service-price")).append(services.getPrice());
    }
}
